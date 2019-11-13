//package com.redescooter.ses.instance.one.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.transaction.jta.JtaTransactionManager;
//
///**
// * @author Mr.lijiating
// * @version V1.0
// * @Date: 24/10/2019 12:18 上午
// * @ClassName: JtaTransactionConfiguration
// * @Function: TODO
// */
//@Configuration
//public class JtaTransactionConfiguration {
//    @Autowired
//    private AtomikosJtaConfiguration jtaConfiguration;
//
//    @Bean
//    public PlatformTransactionManager platformTransactionManager()  throws Throwable {
//        return new JtaTransactionManager(jtaConfiguration.userTransaction(), jtaConfiguration.transactionManager());
//    }
//}