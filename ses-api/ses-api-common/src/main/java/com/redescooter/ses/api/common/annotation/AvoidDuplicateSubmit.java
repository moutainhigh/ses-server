package com.redescooter.ses.api.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author Aleks
 * @Description  短时间之内不能重复提交
 * @Date  2020/11/20 13:33
 * @Param
 * @return
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AvoidDuplicateSubmit {

    // 时间为毫秒  暂时设置为一秒钟
    long timeout() default 1000;

}
