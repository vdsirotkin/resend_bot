package com.vdsirotkin.telegram.resendbot.destiny2.requests;

/**
 * Created by vitalijsirotkin on 16.09.17.
 */
public class GetHistoricalStatsRequest {

    private Long destinyMembershipId;
    private Long characterId;

    public Long getDestinyMembershipId() {
        return destinyMembershipId;
    }

    public GetHistoricalStatsRequest setDestinyMembershipId(Long destinyMembershipId) {
        this.destinyMembershipId = destinyMembershipId;
        return this;
    }

    public Long getCharacterId() {
        return characterId;
    }

    public GetHistoricalStatsRequest setCharacterId(Long characterId) {
        this.characterId = characterId;
        return this;
    }
}
