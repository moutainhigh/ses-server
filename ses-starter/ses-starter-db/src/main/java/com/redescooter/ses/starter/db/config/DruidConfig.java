package com.redescooter.ses.starter.db.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 22/1/2020 4:58 下午
 * @ClassName: DruidConfig
 * @Function: TODO
 */
@Configuration
@ServletComponentScan
public class DruidConfig {

    @Bean("dataSource")
    @ConfigurationProperties(prefix ="spring.datasource.druid")
    public DataSource druidDataSource() {
        return new DruidDataSource();
    }
}
