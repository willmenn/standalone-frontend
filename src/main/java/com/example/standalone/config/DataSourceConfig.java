package com.example.standalone.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {
    @Bean
    public DataSource dataSource(DatabaseProperties props) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(props.getDriver());
        dataSource.setUrl(props.getUrl());
        dataSource.setUsername(props.getUser());
        dataSource.setPassword(props.getPass());

        return dataSource;
    }

    @Data
    @Configuration
    @ConfigurationProperties(prefix = "standalone.jdbc")
    public static class DatabaseProperties {
        private String driver;
        private String url;
        private String user;
        private String pass;
    }
}
