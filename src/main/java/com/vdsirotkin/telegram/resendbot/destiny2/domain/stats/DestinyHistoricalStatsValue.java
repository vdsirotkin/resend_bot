package com.vdsirotkin.telegram.resendbot.destiny2.domain.stats;

/**
 * Created by vitalijsirotkin on 16.09.17.
 */
public class DestinyHistoricalStatsValue {

    private DestinyHistoricalStatsValueDictionary allTime;

    public DestinyHistoricalStatsValueDictionary getAllTime() {
        return allTime;
    }

    public DestinyHistoricalStatsValue setAllTime(DestinyHistoricalStatsValueDictionary allTime) {
        this.allTime = allTime;
        return this;
    }
}
