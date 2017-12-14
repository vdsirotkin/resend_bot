package com.vdsirotkin.telegram.resendbot.destiny;

import com.vdsirotkin.telegram.resendbot.ResendBot;
import com.vdsirotkin.telegram.resendbot.utils.ConfigManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.api.methods.ParseMode;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.io.IOException;

/**
 * Created by vitalijsirotkin on 26.03.17.
 */
@Service
public class DestinyEventScheduler {

    private static final Logger log = Logger.getLogger(DestinyEventScheduler.class);

    @Autowired
    DestinyEventCacher dec;

    @Autowired
    ResendBot bot;

    @Autowired
    private ConfigManager cm;


//    @Scheduled(cron = "0 10 12 * * 2")
    public void updateEventCache() throws IOException, TelegramApiException {
        dec.clearCache();
        AdvisorData data = dec.getPublicEvents();
        SendMessage message = new SendMessage();
        StringBuilder text = new StringBuilder("Недельное обновление:\n\n");
        AdvisorData.Strike nf = data.getNightfall();
        text.append("*Nightfall:*\n").append("_").append(nf.getName()).append("_\n");
        nf.getModifiers().forEach(s -> {
            text.append(s).append("\n");
        });
        AdvisorData.Strike sh = data.getSivaHeroic();
        text.append("\n*Heroic strike*:\n").append("_").append(sh.getName()).append("_\n");
        sh.getModifiers().forEach(s -> {
            text.append(s).append("\n");
        });
        AdvisorData.Strike wr = data.getWeeklyRaid();
        text.append("\n*Weekly raid*:\n").append("_").append(wr.getName()).append("_\n");
        wr.getModifiers().forEach(s -> {
            text.append(s).append("\n");
        });
        text.append("\n*Weekly crucible:*\n").append("_").append(data.getWeeklyCrucible()).append("_");
        message.setText(text.toString());
        message.setChatId(cm.getChannelName());
        message.setParseMode(ParseMode.MARKDOWN);
        bot.execute(message);
        log.info("cleared");
    }

}
