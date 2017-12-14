package com.vdsirotkin.telegram.resendbot.handlers;

import com.vdsirotkin.telegram.resendbot.ResendBot;
import com.vdsirotkin.telegram.resendbot.db.DAO;
import com.vdsirotkin.telegram.resendbot.destiny2.domain.profile.Character;
import com.vdsirotkin.telegram.resendbot.destiny2.domain.stats.DestinyHistoricalStatsValue;
import com.vdsirotkin.telegram.resendbot.destiny2.domain.stats.DestinyHistoricalStatsValueDictionary;
import com.vdsirotkin.telegram.resendbot.destiny2.domain.user.UserInfo;
import com.vdsirotkin.telegram.resendbot.destiny2.executors.GetHistoricalStatsExecutor;
import com.vdsirotkin.telegram.resendbot.destiny2.executors.GetProfileExecutor;
import com.vdsirotkin.telegram.resendbot.destiny2.executors.SearchDestinyPlayerExecutor;
import com.vdsirotkin.telegram.resendbot.destiny2.manifest.ManifestService;
import com.vdsirotkin.telegram.resendbot.destiny2.requests.GetHistoricalStatsRequest;
import com.vdsirotkin.telegram.resendbot.destiny2.requests.GetProfileRequest;
import com.vdsirotkin.telegram.resendbot.destiny2.requests.SearchDestinyPlayerRequest;
import com.vdsirotkin.telegram.resendbot.destiny2.responses.GetHistoricalStatsResponse;
import com.vdsirotkin.telegram.resendbot.destiny2.responses.GetProfileResponse;
import com.vdsirotkin.telegram.resendbot.destiny2.responses.SearchDestinyPlayerResponse;
import com.vdsirotkin.telegram.resendbot.utils.DefaultMessages;
import org.apache.log4j.Logger;
import org.apache.log4j.net.SyslogAppender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.methods.ParseMode;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.*;
import java.util.concurrent.CompletableFuture;

/**
 * Created by vitalijsirotkin on 08.09.17.
 */
@Component
@Scope("prototype")
public class WastedOnDestiny2Handler implements Handler {

    private static final Logger log = Logger.getLogger(WastedOnDestiny2Handler.class);

    @Autowired
    private ResendBot bot;

    @Autowired
    private DAO dao;

    @Autowired
    private DefaultMessages dm;

    @Autowired
    private ManifestService service;

    private int step = 1;
    private boolean finished = false;

    @Override
    public void handleMessage(Update update) {
        Long chatId = update.getMessage().getChatId();
//        if (chatId == 82224688L) { // тиесто
        if (chatId == 43322401L) { // тиесто
            try {
                bot.execute(new SendMessage(43322401L, "пошел нахуй"));
            } catch (TelegramApiException e) {
                log.error(e, e);
            }
        }
        String text = update.getMessage().getText();
        SearchDestinyPlayerResponse response;
        try {
            bot.execute(new SendMessage(chatId, "Ищем..."));
        } catch (TelegramApiException e) {
            log.error(e, e);
        }
        String nickName = dao.getNickName(chatId.toString());
        response = new SearchDestinyPlayerExecutor().executeMethod(new SearchDestinyPlayerRequest().setNickname(step == 1 ? nickName : text).setMembershipType(2));
        if (response == null || response.getResponse().isEmpty()) {
            try {
                bot.execute(dm.cannotFindNickname(chatId.toString()));
                if (step == 1) {
                    ++step;
                }
            } catch (TelegramApiException e) {
                log.error(e, e);
            }
        } else if (response.getResponse().size() > 1) {
            // TODO: 08.09.17 write handling
        } else {
            getWastedTime(chatId, response);
        }
    }

