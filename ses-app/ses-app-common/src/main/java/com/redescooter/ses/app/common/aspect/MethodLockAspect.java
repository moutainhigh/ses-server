package com.redescooter.ses.app.common.aspect;

import com.redescooter.ses.tool.aspect.MethodLockAspectUtils;
import lombok.extern.log4j.Log4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


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
        System.out.println("-------------------切面切入-------------------");
        return MethodLockAspectUtils.methodLock(joinPoint);
    }

}
