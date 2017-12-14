package com.vdsirotkin.telegram.resendbot.destiny2.responses;

import com.vdsirotkin.telegram.resendbot.destiny2.domain.profile.DestinyProfile;

/**
 * Created by vitalijsirotkin on 08.09.17.
 */
public class GetProfileResponse extends BaseResponse {

    private DestinyProfile Response;

    public DestinyProfile getResponse() {
        return Response;
    }

    public GetProfileResponse setResponse(DestinyProfile response) {
        Response = response;
        return this;
    }
}
