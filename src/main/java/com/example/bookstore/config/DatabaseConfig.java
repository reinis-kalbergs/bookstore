package com.example.bookstore.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {

    @Value("${app.useH2Database}")
    private boolean useH2Database;

    @Bean
    public DataSource dataSource() {
        if (useH2Database) {
            return DataSourceBuilder.create()
                    .driverClassName("org.h2.Driver")
                    .url("jdbc:h2:mem:testdb")
                    .username("user")
                    .password("password")
                    .build();
        } else {
            return DataSourceBuilder.create()
                    .driverClassName("org.postgresql.Driver")
                    .url("jdbc:postgresql://localhost:5433/testdb")
                    .username("user")
                    .password("password")
                    .build();
        }
    }
}
