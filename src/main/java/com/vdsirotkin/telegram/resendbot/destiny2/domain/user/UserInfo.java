package com.vdsirotkin.telegram.resendbot.destiny2.domain.user;

/**
 * Created by vitalijsirotkin on 08.09.17.
 */
public class UserInfo {

    private String supplementalDisplayName;
    private String iconPath;
    private Integer membershipType;
    private Long membershipId;
    private String displayName;

    public String getSupplementalDisplayName() {
        return supplementalDisplayName;
    }

    public UserInfo setSupplementalDisplayName(String supplementalDisplayName) {
        this.supplementalDisplayName = supplementalDisplayName;
        return this;
    }

    public String getIconPath() {
        return iconPath;
    }

    public UserInfo setIconPath(String iconPath) {
        this.iconPath = iconPath;
        return this;
    }

    public Integer getMembershipType() {
        return membershipType;
    }

    public UserInfo setMembershipType(Integer membershipType) {
        this.membershipType = membershipType;
        return this;
    }

    public Long getMembershipId() {
        return membershipId;
    }

    public UserInfo setMembershipId(Long membershipId) {
        this.membershipId = membershipId;
        return this;
    }

    public String getDisplayName() {
        return displayName;
    }

    public UserInfo setDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }
}
