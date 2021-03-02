package com.redescooter.ses.web.ros;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.apache.dubbo.config.spring.context.annotation.EnableDubboConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

/**
 * @program: ses-server
 * @description: ROS 启动类
 * @author: Jerry
 * @created: 2020/09/20 12:57
 */

@Slf4j
@EnableDubbo
@EnableDubboConfig(multiple = true)
@SpringBootApplication(scanBasePackages = {"com.redescooter.ses"})
@EnableTransactionManagement
public class SesWebRosApplication {

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
        SpringApplication.run(SesWebRosApplication.class, args);
        log.info("SesWebRosApplication started success ... ");
        synchronized (SesWebRosApplication.class) {
            while (running) {
                try {
                    SesWebRosApplication.class.wait();
                } catch (Throwable e) {
                    log.error("SesWebRosApplication Throwable：", e);
                }
            }
        }
    }


}
