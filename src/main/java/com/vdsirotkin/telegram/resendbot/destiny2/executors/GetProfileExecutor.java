package com.vdsirotkin.telegram.resendbot.destiny2.executors;

import com.vdsirotkin.telegram.resendbot.destiny2.requests.GetProfileRequest;
import com.vdsirotkin.telegram.resendbot.destiny2.responses.GetProfileResponse;
import org.apache.http.client.methods.HttpGet;

/**
 * Created by vitalijsirotkin on 08.09.17.
 */
public class GetProfileExecutor extends BaseExecutor<GetProfileRequest, GetProfileResponse> {


    @Override
    public GetProfileResponse executeMethod(GetProfileRequest request) {
        String url = String.format("/Destiny2/%d/Profile/%d/?components=%s", request.getMembershipType(), request.getDestinyMembershipId(), request.getComponentsAsString());
        HttpGet get = new HttpGet(BASE_URL + url);
        return execute(get, GetProfileResponse.class);
    }
}
