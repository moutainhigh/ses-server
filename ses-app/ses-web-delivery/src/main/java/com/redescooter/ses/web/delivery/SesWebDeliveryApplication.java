package com.redescooter.ses.web.delivery;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.apache.dubbo.config.spring.context.annotation.EnableDubboConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@Slf4j
@EnableDubbo
@EnableDubboConfig(multiple = true)
@SpringBootApplication(scanBasePackages = {"com.redescooter.ses"})
@EnableTransactionManagement
public class SesWebDeliveryApplication {

    private static volatile boolean running = true;

    @PostConstruct
    void started() {
        System.setProperty("user.timezone", "UTC");
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    public static void main(String[] args) {
        SpringApplication.run(SesWebDeliveryApplication.class, args);
        log.info("SesWebDeliveryApplication started success ... ");
        synchronized (SesWebDeliveryApplication.class) {
            while (running) {
                try {
                    SesWebDeliveryApplication.class.wait();
                } catch (Throwable e) {
                    log.error("SesWebDeliveryApplication Throwable：", e);
                }
            }
        }
    }


}
