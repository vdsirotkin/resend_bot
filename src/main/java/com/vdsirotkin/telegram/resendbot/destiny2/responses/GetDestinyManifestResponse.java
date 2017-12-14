package com.vdsirotkin.telegram.resendbot.destiny2.responses;

import com.vdsirotkin.telegram.resendbot.destiny2.domain.common.DestinyManifest;

/**
 * Created by vitalijsirotkin on 10.09.17.
 */
public class GetDestinyManifestResponse extends BaseResponse {

    private DestinyManifest Response;

    public DestinyManifest getResponse() {
        return Response;
    }

    public GetDestinyManifestResponse setResponse(DestinyManifest response) {
        Response = response;
        return this;
    }
}
