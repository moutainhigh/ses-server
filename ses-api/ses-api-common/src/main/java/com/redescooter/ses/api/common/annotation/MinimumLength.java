package com.redescooter.ses.api.common.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 支持String类型
 *
 * @description: MinimumLength
 * @author: Darren
 * @create: 2019/05/31 17:51
 */
@Target({java.lang.annotation.ElementType.TYPE, java.lang.annotation.ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MinimumLength {

    String value() default "0";

    int code() default 0;

    String message() default "parameter is invalid";
}
