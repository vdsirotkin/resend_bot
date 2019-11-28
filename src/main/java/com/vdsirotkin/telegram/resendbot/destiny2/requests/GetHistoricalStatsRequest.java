package com.vdsirotkin.telegram.resendbot.destiny2.requests;

/**
 * Created by vitalijsirotkin on 16.09.17.
 */
public class GetHistoricalStatsRequest {

    private Integer destinyMembershipType;
    private Long destinyMembershipId;
    private Long characterId;

    public Integer getDestinyMembershipType() {
        return destinyMembershipType;
    }

    public GetHistoricalStatsRequest setDestinyMembershipType(Integer destinyMembershipType) {
        this.destinyMembershipType = destinyMembershipType;
        return this;
    }

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
