package com.redescooter.ses.service.scooter;

import com.redescooter.ses.service.scooter.config.emqx.MqttConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.apache.dubbo.config.spring.context.annotation.EnableDubboConfig;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@Slf4j
@EnableDubbo
@EnableScheduling
@EnableDubboConfig(multiple = true)
@SpringBootApplication(scanBasePackages = {"com.redescooter.ses"})
@EnableTransactionManagement
@EnableConfigurationProperties({MqttConfig.class})
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
