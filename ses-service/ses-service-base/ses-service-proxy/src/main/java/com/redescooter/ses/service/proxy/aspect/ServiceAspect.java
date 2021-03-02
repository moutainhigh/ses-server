package com.redescooter.ses.service.proxy.aspect;

import com.redescooter.ses.api.foundation.exception.FoundationException;
import com.redescooter.ses.tool.aspect.ServiceAspectUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.MessageSource;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


/**
 * Created by zhanggt on 2016-5-31.
 */
@Component
@Aspect
@Order(100)
public class ServiceAspect {

    @Resource
    private MessageSource messageSource;

    @Around("execution(* com.redescooter.ses.service..*.*(..))")
    public Object check(ProceedingJoinPoint point) throws Throwable {
        return ServiceAspectUtil.checkArgsAndExceptionI18n(point, messageSource, new FoundationException());
    }

}
