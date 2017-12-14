package com.vdsirotkin.telegram.resendbot.destiny2.domain.stats;

/**
 * Created by vitalijsirotkin on 16.09.17.
 */
public class DestinyHistoricalStatsValueDictionary {

    private StatValue killsDeathsRatio;
    private StatValue killsDeathsAssists;

    public StatValue getKillsDeathsRatio() {
        return killsDeathsRatio;
    }

    public DestinyHistoricalStatsValueDictionary setKillsDeathsRatio(StatValue killsDeathsRatio) {
        this.killsDeathsRatio = killsDeathsRatio;
        return this;
    }

    public StatValue getKillsDeathsAssists() {
        return killsDeathsAssists;
    }

    public DestinyHistoricalStatsValueDictionary setKillsDeathsAssists(StatValue killsDeathsAssists) {
        this.killsDeathsAssists = killsDeathsAssists;
        return this;
    }
}
