package com.redescooter.ses.starter.seata.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.autoconfigure.SpringBootVFS;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.MybatisXMLLanguageDriver;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import io.seata.rm.datasource.DataSourceProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.io.IOException;

/**
 * @program: ses-server
 * @description: seata 配置类
 * @author: Jerry
 * @created: 2020/09/19 02:57
 */

@Configuration
public class SeataAutoConfig {

    @Value("${spring.application.name}")
    private String appName;

    @Autowired
    private DataSourceProperties dataSourceProperties;

    /**
     * init durid datasource
     *
     * @Return: druidDataSource  datasource instance
     */
    @Bean
    @Primary
    public DruidDataSource druidDataSource() {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl(dataSourceProperties.getUrl());
        druidDataSource.setUsername(dataSourceProperties.getUsername());
        druidDataSource.setPassword(dataSourceProperties.getPassword());
        druidDataSource.setDriverClassName(dataSourceProperties.getDriverClassName());
        druidDataSource.setInitialSize(0);
        druidDataSource.setMaxActive(180);
        druidDataSource.setMaxWait(60000);
        druidDataSource.setMinIdle(0);
        druidDataSource.setValidationQuery("Select 1 from DUAL");
        druidDataSource.setTestOnBorrow(false);
        druidDataSource.setTestOnReturn(false);
        druidDataSource.setTestWhileIdle(true);
        druidDataSource.setTimeBetweenEvictionRunsMillis(60000);
        druidDataSource.setMinEvictableIdleTimeMillis(25200000);
        druidDataSource.setRemoveAbandoned(true);
        druidDataSource.setRemoveAbandonedTimeout(1800);
        druidDataSource.setLogAbandoned(true);
        return druidDataSource;
    }

    @Bean
    public DataSourceProxy dataSourceProxy(DataSource dataSource) {
        return new DataSourceProxy(dataSource);
    }

    /**
     * 因为我使用的是MybatisPlus，所以需要注入此部分
     *
     * @param proxy
     * @return
     * @throws IOException
     */
    @Bean
    public MybatisSqlSessionFactoryBean mybatisSqlSessionFactoryBean(DataSourceProxy proxy) throws IOException {
        MybatisSqlSessionFactoryBean mybatisPlus = new MybatisSqlSessionFactoryBean();
        mybatisPlus.setDataSource(proxy);
        mybatisPlus.setVfs(SpringBootVFS.class);
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        mybatisPlus.setMapperLocations(resolver.getResources("classpath*:mapper/**/*Mapper.xml,classpath*:mapper/*Mapper.xml"));
        GlobalConfig globalConfig = new GlobalConfig();
        GlobalConfig.DbConfig dbConfig = new GlobalConfig.DbConfig();
        // ID 策略 AUTO->`0`("数据库ID自增") INPUT->`1`(用户输入ID") ID_WORKER->`2`("全局唯一ID") UUID->`3`("全局唯一ID")
        dbConfig.setIdType(IdType.UUID);
        globalConfig.setDbConfig(dbConfig);
        mybatisPlus.setGlobalConfig(globalConfig);
        MybatisConfiguration mc = new MybatisConfiguration();
        // 对于完全自定义的mapper需要加此项配置，才能实现下划线转驼峰
        mc.setMapUnderscoreToCamelCase(true);
        mc.setDefaultScriptingLanguage(MybatisXMLLanguageDriver.class);
        mybatisPlus.setConfiguration(mc);
        return mybatisPlus;
    }

    //@Bean
    //@DependsOn({BEAN_NAME_SPRING_APPLICATION_CONTEXT_PROVIDER, BEAN_NAME_FAILURE_HANDLER})
    //public GlobalTransactionScanner globalTransactionScanner() {
    //    return new GlobalTransactionScanner(this.appName, String.format("%s-group", this.appName));
    //}

}