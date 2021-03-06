package com.redescooter.ses.mobile.rps.aspect;


import com.redescooter.ses.api.common.annotation.IgnoreLoginCheck;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.exception.BaseException;
import com.redescooter.ses.api.common.exception.BusinessException;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.foundation.vo.user.UserToken;
import com.redescooter.ses.mobile.rps.exception.ExceptionCodeEnums;
import com.redescooter.ses.mobile.rps.exception.SesMobileRpsException;
import com.redescooter.ses.mobile.rps.service.TokenRpsService;
import com.redescooter.ses.tool.aspect.ValidationUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;


/**
 * Created by zhanggt on 2016-5-31.
 */
@Component
@Aspect
@Order(100)
@Slf4j
public class ControllerAspect {

    @Resource
    private MessageSource messageSource;

    @Autowired
    private TokenRpsService tokenRpsService;

    @Around("execution(* com.redescooter.ses.mobile.rps.controller..*.*(..))")
    public Object check(ProceedingJoinPoint point) throws Throwable {
        Object[] objs = point.getArgs();
        try {
            for (Object obj : objs) {
                if (obj instanceof GeneralEnter) {
                    //TODO 多个参数处理优化
                    GeneralEnter enter = (GeneralEnter) obj;
                    checkEnterParameter(enter);
                    checkToken(point, enter);
                }
                ValidationUtil.validation(obj);
            }
            return point.proceed();
        } catch (Exception e) {
            BaseException be = getBaseException(e);
            if (be != null) {
                if (be.isI18nTranslated()) {
                    throw be;
                }
                String errorMsg = getErrorMsg(objs, be);
                throw new BusinessException(be.getErrorCode(), errorMsg, be);
            }
            throw e;
        }
    }

    private String getErrorMsg(Object[] objs, BaseException be) {
        Locale locale = new Locale(Constant.DEFAULT_LANGUAGE, Constant.DEFAULT_COUNTRY);
        for (Object obj : objs) {
            if (obj instanceof GeneralEnter) {
                GeneralEnter enter = (GeneralEnter) obj;
                String language = StringUtils.isEmpty(enter.getLanguage()) ? Constant.DEFAULT_LANGUAGE : enter.getLanguage();
                String country = StringUtils.isEmpty(enter.getCountry()) ? Constant.DEFAULT_COUNTRY : enter.getCountry();
                locale = new Locale(language, country);
                break;
            }
        }
        return messageSource.getMessage(be.getErrorCode(), null, locale);
    }

    private BaseException getBaseException(Exception e) {
        if (e instanceof BaseException) {
            return (BaseException) e;
        }
        for (Throwable cause = e.getCause(); cause != null; cause = cause.getCause()) {
            if (cause instanceof BaseException) {
                return (BaseException) cause;
            }
        }
        return null;
    }

    private void checkToken(ProceedingJoinPoint point, GeneralEnter enter) {
        Method method = null;
        Class<?>[] argTypes = new Class[point.getArgs().length];
        for (int i = 0; i < point.getArgs().length; i++) {
            argTypes[i] = point.getArgs()[i].getClass();
        }
        try {
            method = point.getTarget().getClass()
                    .getMethod(point.getSignature().getName(), argTypes);
        } catch (NoSuchMethodException e) {
            log.error("get method failure:", e);
        }
        if (method.getAnnotation(IgnoreLoginCheck.class) == null) {
            UserToken userToken = tokenRpsService.checkToken(enter);
            enter.setUserId(userToken.getUserId());
            enter.setTenantId(userToken.getTenantId());
        }
    }

    private void checkEnterParameter(GeneralEnter enter) {
        if (StringUtils.isEmpty(enter.getRequestId())) {
            enter.setRequestId(UUID.randomUUID().toString().replaceAll("-", ""));
        }
        //默认语言为英语
        if (StringUtils.isEmpty(enter.getLanguage())) {
            enter.setLanguage(Locale.ENGLISH.getLanguage());
        }
        enter.setTimestamp((new Date()).getTime());
        if (StringUtils.isBlank(enter.getCountry())) {
            throw new SesMobileRpsException(ExceptionCodeEnums.COUNTRY_CANNOT_EMPTY.getCode(), ExceptionCodeEnums.COUNTRY_CANNOT_EMPTY.getMessage());
        }

        if (StringUtils.isBlank(enter.getClientType())) {
            throw new SesMobileRpsException(ExceptionCodeEnums.CLIENTTYPE_CANNOT_EMPTY.getCode(), ExceptionCodeEnums.CLIENTTYPE_CANNOT_EMPTY.getMessage());
        }
        if (StringUtils.isBlank(enter.getClientIp())) {
            throw new SesMobileRpsException(ExceptionCodeEnums.CLIENTIP_CANNOT_EMPTY.getCode(), ExceptionCodeEnums.CLIENTIP_CANNOT_EMPTY.getMessage());
        }
        if (StringUtils.isBlank(enter.getTimeZone())) {
            throw new SesMobileRpsException(ExceptionCodeEnums.TIMEZONE_CANNOT_EMPTY.getCode(), ExceptionCodeEnums.TIMEZONE_CANNOT_EMPTY.getMessage());
        }
        if (StringUtils.isBlank(enter.getVersion())) {
            throw new SesMobileRpsException(ExceptionCodeEnums.VERSION_CANNOT_EMPTY.getCode(), ExceptionCodeEnums.VERSION_CANNOT_EMPTY.getMessage());
        }
    }
}


