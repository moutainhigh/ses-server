package com.redescooter.ses.api.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassNameLogAnnotation
 * @Description
 * @Author aleks
 * @Date2020/9/15 17:32
 * @Version V1.0
 **/
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface LogAnnotation {

    String module() default "";

}
