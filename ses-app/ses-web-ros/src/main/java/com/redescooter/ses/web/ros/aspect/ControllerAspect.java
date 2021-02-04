package com.redescooter.ses.web.ros.aspect;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.redescooter.ses.api.common.annotation.IgnoreLoginCheck;
import com.redescooter.ses.api.common.annotation.WebsiteSignIn;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.enums.account.SysUserSourceEnum;
import com.redescooter.ses.api.common.exception.BaseException;
import com.redescooter.ses.api.common.exception.BusinessException;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.foundation.vo.user.UserToken;
import com.redescooter.ses.tool.aspect.ValidationUtil;
import com.redescooter.ses.web.ros.dm.OpeSysUser;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.OpeSysUserService;
import com.redescooter.ses.web.ros.service.base.TokenRosService;
import com.redescooter.ses.web.ros.service.website.WebSiteTokenService;
import com.redescooter.ses.web.ros.utils.SpringContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
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
    private TokenRosService tokenRosService;

    @Autowired
    private WebSiteTokenService webSiteService;

    @Autowired
    private OpeSysUserService opeSysUserService;

    @Around("execution(* com.redescooter.ses.web.ros.controller..*.*(..))")
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
            method = point.getTarget().getClass().getMethod(point.getSignature().getName(), argTypes);
        } catch (NoSuchMethodException e) {
            log.error("get method failure:", e);
        }
        //ros 内部系统登录
        if (method.getAnnotation(IgnoreLoginCheck.class) == null  && method.getAnnotation(WebsiteSignIn.class) == null) {
            UserToken userToken = tokenRosService.checkToken(enter);
            enter.setUserId(userToken.getUserId());
            enter.setTenantId(userToken.getTenantId());
            enter.setOpeDeptId(userToken.getDeptId());
            //TODO 接口权限验证
            checkPermission(point, enter);
        }
        //website 登陆
        if (method.getAnnotation(WebsiteSignIn.class) != null) {
            UserToken userToken = webSiteService.checkCustomerToken(enter);
            enter.setUserId(userToken.getUserId());
            enter.setTenantId(userToken.getTenantId());
        }

    }

    /**
     * 接口权限控制
     *
     * @param point
     */
    private void checkPermission(ProceedingJoinPoint point, GeneralEnter enter) {
        // todo 2020 9 14 判断如果是管理员账号，则不需要经过这个校验，直接return出去就行
        LambdaQueryWrapper<OpeSysUser> userWrapper = new LambdaQueryWrapper<>();
        userWrapper.eq(OpeSysUser::getId, enter.getUserId());
        userWrapper.eq(OpeSysUser::getDef1, SysUserSourceEnum.SYSTEM.getValue());
        userWrapper.last("limit 1");
        OpeSysUser admin = opeSysUserService.getOne(userWrapper);
        if (admin.getLoginName().equals(Constant.ADMIN_USER_NAME)) {
            return;
        }

        HttpServletRequest request = SpringContextUtils.getHttpServletRequest();
        String requestPath = request.getRequestURI().substring(request.getContextPath().length());
        requestPath = filterUrl(requestPath);
        log.info("拦截请求 >> " + requestPath + ";请求类型 >> " + request.getMethod());
        // 得到该用户拥有的权限path
        List<String> permsList = opeSysUserService.findPerms(enter.getUserId());
        // todo 接口的权限控制，等数据库的数据完善了  再将下面注释的两行（154、164）放开
        if (CollectionUtils.isEmpty(permsList)) {
            throw new SesWebRosException(ExceptionCodeEnums.NO_PERM.getCode(), ExceptionCodeEnums.NO_PERM.getMessage());
        }
        boolean flag = false;
        for (String perm : permsList) {
            if (perm.contains(requestPath)) {
                flag = true;
                break;
            }
        }
        if (!flag) {
            throw new SesWebRosException(ExceptionCodeEnums.NO_PERM.getCode(), ExceptionCodeEnums.NO_PERM.getMessage());
        }
    }

    private void checkEnterParameter(GeneralEnter enter) {
        if (StringUtils.isEmpty(enter.getRequestId())) {
            enter.setRequestId(UUID.randomUUID().toString().replaceAll("-", ""));
        }
        if (StringUtils.isEmpty(enter.getLanguage())) {
            enter.setLanguage(Locale.ENGLISH.getLanguage());
        }
        enter.setTimestamp((new Date()).getTime());
        if (StringUtils.isBlank(enter.getCountry())) {
            throw new SesWebRosException(ExceptionCodeEnums.COUNTRY_CANNOT_EMPTY.getCode(), ExceptionCodeEnums.COUNTRY_CANNOT_EMPTY.getMessage());
        }
//        if (SesStringUtils.isBlank(enter.getLanguage())) {
//            throw new CrmWebException(ExceptionCodeEnums.LANGUAGE_CANNOT_EMPTY.getCode(), ExceptionCodeEnums.LANGUAGE_CANNOT_EMPTY.getMessage());
//        }
        if (StringUtils.isBlank(enter.getClientType())) {
            throw new SesWebRosException(ExceptionCodeEnums.CLIENTTYPE_CANNOT_EMPTY.getCode(), ExceptionCodeEnums.CLIENTTYPE_CANNOT_EMPTY.getMessage());
        }
        if (StringUtils.isBlank(enter.getClientIp())) {
            throw new SesWebRosException(ExceptionCodeEnums.CLIENTIP_CANNOT_EMPTY.getCode(), ExceptionCodeEnums.CLIENTIP_CANNOT_EMPTY.getMessage());
        }
        if (StringUtils.isBlank(enter.getTimeZone())) {
            throw new SesWebRosException(ExceptionCodeEnums.TIMEZONE_CANNOT_EMPTY.getCode(), ExceptionCodeEnums.TIMEZONE_CANNOT_EMPTY.getMessage());
        }
        if (StringUtils.isBlank(enter.getVersion())) {
            throw new SesWebRosException(ExceptionCodeEnums.VERSION_CANNOT_EMPTY.getCode(), ExceptionCodeEnums.VERSION_CANNOT_EMPTY.getMessage());
        }
    }

    private String filterUrl(String requestPath) {
        String url = "";
        if (StringUtils.isNotBlank(requestPath)) {
            url = requestPath.replace("\\", "/");
            url = requestPath.replace("//", "/");
            if (url.indexOf("//") >= 0) {
                url = filterUrl(url);
            }
			/*if(url.startsWith("/")){
				url=url.substring(1);
			}*/
        }
        return url;
    }
}
