package com.redescooter.ses.api.common.annotation;

import com.redescooter.ses.api.common.exception.ValidationExceptionCode;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({java.lang.annotation.ElementType.TYPE, java.lang.annotation.ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface LatCheck {

    String value() default "";

    int code() default ValidationExceptionCode.LAT_IS_ILLEGAL;

    String message() default "";

}
