package com.vdsirotkin.telegram.resendbot.handlers;

import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Component;

/**
 * Created by vitalijsirotkin on 08.09.17.
 */
@Component
public abstract class HandlerFactory {

    public Handler getHandlerByText(String s) {
        if (s.contains("/event")) {
            return sendToChannelHandler();
        } else if (s.contains("/start")) {
            return startHandler();
        } else if (s.contains("/wasted")) {
            return wastedOnDestiny2Handler();
        }
        return null;
    }

    @Lookup
    abstract SendToChannelHandler sendToChannelHandler();

    @Lookup
    abstract StartHandler startHandler();

    @Lookup
    abstract WastedOnDestiny2Handler wastedOnDestiny2Handler();
}
