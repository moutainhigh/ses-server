package com.redescooter.ses.mobile.wh.fr;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.apache.dubbo.config.spring.context.annotation.EnableDubboConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

/**
 * @program: ses-server
 * @description: 法国仓库app 启动类
 * @author: Jerry
 * @created: 2020/09/20 12:57
 */
@Slf4j
@EnableDubbo
@EnableDubboConfig(multiple = true)
@SpringBootApplication(scanBasePackages = {"com.redescooter.ses"})
@EnableTransactionManagement
public class SesMobileWhFrApplication {

    private static volatile boolean running = true;

    /**
     * 设置默认时区
     */
    @PostConstruct
    void started() {
        System.setProperty("user.timezone", "UTC");
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    public static void main(String[] args) {
        SpringApplication.run(SesMobileWhFrApplication.class, args);
        log.info("SesMobileWhFrApplication started success ... ");
        synchronized (SesMobileWhFrApplication.class) {
            while (running) {
                try {
                    SesMobileWhFrApplication.class.wait();
                } catch (Throwable e) {
                    log.error("SesMobileWhFrApplication Throwable：", e);
                }
            }
        }
    }

}