    private void getWastedTime(Long chatId, SearchDestinyPlayerResponse response) {
        long startTime = System.currentTimeMillis();
        UserInfo info = response.getResponse().get(0);
        Long membershipId = info.getMembershipId();
        Integer membershipType = info.getMembershipType();
        GetProfileRequest request = new GetProfileRequest()
                .setMembershipType(membershipType)
                .setDestinyMembershipId(membershipId);
        request.getComponents().addAll(Collections.singletonList(200));
        GetProfileResponse getProfileResponse = new GetProfileExecutor().executeMethod(request);
        if (getProfileResponse != null) {
            ResponseModel model = new ResponseModel();
            Set<Map.Entry<String, Character>> entries = getProfileResponse.getResponse().getCharacters().getData().entrySet();
            Integer overallTime = 0;
            List<CompletableFuture> cfs = new ArrayList<>();
            for (Map.Entry<String, Character> entry : entries) {
                overallTime += entry.getValue().getMinutesPlayedTotal();
                CompletableFuture<Object> cf = CompletableFuture.supplyAsync(() -> {
                    CharacterModel character = new CharacterModel();
                    character.setClassName(service.getClassNameByHash(entry.getValue().getClassHash()));
                    character.setLight(entry.getValue().getLight());
                    GetHistoricalStatsResponse statsResponse = new GetHistoricalStatsExecutor().executeMethod(new GetHistoricalStatsRequest().setCharacterId(entry.getValue().getCharacterId()).setDestinyMembershipId(membershipId));
                    DestinyHistoricalStatsValue allPvp = statsResponse.getResponse().getAllPvP();
                    DestinyHistoricalStatsValueDictionary allTime = allPvp.getAllTime();
                    if (allTime != null) {
                        character.setKd(allTime.getKillsDeathsRatio().getDisplayValue());
                        character.setKda(allTime.getKillsDeathsAssists().getDisplayValue());
                    }
                    model.getCharacters().add(character);
                    return null;
                });
                cfs.add(cf);
            }
            Integer hours = overallTime / 60;
            Integer minutes = overallTime - hours * 60;
            model.setHours(hours);
            model.setMinutes(minutes);
            CompletableFuture.allOf(cfs.toArray(new CompletableFuture[0])).join();
            long endTime = System.currentTimeMillis();
            log.info((endTime-startTime));
            try {
                bot.execute(new SendMessage(chatId, model.toString()).setParseMode(ParseMode.MARKDOWN));
            } catch (TelegramApiException e) {
                log.error(e, e);
            }
        } else {
            try {
                bot.execute(dm.unknownError(chatId.toString()));
            } catch (TelegramApiException e) {
                log.error(e, e);
            }
        }
        finished = true;
    }

    @Override
    public Boolean isFinished() {
        return finished;
    }

    private class ResponseModel {
        private Integer hours;
        private Integer minutes;
        private List<CharacterModel> characters;

        public Integer getHours() {
            return hours;
        }

        public ResponseModel setHours(Integer hours) {
            this.hours = hours;
            return this;
        }

        public Integer getMinutes() {
            return minutes;
        }

        public ResponseModel setMinutes(Integer minutes) {
            this.minutes = minutes;
            return this;
        }

        public List<CharacterModel> getCharacters() {
            if (characters == null) {
                characters = new ArrayList<>();
            }
            return characters;
        }

        @Override
        public String toString() {
            String s = String.format("Время, потраченное на дестини: %d часов %d минут\n\n", hours, minutes);
            s += "Лайт / КД / КДА твоих персонажей:\n";
            for (CharacterModel entry : characters) {
                s += String.format("*%s*: `%d / %s / %s`\n", entry.getClassName(), entry.getLight(), entry.getKd()==null?"нет":entry.getKd(), entry.getKda()==null?"нет":entry.getKda());
            }
            return s;
        }
    }

    private class CharacterModel {
        private String className;
        private Integer light;
        private String kd;
        private String kda;

        public String getClassName() {
            return className;
        }

        public CharacterModel setClassName(String className) {
            this.className = className;
            return this;
        }

        public Integer getLight() {
            return light;
        }

        public CharacterModel setLight(Integer light) {
            this.light = light;
            return this;
        }

        public String getKd() {
            return kd;
        }

        public CharacterModel setKd(String kd) {
            this.kd = kd;
            return this;
        }

        public String getKda() {
            return kda;
        }

        public CharacterModel setKda(String kda) {
            this.kda = kda;
            return this;
        }
    }

}
