package com.vdsirotkin.telegram.resendbot.destiny2.domain.stats;

import com.google.gson.JsonObject;

/**
 * Created by vitalijsirotkin on 16.09.17.
 */
public class StatValue {

    private String statId;
    private JsonObject basic;

    public String getStatId() {
        return statId;
    }

    public StatValue setStatId(String statId) {
        this.statId = statId;
        return this;
    }

    public JsonObject getBasic() {
        return basic;
    }

    public StatValue setBasic(JsonObject basic) {
        this.basic = basic;
        return this;
    }

    public String getDisplayValue() {
        return basic.get("displayValue").getAsString();
    }
}
