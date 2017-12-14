package com.vdsirotkin.telegram.resendbot.web;

import com.vdsirotkin.telegram.resendbot.ResendBot;
import com.vdsirotkin.telegram.resendbot.destiny2.executors.BaseExecutor;
import com.vdsirotkin.telegram.resendbot.utils.ConfigManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.ApiContext;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.generics.BotSession;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * Created by vitalijsirotkin on 25.03.17.
 */
@Service
public class BotInitializer {

    public static BotSession session;

    @Autowired
    private ResendBot bot;

    @Autowired
    ConfigManager cm;

    @PostConstruct
    private void init() throws TelegramApiRequestException {
        TelegramBotsApi api = new TelegramBotsApi();
        session = api.registerBot(bot);
        BaseExecutor.setBungieKey(cm.getBungieApiKey());
    }

    @PreDestroy
    private void stop() {
        session.stop();
    }

}
