package com.vdsirotkin.telegram.resendbot;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import com.vdsirotkin.telegram.resendbot.db.DAO;
import com.vdsirotkin.telegram.resendbot.handlers.Handler;
import com.vdsirotkin.telegram.resendbot.handlers.HandlerFactory;
import com.vdsirotkin.telegram.resendbot.handlers.SendToChannelHandler;
import com.vdsirotkin.telegram.resendbot.handlers.StartHandler;
import com.vdsirotkin.telegram.resendbot.model.Event;
import com.vdsirotkin.telegram.resendbot.utils.ConfigManager;
import com.vdsirotkin.telegram.resendbot.utils.DefaultMessages;
import com.vdsirotkin.telegram.resendbot.utils.EventHelper;
import com.vdsirotkin.telegram.resendbot.web.SpringConfiguration;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.api.objects.CallbackQuery;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.naming.spi.InitialContextFactory;
import javax.naming.spi.InitialContextFactoryBuilder;
import javax.naming.spi.NamingManager;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * Created by vitalijsirotkin on 22.10.16.
 */
@Component
public class ResendBot extends TelegramLongPollingBot {

    private static final Logger log = Logger.getLogger("ResendBot");

    public ResendBot() {

    }

    @Autowired
    private DAO dao;

    @Autowired
    private HandlerFactory factory;

    @Autowired
    private DefaultMessages dm;

    @Autowired
    private EventHelper eh;

    @Autowired
    private ConfigManager cm;

    private Set<Event> eventsList = new HashSet<>();
    private HashMap<Long, Handler> userHandlerMap = new HashMap<>();


    @Override
    public void onUpdateReceived(Update update) {
//        long start = System.currentTimeMillis();
//        String nick = dao.getNickName(update.getMessage().getChatId().toString());
//        long end = System.currentTimeMillis();
//        log.info((end - start) + " " + nick);
        CallbackQuery callbackQuery = update.getCallbackQuery();
        if (callbackQuery == null) {
            String text = update.getMessage().getText();
            Long chatId = update.getMessage().getChatId();
            Handler handler = userHandlerMap.get(chatId);
            if (!text.equals("/cancel")) {
                if (handler == null) {
                    Handler newHandler = factory.getHandlerByText(text);
                    if (newHandler != null) {
                        newHandler.handleMessage(update);
                        if (!newHandler.isFinished()) {
                            userHandlerMap.put(chatId, newHandler);
                        }
                    } else {
                        try {
                            execute(dm.getCantFindCommand(chatId.toString()));
                        } catch (TelegramApiException e) {
                            log.error(e, e);
                        }
                    }
                } else {
                    handler.handleMessage(update);
                    if (handler.isFinished()) {
                        userHandlerMap.remove(chatId);
                    }
                }
            } else {
                try {
                    userHandlerMap.remove(chatId);
                    execute(dm.getCancelCommand(chatId.toString()));
                } catch (TelegramApiException e) {
                    log.error(e, e);
                }
            }
        } else {
            String eventId = callbackQuery.getData();
            try {
                EventHelper.Status status = eh.joinEvent(callbackQuery.getFrom().getId().toString(), Integer.parseInt(eventId));
                AnswerCallbackQuery query = new AnswerCallbackQuery().setCallbackQueryId(callbackQuery.getId()).setShowAlert(true);
                switch (status) {
                    case ALREADY_JOINED:
                        execute(query.setText("Либо событие просрочилось, либо вы создатель этого события"));
                        break;
                    case JOINED:
                        execute(query.setText("Вы успешно присоединились к событию"));
                        break;
                    case LEAVED:
                        execute(query.setText("Вы успешно отсоединились от события"));
                        break;
                    case NOT_REGISTRED:
                        execute(query.setUrl(String.format("t.me/%s?start=%s", cm.getBotUsername(), eventId)));
                        break;
                    case VOLKODAV:
                        execute(query.setText("Хуй тебе в рот))))"));
                        break;
                }
            } catch (TelegramApiException e) {
                log.error(e, e);
            }
        }
    }

    @Override
    public void onClosing() {

    }

    public Set<Event> getEventsList() {
        return eventsList;
    }

    @Override
    public String getBotToken() {
        return cm.getBotToken();
    }

    @Override
    public String getBotUsername() {
        return "ResendBot";
    }

    public DAO getDao() {
        return dao;
    }
}
