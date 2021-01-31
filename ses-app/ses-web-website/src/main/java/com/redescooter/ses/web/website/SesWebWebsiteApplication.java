package com.redescooter.ses.web.website;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.apache.dubbo.config.spring.context.annotation.EnableDubboConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

/**
 * @author jerry
 */
@Slf4j
@EnableDubbo
@EnableCaching
@EnableDubboConfig(multiple = true)
@EnableTransactionManagement
@SpringBootApplication(scanBasePackages = {"com.redescooter.ses"})
public class SesWebWebsiteApplication {

    private static volatile boolean running = true;

    @PostConstruct
    void started() {
        System.setProperty("user.timezone", "UTC");
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    public static void main(String[] args) {
        SpringApplication.run(SesWebWebsiteApplication.class, args);
        log.info("SesWebWebsiteApplication started success ... ");
        synchronized (SesWebWebsiteApplication.class) {
            while (running) {
                try {
                    SesWebWebsiteApplication.class.wait();
                } catch (Throwable e) {
                    log.error("SesWebWebsiteApplication Throwable：", e);
                }
            }
        }
    }


}
