package com.redescooter.ses.api.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *该注解 是在方法上 添加锁的
 */
@Target({java.lang.annotation.ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MethodLock {

    String desc() default "";
}
