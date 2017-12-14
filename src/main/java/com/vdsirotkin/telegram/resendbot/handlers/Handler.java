package com.vdsirotkin.telegram.resendbot.handlers;

import org.telegram.telegrambots.api.objects.Update;

/**
 * Created by vitalijsirotkin on 25.03.17.
 */
public interface Handler {

    void handleMessage(Update update);

    Boolean isFinished();
}
