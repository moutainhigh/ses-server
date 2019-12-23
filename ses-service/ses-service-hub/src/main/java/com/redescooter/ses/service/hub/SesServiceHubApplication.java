package com.redescooter.ses.service.hub;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.apache.dubbo.config.spring.context.annotation.EnableDubboConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@Slf4j
@EnableDubbo
@EnableDubboConfig(multiple = true)
@EnableTransactionManagement
@EnableAspectJAutoProxy(exposeProxy = true)
@SpringBootApplication(scanBasePackages = {"com.redescooter.ses"},exclude = DruidDataSourceAutoConfigure.class)
public class SesServiceHubApplication {

    private static volatile boolean running = true;

    @PostConstruct
    void started() {
        System.setProperty("user.timezone", "UTC");
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    public static void main(String[] args) {
        //非web启动
        new SpringApplicationBuilder(SesServiceHubApplication.class)
                .web(WebApplicationType.NONE) // .REACTIVE, .SERVLET
                .run(args);
        log.info("SesServiceHubApplication started success ... ");
        synchronized (SesServiceHubApplication.class) {
            while (running) {
                try {
                    SesServiceHubApplication.class.wait();
                } catch (Throwable e) {
                    log.error("SesServiceHubApplication Throwable：", e);
                }
            }
        }
    }


}
