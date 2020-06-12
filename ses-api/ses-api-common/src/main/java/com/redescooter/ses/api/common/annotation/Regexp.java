package com.redescooter.ses.api.common.annotation;

import com.redescooter.ses.api.common.constant.RegexpConstant;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by zhanggt on 2016-5-31.
 */
@Target({java.lang.annotation.ElementType.TYPE, java.lang.annotation.ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Regexp {
    // 默认不能输入非法字符
    String value() default RegexpConstant.specialCharacters;

    int code() default 1;

    String message() default "parameter is invalid";
}
