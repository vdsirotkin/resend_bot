package com.vdsirotkin.telegram.resendbot.destiny2.executors;

import com.vdsirotkin.telegram.resendbot.destiny2.requests.SearchDestinyPlayerRequest;
import com.vdsirotkin.telegram.resendbot.destiny2.responses.SearchDestinyPlayerResponse;
import org.apache.http.client.methods.HttpGet;

/**
 * Created by vitalijsirotkin on 08.09.17.
 */
public class SearchDestinyPlayerExecutor extends BaseExecutor<SearchDestinyPlayerRequest, SearchDestinyPlayerResponse> {

    @Override
    public SearchDestinyPlayerResponse executeMethod(SearchDestinyPlayerRequest request) {
        String url = String.format("/Destiny2/SearchDestinyPlayer/%d/%s/", request.getMembershipType(), request.getNickname());
        HttpGet get = new HttpGet(BASE_URL + url);
        return execute(get, SearchDestinyPlayerResponse.class);
    }
}
