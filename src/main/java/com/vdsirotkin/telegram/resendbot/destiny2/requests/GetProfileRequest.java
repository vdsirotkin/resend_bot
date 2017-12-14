package com.vdsirotkin.telegram.resendbot.destiny2.requests;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by vitalijsirotkin on 08.09.17.
 */
public class GetProfileRequest {

    private Long destinyMembershipId;
    private Integer membershipType;
    private List<Integer> components;

    public Long getDestinyMembershipId() {
        return destinyMembershipId;
    }

    public GetProfileRequest setDestinyMembershipId(Long destinyMembershipId) {
        this.destinyMembershipId = destinyMembershipId;
        return this;
    }

    public Integer getMembershipType() {
        return membershipType;
    }

    public GetProfileRequest setMembershipType(Integer membershipType) {
        this.membershipType = membershipType;
        return this;
    }

    public List<Integer> getComponents() {
        if (components == null) {
            components = new ArrayList<>();
        }
        return components;
    }

    public String getComponentsAsString() {
        return components.stream().map(Object::toString).collect(Collectors.joining(","));
    }
}
