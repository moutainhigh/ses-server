package com.redescooter.ses.web.ros.config;

import com.redescooter.ses.app.common.filter.LogAndExceptionFilter;
import com.redescooter.ses.web.ros.filter.RequestFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 15/12/2019 11:51 下午
 * @ClassName: FilterConfig
 * @Function: TODO
 */
@Configuration
public class FilterConfig {

    /**
     * 统一日志和异常处理
     */
    @Bean
    public FilterRegistrationBean logFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter((Filter) new LogAndExceptionFilter());
        registration.addUrlPatterns("/*");
        registration.setName("logAndExceptionFilter");
        registration.setOrder(10);
        return registration;
    }

    @Bean
    public FilterRegistrationBean requestFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter((Filter) new RequestFilter());
        registration.addUrlPatterns("/*");
        registration.setName("requestFilter");
        registration.setOrder(20);
        return registration;
    }
}
