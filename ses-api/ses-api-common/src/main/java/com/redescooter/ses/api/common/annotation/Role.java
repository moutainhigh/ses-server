package com.redescooter.ses.api.common.annotation;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 
 * Created by zhanggt on 2016-4-20.
 * 
 */

@Target({ java.lang.annotation.ElementType.TYPE, java.lang.annotation.ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Role {
	/**
	 * 角色定义表达式
	 * 
	 * @return 角色定义表达式
	 */
	String value() default "";
	
	String[] code() default {};

	String message() default "access denied you do not have permission to access this interface";
}
