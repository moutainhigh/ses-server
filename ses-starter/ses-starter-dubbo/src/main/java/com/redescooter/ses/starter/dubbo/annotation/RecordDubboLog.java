package com.redescooter.ses.starter.dubbo.annotation;



import java.lang.annotation.*;

/**
 *  Dubbo日志输出注解, 默认不输出接口日志,只有添加注解才输出接口日志
 * @author Darren
 * @date 2019/1/16 21:39
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RecordDubboLog {

    /**
     * 是否打印人参，默认打印
     */
    boolean printInArgs() default true;
    /**
     * 是否打印出参，默认不打印
     */
    boolean printOutArgs() default true;
}