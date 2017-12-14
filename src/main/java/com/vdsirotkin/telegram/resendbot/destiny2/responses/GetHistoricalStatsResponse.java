package com.vdsirotkin.telegram.resendbot.destiny2.responses;

import com.vdsirotkin.telegram.resendbot.destiny2.domain.stats.DestinyHistoricalStatsByPeriod;

/**
 * Created by vitalijsirotkin on 16.09.17.
 */
public class GetHistoricalStatsResponse extends BaseResponse {

    private DestinyHistoricalStatsByPeriod Response;

    public DestinyHistoricalStatsByPeriod getResponse() {
        return Response;
    }

    public GetHistoricalStatsResponse setResponse(DestinyHistoricalStatsByPeriod response) {
        Response = response;
        return this;
    }
}
