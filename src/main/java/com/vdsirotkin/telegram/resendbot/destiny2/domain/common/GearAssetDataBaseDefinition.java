package com.vdsirotkin.telegram.resendbot.destiny2.domain.common;

/**
 * Created by vitalijsirotkin on 10.09.17.
 */
public class GearAssetDataBaseDefinition {

    private Integer version;
    private String path;

    public Integer getVersion() {
        return version;
    }

    public GearAssetDataBaseDefinition setVersion(Integer version) {
        this.version = version;
        return this;
    }

    public String getPath() {
        return path;
    }

    public GearAssetDataBaseDefinition setPath(String path) {
        this.path = path;
        return this;
    }
}
