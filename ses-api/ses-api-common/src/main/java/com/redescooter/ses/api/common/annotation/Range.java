package com.redescooter.ses.api.common.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 支持Number类型
 * >=min < max
 * Created by zhanggt on 2016-5-11.
 */
@Target({java.lang.annotation.ElementType.TYPE, java.lang.annotation.ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Range {

    String min() default "";

    String max() default "";

    int code() default 1;

    String message() default "parameter values range is illegal";
}
