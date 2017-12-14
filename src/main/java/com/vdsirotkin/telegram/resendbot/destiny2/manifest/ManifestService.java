package com.vdsirotkin.telegram.resendbot.destiny2.manifest;

import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by vitalijsirotkin on 10.09.17.
 */
@Component
public class ManifestService {

    @Autowired
    private ManifestDAO mDAO;

    public String getClassNameByHash(Long id) {
        JsonObject object = mDAO.getTableContentById(ManifestDAO.Tables.DestinyClassDefinition, id);
        return object.getAsJsonObject("displayProperties").get("name").getAsString();
    }

}
