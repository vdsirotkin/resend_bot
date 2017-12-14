package com.vdsirotkin.telegram.resendbot.destiny2.executors;

import com.vdsirotkin.telegram.resendbot.destiny2.responses.GetDestinyManifestResponse;
import org.apache.http.client.methods.HttpGet;

/**
 * Created by vitalijsirotkin on 10.09.17.
 */
public class GetDestinyManifestExecutor extends BaseExecutor<Object, GetDestinyManifestResponse> {

    @Override
    public GetDestinyManifestResponse executeMethod(Object request) {
        HttpGet get = new HttpGet(BASE_URL + "/Destiny2/Manifest/");
        GetDestinyManifestResponse response = execute(get, GetDestinyManifestResponse.class);
        return response;
    }

}
