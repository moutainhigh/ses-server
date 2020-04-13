package com.redescooter.ses.mobile.rps;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.apache.dubbo.config.spring.context.annotation.EnableDubboConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Slf4j
@EnableDubbo
@EnableDubboConfig(multiple = true)
@SpringBootApplication(scanBasePackages = {"com.redescooter.ses"})
@EnableTransactionManagement
public class SesMobileRpsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SesMobileRpsApplication.class, args);
    }

}
