package com.redescooter.ses.web.ros;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import com.ulisesbocchio.jasyptspringboot.environment.StandardEncryptableEnvironment;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.apache.dubbo.config.spring.context.annotation.EnableDubboConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@Slf4j
@EnableDubbo
@EnableDubboConfig(multiple = true)
@EnableAutoConfiguration
@SpringBootApplication(scanBasePackages = {"com.redescooter.ses"})
@EnableTransactionManagement
//@PropertySource(value = "classpath:application-local.yml")
public class SesWebRosApplication {

    private static volatile boolean running = true;

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
