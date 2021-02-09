package com.redescooter.ses.web.ros.config;

import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @Author jerry
 * @Date 2021/2/9 1:37 下午
 * @Description 解决Mybatis-plus的BaseMapper的selectOne查询多个会异常的问题优化
 **/
@Aspect
@Component
public class MybatisAspect {

    // 配置织入点
    @Pointcut("execution(public * com.baomidou.mybatisplus.core.mapper.BaseMapper.selectOne(..))")
    public void selectOneAspect() {
    }

    @Before("selectOneAspect()")
    public void beforeSelect(JoinPoint point) {
        Object arg = point.getArgs()[0];
        if (arg instanceof AbstractWrapper) {
            arg = (AbstractWrapper) arg;
            ((AbstractWrapper) arg).last("limit 1");
        }
    }
}
