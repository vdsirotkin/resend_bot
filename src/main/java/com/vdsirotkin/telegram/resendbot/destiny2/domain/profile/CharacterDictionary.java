package com.vdsirotkin.telegram.resendbot.destiny2.domain.profile;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by vitalijsirotkin on 08.09.17.
 */
public class CharacterDictionary {

    Map<String, Character> data;
    Integer privacy;

    public Integer getPrivacy() {
        return privacy;
    }

    public CharacterDictionary setPrivacy(Integer privacy) {
        this.privacy = privacy;
        return this;
    }

    public Map<String, Character> getData() {
        if (data == null) {
            data = new HashMap<>();
        }
        return data;
    }
}
