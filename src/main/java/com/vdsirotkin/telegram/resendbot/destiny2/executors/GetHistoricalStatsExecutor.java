package com.vdsirotkin.telegram.resendbot.destiny2.executors;

import com.vdsirotkin.telegram.resendbot.destiny2.requests.GetHistoricalStatsRequest;
import com.vdsirotkin.telegram.resendbot.destiny2.responses.GetHistoricalStatsResponse;
import org.apache.http.client.methods.HttpGet;

/**
 * Created by vitalijsirotkin on 16.09.17.
 */
public class GetHistoricalStatsExecutor extends BaseExecutor<GetHistoricalStatsRequest, GetHistoricalStatsResponse> {

    @Override
    public GetHistoricalStatsResponse executeMethod(GetHistoricalStatsRequest request) {
        String urlPattern = BASE_URL + "/Destiny2/2/Account/%d/Character/%d/Stats?periodType=AllTime";
        String url = String.format(urlPattern, request.getDestinyMembershipId(), request.getCharacterId());
        return execute(new HttpGet(url), GetHistoricalStatsResponse.class);
    }
}
