package com.redescooter.ses.web.ros.aspect;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Sets;
import com.redescooter.ses.api.common.annotation.IgnoreLoginCheck;
import com.redescooter.ses.api.common.annotation.WebsiteSignIn;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.constant.JedisConstant;
import com.redescooter.ses.api.common.enums.account.SysUserSourceEnum;
import com.redescooter.ses.api.common.exception.BaseException;
import com.redescooter.ses.api.common.exception.BusinessException;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.foundation.vo.user.UserToken;
import com.redescooter.ses.tool.aspect.ValidationUtil;
import com.redescooter.ses.tool.utils.SesStringUtils;
import com.redescooter.ses.web.ros.dm.OpeSysUser;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.OpeSysUserService;
import com.redescooter.ses.web.ros.service.base.TokenRosService;
import com.redescooter.ses.web.ros.service.website.WebSiteTokenService;
import com.redescooter.ses.web.ros.utils.SpringContextUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


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

    @Autowired
    private RedisTemplate redisTemplate;

    @Around("execution(* com.redescooter.ses.web.ros.controller..*.*(..))")
    public Object check(ProceedingJoinPoint point) throws Throwable {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
        Object[] objs = point.getArgs();
        try {
            for (Object obj : objs) {
                if (obj instanceof GeneralEnter) {
                    //TODO 多个参数处理优化
                    GeneralEnter enter = (GeneralEnter) obj;
                    checkEnterParameter(enter,request);
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

    private static final String[] whiteList = {
            "/sign/token/login",
            "/sign/token/logout",
            "/sys/menu/tree",
            "/Info/getUserInfo",

            // 验证码登录
            "/sign/token/emailLoginSendCode",
            "/sign/token/emailLogin",

            // 忘记密码
            "/sign/token/sendForgetPasswordEmail",
            "/sign/token/chanage",

            // 组织架构--部门
            "/sys/dept/saveDeptSelectParent",
            "/sys/dept/principal",

            // 组织架构--角色
            "/sys/position/selectPositionType",

            // 组织架构--员工
            "/sys/role/roleData",
            "/country/countryCodelist",

            // 客户账户
            "/account/customer/accountCountStatus",

            // 联系我们
            "/website/countryCityPostCode",
            "/contactUs/reply",

            // RFQ
            "/inquiry/countStatus",
            "/inquiry/depositPaymentEmail",

            // 客户
            "/customer/countStatus",

            // 调拨单
            "/sys/allocate/order/whData",
            "/sale/scooter/colorData",
            "/specificat/group/specificatGroupData",

            // 生产采购单
            "/restproduction/purchas/supplierList",
            "/production/purchasing/queryPurchasProductList",

            // 售卖产品
            "/bom/sales/product/getType",
            "/bom/sales/after/getType",
            "/sale/scooter/specificatTypeData",

            // 中国仓库
            "/production/scooter/colorList",
            "/wms/stock/ableProductionScooter",
            "/production/scooter/groupList",
            "/wms/stock/wmsStockCount",
            "/restproduction/assembly/scooterGroupData",
            "/restproduction/assembly/colorData",

            // 经销商
            "/distributor/city/cp",
            "/distributor/sale/product",

            // 部件
            "/bom/parts/secList",
            "/bom/parts/typeCount",
            "/supplier/list",

            // 组装件
            "/production/combination/checkProductN",
            "/production/combination/secList",
            "/production/combination/productionProductPartList",

            // 车辆
            "/production/scooter/productionProductPartList",
            "/specificat/type/specificatNameCheck",

            // 价格
            "/bom/supplierchaim/currencyUnit",
            "/bom/supplierchaim/productPriceHistroyList",
            "/bom/supplierchaim/productPriceHistroyChart",

            // 采购单
            "/sys/purchase/order/allocateNoData",
            "/sys/allocate/order/userData",

            // 入库单
            "/sys/inWhouse/order/relationCombinOrderData",

            // 菜单
            "/sys/menu/menuDatas",

            // 数据字典
            "/setting/parameter/groupList"
    };

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
        if (null != admin) {
            if (Constant.ADMIN_USER_NAME.equals(admin.getLoginName())) {
                return;
            }
        }

        // 获取请求路径
        HttpServletRequest request = SpringContextUtils.getHttpServletRequest();
        String requestPath = request.getRequestURI().substring(request.getContextPath().length());
        requestPath = filterUrl(requestPath);
        log.info("拦截请求 >> " + requestPath + ";请求类型 >> " + request.getMethod());

        // 权限从redis中取,如果有就用redis中的,如果没有就走db然后存放到redis
        String key = JedisConstant.PERMISSION + enter.getUserId();
        HashSet<String> permsSet = Sets.newHashSet();
        Boolean keyExistFlag = redisTemplate.hasKey(key);

        if (keyExistFlag) {
            String value = (String) redisTemplate.opsForValue().get(key);
            String[] split = value.split(",");
            for (String s : split) {
                permsSet.add(s);
            }
        } else {
            // 得到该用户拥有的权限path
            List<String> permsList = opeSysUserService.findPerms(enter.getUserId());
            if (CollectionUtils.isNotEmpty(permsList)) {
                for (String path : permsList) {
                    if (path.contains(",")) {
                        String[] split = path.split(",");
                        permsSet.addAll(Arrays.asList(split));
                    } else {
                        permsSet.add(path);
                    }
                }
            }

            // todo 接口的权限控制，等数据库的数据完善了  再将下面注释的两行（154、164）放开
            if (CollectionUtils.isEmpty(permsSet)) {
                throw new SesWebRosException(ExceptionCodeEnums.NO_PERM.getCode(), ExceptionCodeEnums.NO_PERM.getMessage());
            } else {
                for (String s : permsSet) {
                    log.info("该用户拥有的权限有:[{}]", s);
                }
                permsSet.addAll(Arrays.asList(whiteList));
            }

            // 存放到redis中
            String val = StringUtils.join(permsSet, ",");
            redisTemplate.opsForValue().set(key, val, 24, TimeUnit.HOURS);
        }

        boolean flag = true;
        for (String perm : permsSet) {
            if (perm.contains(requestPath)) {
                flag = false;
                break;
            }
        }
        if (flag) {
            throw new SesWebRosException(ExceptionCodeEnums.NO_PERM.getCode(), ExceptionCodeEnums.NO_PERM.getMessage());
        }
    }

    @SneakyThrows
    private void checkEnterParameter(GeneralEnter enter,HttpServletRequest request) {
        if (StringUtils.isEmpty(enter.getRequestId())) {
            enter.setRequestId(UUID.randomUUID().toString().replaceAll("-", ""));
        }
        /**笨方法解决@RequestBody映射参数的问题**/
        if (StringUtils.isEmpty(enter.getLanguage())) {
            BeanUtils.populate(enter, request.getParameterMap());
        }
        if (StringUtils.isEmpty(enter.getLanguage())) {
            enter.setLanguage(Locale.ENGLISH.getLanguage());
        }
        enter.setTimestamp((new Date()).getTime());
        if (StringUtils.isBlank(enter.getCountry())) {
            throw new SesWebRosException(ExceptionCodeEnums.COUNTRY_CANNOT_EMPTY.getCode(), ExceptionCodeEnums.COUNTRY_CANNOT_EMPTY.getMessage());
        }
        if (SesStringUtils.isBlank(enter.getLanguage())) {
            throw new SesWebRosException(ExceptionCodeEnums.LANGUAGE_CANNOT_EMPTY.getCode(), ExceptionCodeEnums.LANGUAGE_CANNOT_EMPTY.getMessage());
        }
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
