package com.vdsirotkin.telegram.resendbot.handlers;

import com.vdsirotkin.telegram.resendbot.ResendBot;
import com.vdsirotkin.telegram.resendbot.model.Event;
import com.vdsirotkin.telegram.resendbot.model.User;
import com.vdsirotkin.telegram.resendbot.utils.ConfigManager;
import com.vdsirotkin.telegram.resendbot.utils.DefaultMessages;
import com.vdsirotkin.telegram.resendbot.utils.EventHelper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.methods.groupadministration.GetChat;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by vitalijsirotkin on 22.10.16.
 */
@Component
@Scope("prototype")
public class SendToChannelHandler implements Handler {

    @Autowired
    private ResendBot bot;

    @Autowired
    private DefaultMessages dm;

    @Autowired
    private ConfigManager cm;

    @Autowired
    Environment environment;

    private static Logger log = Logger.getLogger(SendToChannelHandler.class);
    private Boolean isFinished = Boolean.FALSE;
    private Integer step = 1;

    public SendToChannelHandler() {
    }

    public void handleMessage(Update update) {
        String chatId = update.getMessage().getChatId().toString();
        switch (step) {
            case 1:
                try {
                    bot.execute(dm.getFirstStep(chatId));
                    ++step;
                } catch (TelegramApiException e) {
                    log.error(e, e);
                }
                break;
            case 2:
                try {
                    String text = update.getMessage().getText();
                    if (text.contains("/start") || text.contains("/event")) {
                        bot.execute(new SendMessage().setChatId(update.getMessage().getChatId()).setText("Опять забыл что написал /event раньше?... Попробуй снова."));
                        return;
                    }
                    int gameId = new Random().nextInt() % 10000;
                    Chat chat = bot.execute(new GetChat().setChatId(chatId));
                    StringBuilder sb = new StringBuilder("");
                    if (chat != null) {
                        if (chat.getUserName() != null) {
                            sb.append("@").append(chat.getUserName());
                        } else if (chat.getFirstName() != null || chat.getLastName() != null) {
                            sb.append(chat.getFirstName() != null ? chat.getFirstName() + " " : "");
                            sb.append(chat.getLastName() != null ? chat.getLastName() + " " : "");
                        }
                    }
                    String psnNick = bot.getDao().getNickName(chatId);
                    SendMessage channelMessage = new SendMessage();
                    Event event = new Event().setEventText(text).setGameId(gameId).setParent(new User(chatId, sb.toString(), psnNick));
                    channelMessage.setChatId(cm.getChannelName());
                    channelMessage.setText(event.toString());
                    channelMessage.disableWebPagePreview();
                    if (Arrays.asList(environment.getActiveProfiles()).contains("dev")) {
                        channelMessage.disableNotification();
                    }
                    channelMessage.setReplyMarkup(new InlineKeyboardMarkup().setKeyboard(EventHelper.getDefaultButton(gameId)));
                    Message message = bot.execute(channelMessage);
                    event.setMessageId(message.getMessageId());
                    bot.getEventsList().add(event);
                    bot.execute(dm.getSecondStep(chatId));
                    isFinished = Boolean.TRUE;
                } catch (TelegramApiException e) {
                    log.error(e, e);
                    isFinished = Boolean.TRUE;
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
