package com.vdsirotkin.telegram.resendbot.destiny2.domain.stats;

/**
 * Created by vitalijsirotkin on 16.09.17.
 */
public class DestinyHistoricalStatsByPeriod {

    private DestinyHistoricalStatsValue allPvP;

    public DestinyHistoricalStatsValue getAllPvP() {
        return allPvP;
    }

    public DestinyHistoricalStatsByPeriod setAllPvP(DestinyHistoricalStatsValue allPvP) {
        this.allPvP = allPvP;
        return this;
    }
}
