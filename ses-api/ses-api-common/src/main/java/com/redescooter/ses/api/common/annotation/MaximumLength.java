package com.redescooter.ses.api.common.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 支持String类型
 *
 * @description: MaximumLength
 * @author: Darren
 * @create: 2019/05/31 17:51
 */
@Target({java.lang.annotation.ElementType.TYPE, java.lang.annotation.ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MaximumLength {

    String value() default ""+Integer.MAX_VALUE;

    int code() default 0;

    String message() default "parameter is invalid";
}
