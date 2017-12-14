package com.vdsirotkin.telegram.resendbot.destiny2.domain.profile;

/**
 * Created by vitalijsirotkin on 08.09.17.
 */
public class DestinyProfile {

    private CharacterDictionary characters;

    public CharacterDictionary getCharacters() {
        return characters;
    }

    public DestinyProfile setCharacters(CharacterDictionary characters) {
        this.characters = characters;
        return this;
    }
}
