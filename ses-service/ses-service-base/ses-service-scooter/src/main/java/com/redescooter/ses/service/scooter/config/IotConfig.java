//package com.redescooter.ses.service.scooter.config;
//
//import com.redescooter.ses.admin.client.TerminalService;
//import com.redescooter.ses.admin.client.TerminalServiceFactory;
//import lombok.Data;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//
//@Data
//@Configuration
//@ConfigurationProperties(prefix = "iot")
//@Slf4j
//public class IotConfig {
//
//    private String address;
//
//
//    @Bean
//    public TerminalService initIotConnector() {
//        String[] split = address.split(";");
//        String[] hostAndPort = split[0].split(":");
//        TerminalServiceFactory.getInstance().initialization(hostAndPort[0], Integer.parseInt(hostAndPort[1]), null);
//        log.info("车载初始化完成，连接地址：" + address);
//        return TerminalServiceFactory.getInstance();
//    }
//}
