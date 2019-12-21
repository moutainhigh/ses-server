package com.redescooter.ses.tool.utils;

import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.exception.BaseException;
import com.redescooter.ses.api.common.exception.ValidationException;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.context.MessageSource;

import java.util.Locale;

/**
 * @description: ServiceAspectUtil
 * @author: Darren
 * @create: 2019/01/22 15:18
 */
public class ServiceAspectUtil {

    public static Object checkArgsAndExceptionI18n(ProceedingJoinPoint point, MessageSource messageSource, BaseException targetException) throws Throwable {
        Object[] objs = point.getArgs();
        try {
            if (objs != null) {
                for (Object obj : objs) {
                    ValidationUtil.validation(obj);
                }
            }
            return point.proceed();
        } catch (BaseException be) {
            throw throwException(messageSource, targetException, objs, be);
        }
    }

    private static Throwable throwException(MessageSource messageSource, BaseException targetException, Object[] objs, BaseException be) {
        if (!be.isI18nTranslated()) {
            String errorMsg = exceptionMsgI18nTranslate(messageSource, objs, be);
            if (be instanceof ValidationException) {
                targetException.setErrorCode(be.getErrorCode());
                targetException.setErrorMessage(errorMsg);
                targetException.setI18nTranslated(true);
                return targetException;
            } else {
                be.setErrorMessage(errorMsg);
                be.setI18nTranslated(true);
                return be;
            }
        } else {
            if (!be.getClass().isInstance(targetException) && be instanceof BaseException) {
                targetException.setErrorCode(be.getErrorCode());
                targetException.setErrorMessage(be.getErrorMessage());
                targetException.setStackTrace(be.getStackTrace());
                targetException.setI18nTranslated(true);
                return targetException;
            }
        }
        return be;
    }

    private static String exceptionMsgI18nTranslate(MessageSource messageSource, Object[] objs, BaseException be) {
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
}