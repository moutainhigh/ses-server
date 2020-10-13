package com.xxl.job.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.TimeZone;
/**
 * @author xuxueli 2018-10-28 00:38:13
 */
@Slf4j
@SpringBootApplication
public class SesJobAdminApplication {


	private static volatile boolean running = true;

	@PostConstruct
	void started() {
		System.setProperty("user.timezone", "UTC");
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}

	public static void main(String[] args) {
		SpringApplication.run(SesJobAdminApplication.class, args);
		log.info("SesJobAdminApplication started success ... ");
		synchronized (SesJobAdminApplication.class) {
			while (running) {
				try {
					SesJobAdminApplication.class.wait();
				} catch (Throwable e) {
					log.error("Bootstrap Throwable：", e);
				}
			}
		}
	}
}