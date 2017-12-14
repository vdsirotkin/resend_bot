package com.vdsirotkin.telegram.resendbot.destiny;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.io.IOException;

/**
 * Created by vitalijsirotkin on 26.03.17.
 */
@RestController
public class DestinyEventsController {
    private static final Logger log = Logger.getLogger(DestinyEventsController.class);

    @Autowired
    DestinyEventCacher dec;

    @Autowired
    DestinyEventScheduler des;

    @RequestMapping(value = "/get.events", method = RequestMethod.GET)
    public ResponseEntity<Object> getEvents() throws IOException {
        return ResponseEntity.ok(dec.getPublicEvents());
    }

    @RequestMapping(value = "/update.events", method = RequestMethod.GET)
    public void updatesEvents() {
        dec.clearCache();
    }

    @GetMapping(value = "/send.to.channel")
    public void sendToChannel() {
        try {
            des.updateEventCache();
        } catch (IOException | TelegramApiException e) {
            log.error(e, e);
        }
    }

}
