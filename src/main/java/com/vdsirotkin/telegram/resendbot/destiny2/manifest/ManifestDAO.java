package com.vdsirotkin.telegram.resendbot.destiny2.manifest;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.AbstractMap;
import java.util.HashMap;

/**
 * Created by vitalijsirotkin on 10.09.17.
 */
@Component
public class ManifestDAO {

    @Autowired
    private ManifestHelper helper;

    public JsonObject getTableContentById(Tables table, Long id) {
        JdbcTemplate template = new JdbcTemplate(helper.getManifestDatasource());
        String query = String.format("select json from %s where id=%d or id=%d-4294967296", table.toString(), id, id);
        String json = template.queryForObject(query, (rs, i) -> rs.getString(1));
        return new JsonParser().parse(json).getAsJsonObject();
    }

    static enum Tables {
        DestinyClassDefinition
    }

}
