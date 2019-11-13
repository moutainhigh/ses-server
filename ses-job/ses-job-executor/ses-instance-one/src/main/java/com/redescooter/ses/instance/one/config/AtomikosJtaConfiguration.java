//package com.redescooter.ses.instance.one.config;
//
//import com.atomikos.icatch.jta.UserTransactionImp;
//import com.atomikos.icatch.jta.UserTransactionManager;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//
//import javax.transaction.TransactionManager;
//import javax.transaction.UserTransaction;
//
///**
// * @author Mr.lijiating
// * @version V1.0
// * @Date: 24/10/2019 12:19 上午
// * @ClassName: AtomikosJtaConfiguration
// * @Function: TODO
// */
//@Configuration
//@ComponentScan("com.redescooter.ses.instance.one.config")
//public class AtomikosJtaConfiguration {
//    @Bean
//    public UserTransaction userTransaction() throws Throwable {
//        UserTransactionImp userTransactionImp = new UserTransactionImp();
//        userTransactionImp.setTransactionTimeout(1000);
//        return userTransactionImp;
//    }
//
//    @Bean(initMethod = "init", destroyMethod = "close")
//    public TransactionManager transactionManager() throws Throwable {
//        UserTransactionManager userTransactionManager = new UserTransactionManager();
//        userTransactionManager.setForceShutdown(false);
//        return userTransactionManager;
//    }
//}