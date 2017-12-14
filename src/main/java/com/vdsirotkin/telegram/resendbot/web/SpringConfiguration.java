package com.vdsirotkin.telegram.resendbot.web;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import com.vdsirotkin.telegram.resendbot.db.DAO;
import com.vdsirotkin.telegram.resendbot.handlers.StartHandler;
import com.vdsirotkin.telegram.resendbot.utils.ConfigManager;
import com.vdsirotkin.telegram.resendbot.utils.DefaultMessages;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.DataSource;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.Properties;

/**
 * Created by vitalijsirotkin on 25.03.17.
 */
@Configuration
@ComponentScan("com.vdsirotkin")
@PropertySource("classpath:config_${spring.profiles.active}.properties")
@EnableWebMvc
@EnableCaching
@EnableScheduling
public class SpringConfiguration {
    private static final Logger log = Logger.getLogger(SpringConfiguration.class);

    @Autowired
    private ConfigManager cm;

    @Bean
    @Profile("prod")
    public DAO dao() {
        DataSource dataSource = new JndiDataSourceLookup().getDataSource(cm.getJndiName());
        return new DAO(dataSource);
    }

    @Bean
    @Profile("dev")
    public DAO daoDev() {
        try {
            MysqlDataSource source = new MysqlDataSource();
            Properties properties = PropertiesLoaderUtils.loadProperties(new ClassPathResource("/config_dev.properties"));
            source.setServerName((String) properties.get("mysql.host"));
            source.setPort(1433);
            source.setUser((String) properties.get("mysql.user"));
            source.setPassword((String) properties.get("mysql.password"));
            return new DAO(source);
        } catch (IOException e) {
            log.error(e, e);
        }
        return new DAO(null);
    }

    @Bean
    public SimpleCacheManager cacheManager() {
        SimpleCacheManager manager = new SimpleCacheManager();
        manager.setCaches(Arrays.asList(
                new ConcurrentMapCache("nickname"),
                new ConcurrentMapCache("publicevents"),
                new ConcurrentMapCache("sqlite")
        ));
        return manager;
    }
}
