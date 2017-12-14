package com.vdsirotkin.telegram.resendbot.utils;

import com.vdsirotkin.telegram.resendbot.ResendBot;
import com.vdsirotkin.telegram.resendbot.db.DAO;
import com.vdsirotkin.telegram.resendbot.model.Event;
import com.vdsirotkin.telegram.resendbot.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.api.methods.groupadministration.GetChat;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Created by vitaly on 25.10.16.
 */
@Component
public class EventHelper {

    @Autowired
    private ResendBot bot;

    @Autowired
    private DefaultMessages dm;

    @Autowired
    private DAO dao;

    @Autowired
    private ConfigManager cm;

    public Status joinEvent(String chatId, Integer gameId) throws TelegramApiException {
        String nick = dao.getNickName(chatId);
        Status status = Status.ALREADY_JOINED;
        if (!nick.isEmpty()) {
            Optional<Event> first = bot.getEventsList().stream().filter(event -> event.getGameId().equals(gameId)).findFirst();
            if (first.isPresent()) {
                Event event = first.get();
                if (!event.getParent().getChatId().equals(chatId)) {
                    Optional<User> any = event.getParticipants().stream().filter(user -> user.getChatId().equals(chatId)).findAny();
                    if (any.isPresent()) {
                        if (chatId.equals(String.valueOf(290138667L))) {
                            status = Status.VOLKODAV;
                        } else {
                            event.getParticipants().remove(any.get());
                            bot.execute(dm.getLeavedYourEvent(event.getParent().getChatId(), any.get().toString()));
                            status = Status.LEAVED;
                        }
                    } else {
                        Chat chat = bot.execute(new GetChat().setChatId(chatId));
                        String name = "";
                        if (chat != null) {
                            if (chat.getUserName() != null) {
                                name = "@" + chat.getUserName();
                            } else if (chat.getFirstName() != null || chat.getLastName() != null) {
                                name = (chat.getFirstName() != null ? chat.getFirstName() + " " : "");
                                name += (chat.getLastName() != null ? chat.getLastName() + " " : "");
                            }
                        }
                        User e = new User(chatId, name, nick);
                        event.getParticipants().add(e);
                        bot.execute(dm.getJoinedYourEvent(event.getParent().getChatId(), e.toString()));
                        status = Status.JOINED;
                    }
                    bot.execute(new EditMessageText()
                            .setText(event.toString())
                            .setMessageId(event.getMessageId())
                            .setChatId(cm.getChannelName())
                            .setReplyMarkup(new InlineKeyboardMarkup().setKeyboard(getDefaultButton(event.getGameId())))
                            .disableWebPagePreview());
                }
            }
        } else {
            status = Status.NOT_REGISTRED;
        }
        return status;
    }

    public static List<List<InlineKeyboardButton>> getDefaultButton(Integer gameId) {
        return Collections.singletonList(Collections.singletonList(new InlineKeyboardButton().setCallbackData(String.valueOf(gameId)).setText("Присоединиться/отсоединиться")));
    }

    public static enum Status {
        ALREADY_JOINED,
        JOINED,
        LEAVED,
        NOT_REGISTRED,
        VOLKODAV
    }

}
