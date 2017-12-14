package com.vdsirotkin.telegram.resendbot.destiny2.domain.profile;

import java.util.Date;

/**
 * Created by vitalijsirotkin on 08.09.17.
 */
public class Character {

    private Long membershipId;
    private Integer membershipType;
    private Long characterId;
    private Date dateLastPlayed;
    private Integer minutesPlayedThisSession;
    private Integer minutesPlayedTotal;
    private Integer light;
    private Long classHash;

    public Long getMembershipId() {
        return membershipId;
    }

    public Character setMembershipId(Long membershipId) {
        this.membershipId = membershipId;
        return this;
    }

    public Integer getMembershipType() {
        return membershipType;
    }

    public Character setMembershipType(Integer membershipType) {
        this.membershipType = membershipType;
        return this;
    }

    public Long getCharacterId() {
        return characterId;
    }

    public Character setCharacterId(Long characterId) {
        this.characterId = characterId;
        return this;
    }

    public Date getDateLastPlayed() {
        return dateLastPlayed;
    }

    public Character setDateLastPlayed(Date dateLastPlayed) {
        this.dateLastPlayed = dateLastPlayed;
        return this;
    }

    public Integer getMinutesPlayedThisSession() {
        return minutesPlayedThisSession;
    }

    public Character setMinutesPlayedThisSession(Integer minutesPlayedThisSession) {
        this.minutesPlayedThisSession = minutesPlayedThisSession;
        return this;
    }

    public Integer getMinutesPlayedTotal() {
        return minutesPlayedTotal;
    }

    public Character setMinutesPlayedTotal(Integer minutesPlayedTotal) {
        this.minutesPlayedTotal = minutesPlayedTotal;
        return this;
    }

    public Integer getLight() {
        return light;
    }

    public Character setLight(Integer light) {
        this.light = light;
        return this;
    }

    public Long getClassHash() {
        return classHash;
    }

    public Character setClassHash(Long classHash) {
        this.classHash = classHash;
        return this;
    }
}
