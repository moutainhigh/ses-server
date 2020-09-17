package com.redescooter.ses.web.ros.service.admin;

import com.redescooter.ses.api.common.annotation.LogAnnotation;
import com.redescooter.ses.web.ros.dm.OpeSysUser;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.common.utils.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @ClassNameAdvice
 * @Description
 * @Author Aleks
 * @Date2020/9/15 17:27
 * @Version V1.0
 **/
@Aspect
@Component
public class Advice {

    @Around(value = "@annotation(com.redescooter.ses.api.common.annotation.LogAnnotation)")
    public Object testLog(ProceedingJoinPoint joinPoint) throws Throwable{
        OpeSysUser user = new OpeSysUser();
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Integer code = null;
        LogAnnotation logAnnotation = methodSignature.getMethod().getDeclaredAnnotation(LogAnnotation.class);
        String module = logAnnotation.module();
        if (StringUtils.isEmpty(module)) {
            ApiOperation apiOperation = methodSignature.getMethod().getDeclaredAnnotation(ApiOperation.class);
            if (apiOperation != null) {
                module = apiOperation.value();
                code = apiOperation.code();
            }
        }
        if (StringUtils.isEmpty(module)) {
            throw new RuntimeException("没有指定日志module");
        }
        try {
            if (code != null && !code.equals(200)){
                String id = null;
                Object object = joinPoint.getArgs()[0];

                if (code % 2 == 0) {
                    id = (String) (object);

                } else if (code % 2 == 1) {

                }

            }
            Object object = joinPoint.proceed();

            return object;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        } finally {
            System.out.println("555555");
        }

    }


}
