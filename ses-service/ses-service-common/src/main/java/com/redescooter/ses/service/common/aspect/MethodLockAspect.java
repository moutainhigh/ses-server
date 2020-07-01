package com.redescooter.ses.service.common.aspect;

import com.redescooter.ses.api.foundation.exception.FoundationException;
import com.redescooter.ses.tool.utils.ServiceAspectUtil;
import com.redescooter.ses.tool.utils.aspect.MethodLockAspectUtils;
import lombok.extern.log4j.Log4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.MessageSource;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 * 锁注解 切面
 */
@Component
@Aspect
@Order(100)
@Log4j
public class MethodLockAspect {

    @Around("@annotation(com.redescooter.ses.api.common.annotation.MethodLock)")
    public Object around(ProceedingJoinPoint joinPoint) {
        return MethodLockAspectUtils.methodLock(joinPoint);
    }
}
