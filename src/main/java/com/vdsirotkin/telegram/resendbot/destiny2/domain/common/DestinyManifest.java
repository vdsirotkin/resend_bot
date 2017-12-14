package com.vdsirotkin.telegram.resendbot.destiny2.domain.common;

import java.util.List;
import java.util.Map;

/**
 * Created by vitalijsirotkin on 10.09.17.
 */
public class DestinyManifest {

    private String version;
    private String mobileAssetContentPath;
    private List<GearAssetDataBaseDefinition> mobileGearAssetDataBases;
    private Map<String, String> mobileWorldContentPaths;
    private String mobileClanBannerDatabasePath;
    private Map<String, String> mobileGearCDN;

    public String getVersion() {
        return version;
    }

    public DestinyManifest setVersion(String version) {
        this.version = version;
        return this;
    }

    public String getMobileAssetContentPath() {
        return mobileAssetContentPath;
    }

    public DestinyManifest setMobileAssetContentPath(String mobileAssetContentPath) {
        this.mobileAssetContentPath = mobileAssetContentPath;
        return this;
    }

    public List<GearAssetDataBaseDefinition> getMobileGearAssetDataBases() {
        return mobileGearAssetDataBases;
    }

    public DestinyManifest setMobileGearAssetDataBases(List<GearAssetDataBaseDefinition> mobileGearAssetDataBases) {
        this.mobileGearAssetDataBases = mobileGearAssetDataBases;
        return this;
    }

    public Map<String, String> getMobileWorldContentPaths() {
        return mobileWorldContentPaths;
    }

    public DestinyManifest setMobileWorldContentPaths(Map<String, String> mobileWorldContentPaths) {
        this.mobileWorldContentPaths = mobileWorldContentPaths;
        return this;
    }

    public String getMobileClanBannerDatabasePath() {
        return mobileClanBannerDatabasePath;
    }

    public DestinyManifest setMobileClanBannerDatabasePath(String mobileClanBannerDatabasePath) {
        this.mobileClanBannerDatabasePath = mobileClanBannerDatabasePath;
        return this;
    }

    public Map<String, String> getMobileGearCDN() {
        return mobileGearCDN;
    }

    public DestinyManifest setMobileGearCDN(Map<String, String> mobileGearCDN) {
        this.mobileGearCDN = mobileGearCDN;
        return this;
    }
}
