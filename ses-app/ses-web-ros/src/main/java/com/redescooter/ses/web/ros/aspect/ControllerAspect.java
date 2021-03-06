package com.redescooter.ses.web.ros.aspect;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
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
import com.redescooter.ses.starter.redis.enums.RedisExpireEnum;
import com.redescooter.ses.tool.aspect.ValidationUtil;
import com.redescooter.ses.tool.utils.SesStringUtils;
import com.redescooter.ses.tool.utils.list.ListUtils;
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
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import redis.clients.jedis.JedisCluster;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


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
    private JedisCluster jedisCluster;

    @Autowired
    private WebApplicationContext applicationContext;

    @Around("execution(* com.redescooter.ses.web.ros.controller..*.*(..))")
    public Object check(ProceedingJoinPoint point) throws Throwable {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
        Object[] objs = point.getArgs();
        try {
            for (Object obj : objs) {
                if (obj instanceof GeneralEnter) {
                    //TODO ????????????????????????
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
        //ros ??????????????????
        if (method.getAnnotation(IgnoreLoginCheck.class) == null  && method.getAnnotation(WebsiteSignIn.class) == null) {
            UserToken userToken = tokenRosService.checkToken(enter);
            enter.setUserId(userToken.getUserId());
            enter.setTenantId(userToken.getTenantId());
            enter.setOpeDeptId(userToken.getDeptId());
            //TODO ??????????????????
            checkPermission(point, enter);
        }
        //website ??????
        if (method.getAnnotation(WebsiteSignIn.class) != null) {
            UserToken userToken = webSiteService.checkCustomerToken(enter);
            enter.setUserId(userToken.getUserId());
            enter.setTenantId(userToken.getTenantId());
        }

    }

    /*private static final String[] whiteList = {
            // ??????,??????,?????????,????????????????????????
            "/sign/token/login",
            "/sign/token/logout",
            "/setup/sys/menu/tree",
            "/Info/getUserInfo",

            // ???????????????
            "/sign/token/emailLoginSendCode",
            "/sign/token/emailLogin",

            // ????????????
            "/sign/token/sendForgetPasswordEmail",
            "/sign/token/chanage",

            // ????????????--??????
            "/organization/sys/dept/saveDeptSelectParent",
            "/organization/sys/dept/principal",

            // ????????????--??????
            "/organization/sys/position/selectPositionType",

            // ????????????--??????
            "/organization/sys/role/roleData",
            "/country/countryCodelist", // ???common?????????

            // Account
            "/account/customer/accountCountStatus",

            // Sales--????????????
            "/website/countryCityPostCode",
            "/sales/contactUs/reply",

            // Sales--RFQ
            "/sales/inquiry/countStatus",
            "/sales/inquiry/depositPaymentEmail",

            // Sales--??????
            "/sales/customer/countStatus",

            // Sales--?????????
            "/sales/sys/allocate/order/whData",
            "/sales/scooter/colorData",
            "/product/specificat/group/specificatGroupData",

            // Sales--???????????????
            "/sales/restproduction/purchas/supplierList",
            "/production/purchasing/queryPurchasProductList",

            // Sales--????????????
            "/bom/sales/product/getType",
            "/bom/sales/after/getType",
            "/sales/scooter/specificatTypeData",

            // Warehouse--????????????
            "/product/production/scooter/colorList",
            "/warehouse/wms/stock/ableProductionScooter",
            "/product/production/scooter/groupList",
            "/warehouse/wms/stock/wmsStockCount",
            "/production/restproduction/assembly/scooterGroupData",
            "/production/restproduction/assembly/colorData",

            // Supply Chain--?????????
            "/supply/distributor/city/cp",
            "/supply/distributor/sale/product",

            // Product--??????
            "/bom/parts/secList",
            "/bom/parts/typeCount",
            "/supply/supplier/list",

            // Product--?????????
            "/product/production/combination/checkProductN",
            "/product/production/combination/secList",
            "/product/production/combination/productionProductPartList",

            // Product--??????
            "/product/production/scooter/productionProductPartList",
            "/product/specificat/type/specificatNameCheck",

            // ??????
            "/product/bom/supplierchaim/currencyUnit",
            "/product/bom/supplierchaim/productPriceHistroyList",
            "/product/bom/supplierchaim/productPriceHistroyChart",

            // ?????????
            "/production/sys/purchase/order/allocateNoData",
            "/sales/sys/allocate/order/userData",

            // ?????????
            "/production/sys/inWhouse/order/relationCombinOrderData",

            // ??????
            "/setup/sys/menu/menuDatas",

            // ????????????
            "/setup/setting/parameter/groupList"
    };*/

    /**
     * ?????????????????????????????????url
     */
    private List<String> getAllUrl() {
        RequestMappingHandlerMapping mapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
        Map<RequestMappingInfo, HandlerMethod> map = mapping.getHandlerMethods();

        List<Map<String, String>> list = Lists.newArrayList();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> m : map.entrySet()) {
            Map<String, String> item = Maps.newHashMap();
            RequestMappingInfo info = m.getKey();
            HandlerMethod method = m.getValue();
            PatternsRequestCondition p = info.getPatternsCondition();
            for (String url : p.getPatterns()) {
                item.put("url", url);
            }
            item.put("className", method.getMethod().getDeclaringClass().getName()); // ??????
            item.put("method", method.getMethod().getName()); // ?????????
            RequestMethodsRequestCondition methodsCondition = info.getMethodsCondition();
            for (RequestMethod requestMethod : methodsCondition.getMethods()) {
                item.put("type", requestMethod.toString());
            }
            list.add(item);
        }
        List<String> url = list.stream().map(o -> o.get("url")).distinct().collect(Collectors.toList());
        return url;
        //url.removeIf(s -> appearNumber(s, "/") < 2);
        //Map<String, List<String>> result = url.stream().collect(Collectors.groupingBy(s -> subString(s, "/")));
        //return result;
    }

    /**
     * ??????????????????
     *
     * @param point
     */
    private void checkPermission(ProceedingJoinPoint point, GeneralEnter enter) {
        // todo 2020 9 14 ????????????????????????????????????????????????????????????????????????return????????????
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

        // ??????????????????
        HttpServletRequest request = SpringContextUtils.getHttpServletRequest();
        String requestPath = request.getRequestURI().substring(request.getContextPath().length());
        requestPath = filterUrl(requestPath);
        log.info("???????????? >> " + requestPath + ";???????????? >> " + request.getMethod());

        // ?????????redis??????,???????????????redis??????,??????????????????db???????????????redis
        String key = JedisConstant.PERMISSION + enter.getUserId();
        HashSet<String> permsSet = Sets.newHashSet();
        Boolean keyExistFlag = jedisCluster.exists(key);

        if (keyExistFlag) {
            String value = jedisCluster.get(key);
            String[] split = value.split(",");
            for (String s : split) {
                permsSet.add(s);
            }
        } else {
            // ??????????????????url
            List<String> urlList = getAllUrl();
            // ??????menu?????????path
            List<String> dbPathList = opeSysUserService.findAllPerms();
            // ????????????list?????????
            List<String> differentList = ListUtils.getDifferent(urlList, dbPathList);
            // ????????????????????????menu?????????path?????????????????????????????????
            permsSet.addAll(differentList);

            // ????????????,??????????????????db???????????????path,???????????????
            List<String> permsList = opeSysUserService.findPerms(enter.getUserId());
            // db????????????path,????????????
            List<String> alreadyOwnList = Lists.newArrayList();
            if (CollectionUtils.isNotEmpty(permsList)) {
                for (String path : permsList) {
                    if (path.contains(",")) {
                        String[] split = path.split(",");
                        alreadyOwnList.addAll(Arrays.asList(split));
                    } else {
                        alreadyOwnList.add(path);
                    }
                }
            }

            // menu?????????path - db????????????path = ????????????path
            List<String> notOwnList = ListUtils.getDifferent(dbPathList, alreadyOwnList);
            if (CollectionUtils.isNotEmpty(notOwnList)) {
                // ??????????????????path,?????????????????????path???db????????????path??????,??????????????????path?????????
                for (String non : notOwnList) {
                    Iterator<String> iterator = alreadyOwnList.iterator();
                    while (iterator.hasNext()) {
                        String own = iterator.next();
                        if (own.contains(non)) {
                            iterator.remove();
                        }
                    }
                }
            }
            permsSet.addAll(alreadyOwnList);

            // todo ??????????????????????????????????????????????????????  ??????????????????????????????154???164?????????
            if (CollectionUtils.isEmpty(permsSet)) {
                throw new SesWebRosException(ExceptionCodeEnums.NO_PERM.getCode(), ExceptionCodeEnums.NO_PERM.getMessage());
            }

            // ?????????redis???
            String val = StringUtils.join(permsSet, ",");
            jedisCluster.set(key, val);
            jedisCluster.expire(key, new Long(RedisExpireEnum.HOURS_24.getSeconds()).intValue());
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
        /**???????????????@RequestBody?????????????????????**/
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

    /**
     * ????????????????????????????????????
     */
    private static int appearNumber(String srcText, String findText) {
        int count = 0;
        Pattern p = Pattern.compile(findText);
        Matcher m = p.matcher(srcText);
        while (m.find()) {
            count++;
        }
        return count;
    }

    /**
     * ??????2?????????????????????????????????????????????
     */
    private static String subString(String str, String subChar) {
        return str.substring(1, str.indexOf(subChar, str.indexOf(subChar) + 1));
    }

}
