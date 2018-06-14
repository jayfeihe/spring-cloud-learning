package com.jay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import zipkin.server.internal.EnableZipkinServer;
import zipkin.storage.mysql.MySQLStorage;

import javax.sql.DataSource;

@SpringBootApplication
@EnableZipkinServer
public class SleuthZipkinServerApplication {

    @Bean
    public MySQLStorage mySQLStorage(DataSource dataSource){
        return MySQLStorage.builder().datasource(dataSource).executor(Runnable::run).build();
    }

    public static void main(String[] args) {
        SpringApplication.run(SleuthZipkinServerApplication.class, args);
    }
}
