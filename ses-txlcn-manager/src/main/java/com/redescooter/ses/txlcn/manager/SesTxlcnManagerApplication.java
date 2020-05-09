package com.redescooter.ses.txlcn.manager;

import com.codingapi.txlcn.tm.config.EnableTransactionManagerServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableTransactionManagerServer
@SpringBootApplication
public class SesTxlcnManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SesTxlcnManagerApplication.class, args);
    }

}
