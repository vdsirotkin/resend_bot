package com.vdsirotkin.telegram.resendbot.destiny2.requests;

import java.util.List;

/**
 * Created by vitalijsirotkin on 08.09.17.
 */
public class SearchDestinyPlayerRequest {

    private Integer membershipType;
    private String nickname;

    public Integer getMembershipType() {
        return membershipType;
    }

    public SearchDestinyPlayerRequest setMembershipType(Integer membershipType) {
        this.membershipType = membershipType;
        return this;
    }

    public String getNickname() {
        return nickname;
    }

    public SearchDestinyPlayerRequest setNickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

}
