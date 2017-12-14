package com.vdsirotkin.telegram.resendbot.handlers;

import com.vdsirotkin.telegram.resendbot.ResendBot;
import com.vdsirotkin.telegram.resendbot.utils.DefaultMessages;
import com.vdsirotkin.telegram.resendbot.utils.EventHelper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.methods.groupadministration.GetChat;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.exceptions.TelegramApiException;

/**
 * Created by vitalijsirotkin on 22.10.16.
 */
@Component
@Scope("prototype")
public class StartHandler implements Handler {

    @Autowired
    private ResendBot bot;

    @Autowired
    private DefaultMessages dm;

    @Autowired
    private EventHelper eventHelper;

    private static final Logger log = Logger.getLogger("StartHandler");
    private Boolean isFinished = Boolean.FALSE;
    private Integer step = 1;
    private Integer eventId = null;

    public StartHandler() {

    }

    public void handleMessage(Update update) {
        String chatId = update.getMessage().getChatId().toString();
        String text = update.getMessage().getText();
        switch (step) {
            case 1:
                try {
                    String[] split = text.split(" ");
                    if (split.length > 1) {
                        this.eventId = Integer.parseInt(split[1]);
                    }
                    bot.execute(dm.getStartMessage(chatId));
                    ++step;
                } catch (TelegramApiException e) {
                    log.error(e, e);
                }
                break;
            case 2:
                try {
                    if (text.equals("/nick")) {
                        Chat chat = bot.execute(new GetChat(chatId));
                        String userName = chat.getUserName();
                        if (userName == null) {
                            bot.execute(dm.getWrongNickname(chatId));
                            return;
                        } else {
                            text = userName;
                        }
                    }
                    bot.getDao().saveNickname(chatId, text);
                    bot.execute(dm.getThanksMessage(chatId, text));
                    if (eventId != null) {
                        EventHelper.Status status = eventHelper.joinEvent(chatId, eventId);
                        if (status.equals(EventHelper.Status.JOINED)) {
                            bot.execute(dm.getSuccessJoin(chatId));
                        }
                    }
                    isFinished = Boolean.TRUE;
                } catch (TelegramApiException e) {
                    log.error(e, e);
                }
                break;
            default:
                break;
        }
    }

    public Boolean isFinished() {
        return isFinished;
    }
}
