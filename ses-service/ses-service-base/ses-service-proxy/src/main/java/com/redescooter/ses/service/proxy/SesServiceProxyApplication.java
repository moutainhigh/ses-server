package com.redescooter.ses.service.proxy;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.apache.dubbo.config.spring.context.annotation.EnableDubboConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.scheduling.annotation.EnableAsync;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@Slf4j
@EnableAsync //开启异步请求
@EnableDubbo
@EnableDubboConfig(multiple = true)
@SpringBootApplication(scanBasePackages = {"com.redescooter.ses"})
public class SesServiceProxyApplication {

    private static volatile boolean running = true;

    @PostConstruct
    void started() {
        System.setProperty("user.timezone", "UTC");
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    public static void main(String[] args) {

        //非web启动
        new SpringApplicationBuilder(SesServiceProxyApplication.class)
                .web(WebApplicationType.NONE) // .REACTIVE, .SERVLET
                .run(args);
        log.info("SesServiceProxyApplication started success ... ");
        synchronized (SesServiceProxyApplication.class) {
            while (running) {
                try {
                    SesServiceProxyApplication.class.wait();
                } catch (Throwable e) {
                    log.error("SesServiceProxyApplication Throwable：", e);
                }
            }
        }
    }


}
