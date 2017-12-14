package com.vdsirotkin.telegram.resendbot.destiny2.responses;

import com.vdsirotkin.telegram.resendbot.destiny2.domain.user.UserInfo;

import java.util.List;

/**
 * Created by vitalijsirotkin on 08.09.17.
 */
public class SearchDestinyPlayerResponse extends BaseResponse {

    private List<UserInfo> Response;

    public List<UserInfo> getResponse() {
        return Response;
    }

    public SearchDestinyPlayerResponse setResponse(List<UserInfo> response) {
        Response = response;
        return this;
    }
}
