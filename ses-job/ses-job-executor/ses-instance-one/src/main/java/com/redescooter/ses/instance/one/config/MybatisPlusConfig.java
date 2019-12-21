//package com.redescooter.ses.instance.one.config;
//
//import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
//import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
//import org.mybatis.spring.annotation.MapperScan;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//
//import java.util.Properties;
//
///**
// * @author Mr.lijiating
// * @version V1.0
// * @Date: 29/10/2019 5:27 下午
// * @ClassName: MybatisPlusConfig
// * @Function: TODO
// */
//@EnableTransactionManagement
//@Configuration
//@MapperScan("com.redescooter.ses.instance.one.db.**.dao")
//public class MybatisPlusConfig {
//    /**
//     * 分页插件
//     *
//     * @return PaginationInterceptor
//     */
//    @Bean
//    public PaginationInterceptor paginationInterceptor() {
//        return new PaginationInterceptor();
//    }
//
//    /**
//     * mybatis-plus SQL执行效率插件【生产环境可以关闭】
//     */
//    @Bean
//    public PerformanceInterceptor performanceInterceptor() {
//        PerformanceInterceptor performanceInterceptor = new PerformanceInterceptor();
//        //格式化sql语句
//        Properties properties = new Properties();
//        properties.setProperty("format", "true");
//        performanceInterceptor.setProperties(properties);
//        return performanceInterceptor;
//    }
//}
