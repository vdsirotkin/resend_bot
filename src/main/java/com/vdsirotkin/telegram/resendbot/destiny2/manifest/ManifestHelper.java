package com.vdsirotkin.telegram.resendbot.destiny2.manifest;

import com.vdsirotkin.telegram.resendbot.db.DAO;
import com.vdsirotkin.telegram.resendbot.destiny2.executors.BaseExecutor;
import com.vdsirotkin.telegram.resendbot.utils.ConfigManager;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;
import org.sqlite.SQLiteDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import java.util.zip.ZipInputStream;

/**
 * Created by vitalijsirotkin on 10.09.17.
 */
@Component
public class ManifestHelper {
    private static final Logger log = Logger.getLogger(ManifestHelper.class);

    @Autowired
    private DAO dao;

    @Autowired
    private ConfigManager cm;

    private static HttpClient httpClient = HttpClientBuilder.create().build();

    public void saveDatabase(String url, String version) {
        HttpGet get = new HttpGet(BaseExecutor.BASE_URL_NO_PLATFORM + url);
        try {
            String fileName = UUID.randomUUID().toString();
            HttpResponse response = httpClient.execute(get);
            Path tempFile = Files.createTempFile(Paths.get(cm.getDbDirPath()), "vds", "vds");
            Files.copy(response.getEntity().getContent(), tempFile, StandardCopyOption.REPLACE_EXISTING);

            ZipInputStream zis = new ZipInputStream(Files.newInputStream(tempFile));
            zis.getNextEntry();
            Path file = Files.createFile(Paths.get(cm.getDbDirPath() + fileName));
            Files.copy(zis, file, StandardCopyOption.REPLACE_EXISTING);

            try {
                clearPreviousFile();
            } catch (Exception e) {
                // ignored
            }
            dao.saveCurrentDbFileName(fileName);
            dao.saveCurrentDbVersion(version);
            Files.delete(tempFile);
        } catch (IOException e) {
            log.error(e, e);
        }
    }

    private void clearPreviousFile() {
        try {
            String fileName = dao.getCurrentDbFileName();
            Files.delete(Paths.get(cm.getDbDirPath() + fileName));
        } catch (IOException e) {
            log.error(e, e);
        }
    }

    @CacheEvict(value = "sqlite", allEntries = true)
    public void clearCache() {

    }

    @Cacheable("sqlite")
    public DataSource getManifestDatasource() {
        String filePath = cm.getDbDirPath().concat(dao.getCurrentDbFileName());
        String url = String.format("jdbc:sqlite:%s", filePath);
        SQLiteDataSource source = new SQLiteDataSource();
        source.setUrl(url);
        return source;
    }


}
