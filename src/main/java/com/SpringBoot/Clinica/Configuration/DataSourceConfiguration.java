package com.SpringBoot.Clinica.Configuration;


import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfiguration {


    @Value("${spring.db.url}")
    private String url;
    @Value("${spring.db.username}")
    private String username;
    @Value("${spring.db.password}")
    private String password;
    @Value("${spring.db.driver-class-name}")
    private String driver;

    /**
     *
     * @Info: Configuracion del data source
     * @return: dataSource
     * */
    @Bean
    public DataSource dataSource(){
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(url);
        dataSource.setDriverClassName(driver);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setMaximumPoolSize(3);
        dataSource.setInitializationFailTimeout(10);


        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(){
        return  new JdbcTemplate(dataSource());
    }
}
