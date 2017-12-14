package com.vdsirotkin.telegram.resendbot.db;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

/**
 * Created by vitalijsirotkin on 23.10.16.
 */
public class DAO {

    private static final Logger log = Logger.getLogger("DAO");
    private DataSource source;
    private JdbcTemplate template;

    public DAO(DataSource source) {
        this.source = source;
        this.template = new JdbcTemplate(source);
    }

    public void saveNickname(String chatId, String psnNick) {
        String nickName = getNickName(chatId);
        if (nickName.isEmpty()) {
            try (Connection connection = source.getConnection();
                 PreparedStatement statement = connection.prepareStatement("insert into psn_nicks (pn_chat_id, pn_psn_nick) VALUES (?,?)")) {
                statement.setString(1, chatId);
                statement.setString(2, psnNick);
                statement.executeUpdate();
            } catch (SQLException e) {
                log.error(e,e);
            }
        } else {
            try (Connection conn = source.getConnection();
                PreparedStatement ps = conn.prepareStatement("update psn_nicks SET pn_psn_nick= ? where pn_chat_id = ?")) {
                ps.setString(1, psnNick);
                ps.setString(2, chatId);
                ps.executeUpdate();
            } catch (SQLException e) {
                log.error(e, e);
            }
        }
    }

    public HashMap<String, String> getNicknames() {
        HashMap<String, String> map = new HashMap<>();
        try (Connection connection = source.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery("select * from psn_nicks");
            while (rs.next()) {
                map.put(rs.getString("pn_chat_id"), rs.getString("pn_psn_nick"));
            }
        } catch (SQLException e) {
            log.error(e,e);
        }
        return map;
    }

    public String getNickName(String chatId) {
        String nickName = "";
        List<String> list = template.query("SELECT pn_psn_nick FROM psn_nicks WHERE pn_chat_id = ?", new Object[]{chatId}, (resultSet, i) -> resultSet.getString(1));
        Optional<String> first = list.stream().findFirst();
        if (first.isPresent()) {
            nickName = first.get();
        }
        return nickName;
    }


    public void saveCurrentDbFileName(String fileName) {
        updateSettings("DB_FILE_NAME", fileName);
    }

    public String getCurrentDbFileName() {
        return getSettingsValue("DB_FILE_NAME");
    }

    public void saveCurrentDbVersion(String version) {
        updateSettings("DB_VERSION", version);
    }

    public String getCurrentDbVersion() {
        return getSettingsValue("DB_VERSION");
    }

    private void updateSettings(String key, String value) {
        JdbcTemplate template = new JdbcTemplate(source);
        template.update("UPDATE settings SET ST_VALUE=? WHERE ST_KEY=?", value, key);
    }

    private String getSettingsValue(String key) {
        JdbcTemplate template = new JdbcTemplate(source);
        List<Map.Entry<String, String>> result = template.query("SELECT * FROM settings", (rs, i) -> new AbstractMap.SimpleEntry<>(rs.getString(1), rs.getString(2)));
        Optional<Map.Entry<String, String>> name = result.stream().filter(e -> e.getKey().equals(key)).findFirst();
        if (name.isPresent()) {
            return name.get().getValue();
        } else {
            return null;
        }
    }
}
