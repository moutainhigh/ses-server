package com.redescooter.ses.service.scooter;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.apache.dubbo.config.spring.context.annotation.EnableDubboConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@Slf4j
@EnableDubbo
@EnableDubboConfig(multiple = true)
@SpringBootApplication(scanBasePackages = {"com.redescooter.ses"})
@EnableTransactionManagement
public class SesServiceScooterApplication {

	private static volatile boolean running = true;

	@PostConstruct
	void started() {
		System.setProperty("user.timezone", "UTC");
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}

	public static void main(String[] args) {

		//非web启动
		new SpringApplicationBuilder(SesServiceScooterApplication.class)
				.web(WebApplicationType.NONE) // .REACTIVE, .SERVLET
				.run(args);
		log.info("SesServiceScooterApplication started success ... ");
		synchronized (SesServiceScooterApplication.class) {
			while (running) {
				try {
					SesServiceScooterApplication.class.wait();
				} catch (Throwable e) {
					log.error("SesServiceScooterApplication Throwable：", e);
				}
			}
		}
	}


}
