package com.vdsirotkin.telegram.resendbot.destiny2.manifest;

import com.vdsirotkin.telegram.resendbot.ResendBot;
import com.vdsirotkin.telegram.resendbot.db.DAO;
import com.vdsirotkin.telegram.resendbot.destiny2.executors.GetDestinyManifestExecutor;
import com.vdsirotkin.telegram.resendbot.destiny2.responses.GetDestinyManifestResponse;
import com.vdsirotkin.telegram.resendbot.utils.DefaultMessages;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.exceptions.TelegramApiException;

/**
 * Created by vitalijsirotkin on 10.09.17.
 */
@Service
public class ManifestScheduler {
    private static final Logger log = Logger.getLogger(ManifestScheduler.class);

    @Autowired
    private ManifestHelper helper;

    @Autowired
    private ResendBot bot;

    @Autowired
    private DefaultMessages dm;

    @Autowired
    private DAO dao;

    @Scheduled(fixedRate = 30*60*1000)
//    @Scheduled(fixedRate = 60*1000)
    public void updateManifest() {
        GetDestinyManifestResponse response = new GetDestinyManifestExecutor().executeMethod(null);
        String currentDbVersion = dao.getCurrentDbVersion();
        String newVersion = response.getResponse().getVersion();
        if (!currentDbVersion.equals(newVersion)) {
            String url = response.getResponse().getMobileWorldContentPaths().get("ru");
            helper.saveDatabase(url, newVersion);
            helper.clearCache();
            log.info("Saved new database");
            try {
                bot.execute(dm.serviceMessage("База обновлена на новую версию: " + newVersion));
            } catch (TelegramApiException e) {
                log.error(e, e);
            }
        }
    }

}
