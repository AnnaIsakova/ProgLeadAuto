package ua.kiev.prog.config;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;

import javax.sql.DataSource;

public class DbConfig {

     public static DataSource createDataSource(String driver, String url, String userName, String password){
        return DataSourceBuilder.create()
                .username(userName)
                .password(password)
                .url(url)
                .driverClassName(driver)
                .build();
    }

    public static DataSource createDataSource(String url, String userName, String password){
        return DataSourceBuilder.create()
                .username(userName)
                .password(password)
                .url(url)
                .build();
    }
}
