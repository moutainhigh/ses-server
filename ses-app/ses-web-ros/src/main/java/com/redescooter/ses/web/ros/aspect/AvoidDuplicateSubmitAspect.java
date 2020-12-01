package com.redescooter.ses.web.ros.aspect;

import com.redescooter.ses.api.common.annotation.AvoidDuplicateSubmit;
import com.redescooter.ses.tool.utils.IpUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.utils.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @ClassNameAvoidDuplicateSubmitAspect
 * @Description
 * @Author Aleks
 * @Date2020/11/20 13:35
 * @Version V1.0
 **/
@Component
@Aspect
@Slf4j
public class AvoidDuplicateSubmitAspect {


    @Autowired
    private RedisTemplate redisTemplate;

    @Around(value = "@annotation(com.redescooter.ses.api.common.annotation.AvoidDuplicateSubmit)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取request对象
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String ip = IpUtils.getIpAddr(request);
        // 获取方法签名
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        String className = method.getDeclaringClass().getName();
        String methodName = method.getName();
        String ipKey = String.format("%s#%s", className, methodName);
        int hashCode = Math.abs(ipKey.hashCode());
        // 拼接redisKey,如：127.0.0.1_1898984393
        String redisKey = String.format("%s_%d", ip, hashCode);

        String value = (String) redisTemplate.opsForValue().get(redisKey);
        if (!StringUtils.isEmpty(value)) {
            return null;
        }

        // 获取注解
        AvoidDuplicateSubmit avoidDuplicateSubmit = method.getAnnotation(AvoidDuplicateSubmit.class);
        long timeout = avoidDuplicateSubmit.timeout();
        if (timeout < 0) {
            timeout = 500;
        }
        // 第一次提交，插入redis
        redisTemplate.opsForValue().set(redisKey, UUID.randomUUID().toString(), timeout, TimeUnit.MILLISECONDS);
        // 继续执行方法
        return joinPoint.proceed();
    }

}
