package com.msb.insurance.pob.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.IOException;

@Configuration
@Slf4j
public class DatabaseConfig {

    @Value("${spring.datasource.url}")
    private String dataSourceUrl;

    @Bean
    public void createDatabaseFile() throws IOException {
        String dbPath = dataSourceUrl.replace("jdbc:sqlite:", "");
        File dbFile = new File(dbPath);
        if (!dbFile.exists()) {
            dbFile.getParentFile().mkdirs();
            dbFile.createNewFile();
            log.info("Database file created: " + dbPath);
        } else {
            log.info("Database file already exists: " + dbPath);
        }
    }
}
