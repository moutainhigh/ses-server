package com.redescooter.ses.service.foundation;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.apache.dubbo.config.spring.context.annotation.EnableDubboConfig;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

/**
 * @author lijiating
 */
@Slf4j
@EnableDubbo
@EnableDubboConfig(multiple = true)
@SpringBootApplication(scanBasePackages = {"com.redescooter.ses"})
@EnableTransactionManagement
public class SesServiceFoundationApplication {

    private static volatile boolean running = true;

    @PostConstruct
    void started() {
        System.setProperty("user.timezone", "UTC");
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    public static void main(String[] args) {

        //非web启动
        new SpringApplicationBuilder(SesServiceFoundationApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);
        log.info("SesServiceFoundationApplication started success ... ");
        synchronized (SesServiceFoundationApplication.class) {
            while (running) {
                try {
                    SesServiceFoundationApplication.class.wait();
                } catch (Throwable e) {
                    log.error("SesServiceFoundationApplication Throwable：", e);
                }
            }
        }
    }


}
