package com.vdsirotkin.telegram.resendbot.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConfigManager {

    @Value("${bot_token}")
    public String botToken;

    @Value("${channel_name}")
    public String channelName;

    @Value("${bot_username}")
    public String botUsername;

    @Value("${jndi_name}")
    public String jndiName;

    @Value("${db.directory.path}")
    public String dbDirPath;

    @Value("${bungie_api_key}")
    private String bungieApiKey;

    public String getBotToken() {
        return botToken;
    }

    public String getChannelName() {
        return channelName;
    }

    public String getBotUsername() {
        return botUsername;
    }

    public String getJndiName() {
        return jndiName;
    }

    public String getDbDirPath() {
        return dbDirPath;
    }

    public String getBungieApiKey() {
        return bungieApiKey;
    }
}