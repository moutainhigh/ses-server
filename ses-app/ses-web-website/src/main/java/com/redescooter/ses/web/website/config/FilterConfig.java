package com.redescooter.ses.web.website.config;

import com.redescooter.ses.app.common.filter.LogAndExceptionFilter;
import com.redescooter.ses.web.website.filter.RequestFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author JERRY
 * @version V1.1
 * @Date: 03/01/2021 10:50
 * @ClassName: FilterConfig
 * @Function: 异常处理
 */
@Configuration
public class FilterConfig {

    /**
     * 统一日志和异常处理
     */
    @Bean
    public FilterRegistrationBean logFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new LogAndExceptionFilter());
        registration.addUrlPatterns("/*");
        registration.setName("logAndExceptionFilter");
        registration.setOrder(10);
        return registration;
    }

    @Bean
    public FilterRegistrationBean requestFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new RequestFilter());
        registration.addUrlPatterns("/*");
        registration.setName("requestFilter");
        registration.setOrder(20);
        return registration;
    }
}
