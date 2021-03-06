package com.redescooter.ses.web.ros.service.website.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.base.Strings;
import com.redescooter.ses.api.common.enums.account.SysUserSourceEnum;
import com.redescooter.ses.api.common.enums.account.SysUserStatusEnum;
import com.redescooter.ses.api.common.enums.base.AppIDEnums;
import com.redescooter.ses.api.common.enums.base.SystemIDEnums;
import com.redescooter.ses.api.common.enums.customer.CustomerSourceEnum;
import com.redescooter.ses.api.common.enums.customer.CustomerStatusEnum;
import com.redescooter.ses.api.common.enums.inquiry.InquiryStatusEnums;
import com.redescooter.ses.api.common.enums.proxy.mail.MailTemplateEventEnums;
import com.redescooter.ses.api.common.vo.base.BaseMailTaskEnter;
import com.redescooter.ses.api.common.vo.base.BaseSendMailEnter;
import com.redescooter.ses.api.common.vo.base.CityNameEnter;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.TokenResult;
import com.redescooter.ses.api.common.vo.base.WebResetPasswordEnter;
import com.redescooter.ses.api.foundation.service.MailMultiTaskService;
import com.redescooter.ses.api.foundation.service.base.CityBaseService;
import com.redescooter.ses.api.foundation.vo.common.CityPostResult;
import com.redescooter.ses.api.foundation.vo.common.CountryCityResult;
import com.redescooter.ses.api.foundation.vo.login.LoginEnter;
import com.redescooter.ses.api.foundation.vo.user.UserToken;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.starter.redis.enums.RedisExpireEnum;
import com.redescooter.ses.tool.crypt.RsaUtils;
import com.redescooter.ses.tool.utils.SesStringUtils;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.constant.StringManaConstant;
import com.redescooter.ses.web.ros.dao.base.OpeCustomerInquiryMapper;
import com.redescooter.ses.web.ros.dao.base.OpeCustomerMapper;
import com.redescooter.ses.web.ros.dm.OpeCustomer;
import com.redescooter.ses.web.ros.dm.OpeCustomerInquiry;
import com.redescooter.ses.web.ros.dm.OpeSysUser;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.OpeCustomerService;
import com.redescooter.ses.web.ros.service.base.OpeSysUserService;
import com.redescooter.ses.web.ros.service.website.WebSiteTokenService;
import com.redescooter.ses.web.ros.vo.website.SignUpEnter;
import com.redescooter.ses.web.ros.vo.website.WebEditCustomerEnter;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisCluster;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @ClassName:WebsiteServiceImpl
 * @description: WebsiteServiceImpl
 * @author: Alex
 * @Version???1.3
 * @create: 2020/05/12 12:00
 */
@Slf4j
@Service
public class WebsiteTokenServiceImpl implements WebSiteTokenService {

    @Autowired
    private OpeCustomerService opeCustomerService;

    @Autowired
    private JedisCluster jedisCluster;

    @DubboReference
    private IdAppService idAppService;

    @Autowired
    private OpeCustomerMapper opeCustomerMapper;

    @DubboReference
    private MailMultiTaskService mailMultiTaskService;

    @Autowired
    private OpeCustomerInquiryMapper opeCustomerInquiryMapper;

    @Autowired
    private OpeSysUserService opeSysUserService;

    @Value("${Request.privateKey}")
    private String privatekey;

    @DubboReference
    private CityBaseService cityBaseService;

    /**
     * ??????
     *
     * @param enter
     * @return
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public TokenResult login(LoginEnter enter) {
        //?????????????????????
        SesStringUtils.objStringTrim(enter);
        if (StringManaConstant.entityIsNotNull(enter.getPassword())) {
            String decryptPassword = "";
            String email = "";
            try {
                email = RsaUtils.decrypt(enter.getLoginName(), privatekey);
                decryptPassword = RsaUtils.decrypt(enter.getPassword(), privatekey);
            } catch (Exception e) {
                throw new SesWebRosException(ExceptionCodeEnums.PASSROD_WRONG.getCode(), ExceptionCodeEnums.PASSROD_WRONG.getMessage());
            }
            enter.setPassword(decryptPassword);
            enter.setLoginName(email);
        }
        //????????????
        OpeSysUser opeSysUser =
                opeSysUserService.getOne(new LambdaQueryWrapper<OpeSysUser>()
                        .eq(OpeSysUser::getLoginName, enter.getLoginName())
                        .eq(OpeSysUser::getDef1, SysUserSourceEnum.WEBSITE.getValue())
                        .eq(OpeSysUser::getAppId, AppIDEnums.SES_WEBSITE.getValue())
                        .last("limit 1"));
        if (StringManaConstant.entityIsNull(opeSysUser)) {
            throw new SesWebRosException(ExceptionCodeEnums.USER_NOT_EXIST.getCode(), ExceptionCodeEnums.USER_NOT_EXIST.getMessage());
        }

        //????????????
        String password = DigestUtils.md5Hex(enter.getPassword() + opeSysUser.getSalt());
        if (!StringUtils.equals(password, opeSysUser.getPassword())) {
            throw new SesWebRosException(ExceptionCodeEnums.PASSROD_WRONG.getCode(), ExceptionCodeEnums.PASSROD_WRONG.getMessage());
        }

        //???????????????token
        if (StringUtils.isNotEmpty(opeSysUser.getLastLoginToken())) {
            jedisCluster.del(opeSysUser.getLastLoginToken());
        }
        //??????token
        UserToken userToken = setToken(enter, opeSysUser);

        //??????????????????
        opeSysUser.setLastLoginToken(userToken.getToken());
        opeSysUser.setUpdatedBy(enter.getUserId());
        opeSysUser.setUpdatedTime(new Date());
        opeSysUserService.updateById(opeSysUser);

        TokenResult result = new TokenResult();
        result.setToken(userToken.getToken());
        result.setRequestId(enter.getRequestId());
        return result;
    }

    /**
     * ????????????
     *
     * @param enter
     * @return
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult logout(GeneralEnter enter) {
        String token = enter.getToken();
        jedisCluster.del(token);
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * ????????????
     *
     * @param enter
     * @return
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult signUp(SignUpEnter enter) {

        //?????????????????????
        SesStringUtils.objStringTrim(enter);

        if (StringUtils.isEmpty(enter.getPassword())) {
            throw new SesWebRosException(ExceptionCodeEnums.PASSWORD_EMPTY.getCode(), ExceptionCodeEnums.PASSWORD_EMPTY.getMessage());
        }
        String decryptEamil = null;
        if (StringUtils.isNotEmpty(enter.getEmail())) {
            try {
                //????????????
                decryptEamil = RsaUtils.decrypt(enter.getEmail(), privatekey);
            } catch (Exception e) {
                throw new SesWebRosException(ExceptionCodeEnums.EMAIL_ALREADY_EXISTS.getCode(), ExceptionCodeEnums.EMAIL_ALREADY_EXISTS.getMessage());
            }
            enter.setEmail(decryptEamil);
        }

        //????????????
        OpeSysUser sysUser = opeSysUserService.getOne(new LambdaQueryWrapper<OpeSysUser>().eq(OpeSysUser::getLoginName, decryptEamil)
                .eq(OpeSysUser::getDef1, SysUserSourceEnum.WEBSITE.getValue()));
        if (StringManaConstant.entityIsNotNull(sysUser)) {
            throw new SesWebRosException(ExceptionCodeEnums.EMAIL_ALREADY_EXISTS.getCode(), ExceptionCodeEnums.EMAIL_ALREADY_EXISTS.getMessage());
        }
        //??????????????????
        if (opeCustomerService.count(new LambdaQueryWrapper<OpeCustomer>().eq(OpeCustomer::getEmail, enter.getEmail())) > 0) {
            throw new SesWebRosException(ExceptionCodeEnums.EMAIL_ALREADY_EXISTS.getCode(), ExceptionCodeEnums.EMAIL_ALREADY_EXISTS.getMessage());
        }
        //????????????
        String decryptPassword = null;
        try {
            //????????????
            decryptPassword = RsaUtils.decrypt(enter.getPassword(), privatekey);
        } catch (Exception e) {
            throw new SesWebRosException(ExceptionCodeEnums.PASSROD_WRONG.getCode(), ExceptionCodeEnums.PASSROD_WRONG.getMessage());
        }
        enter.setPassword(decryptPassword);
        //??????????????????
        checkString(enter.getPassword(), 8, 64);
        //??????????????????
        checkString(enter.getEmail(), 2, 50);

        int salt = RandomUtils.nextInt(10000, 99999);

        opeCustomerService.save(buildCustomerSingle(enter));

        //????????????
        OpeSysUser opeSysUser = buildOpeSysUser(decryptEamil, decryptPassword, salt);
        opeSysUserService.save(opeSysUser);

        //????????????
        BaseMailTaskEnter baseMailTaskEnter = new BaseMailTaskEnter();
        baseMailTaskEnter.setName(enter.getFirstName() + " " + enter.getLastName());
        baseMailTaskEnter.setEvent(MailTemplateEventEnums.WEBSITE_SIGN_UP.getEvent());
        baseMailTaskEnter.setSystemId(SystemIDEnums.REDE_SES.getSystemId());
        baseMailTaskEnter.setAppId(AppIDEnums.SES_ROS.getValue());
        baseMailTaskEnter.setEmail(enter.getEmail());
        baseMailTaskEnter.setRequestId(enter.getRequestId());
        baseMailTaskEnter.setUserId(opeSysUser.getId());
        mailMultiTaskService.addMultiMailTask(baseMailTaskEnter);
        return new GeneralResult(enter.getRequestId());
    }

    private OpeSysUser buildOpeSysUser(String decryptEamil, String decryptPassword, int salt) {
        return OpeSysUser.builder()
                .id(idAppService.getId(SequenceName.OPE_SYS_USER))
                .dr(0)
                .deptId(0L)
                .orgStaffId(0L)
                .appId(AppIDEnums.SES_WEBSITE.getValue())
                .systemId(AppIDEnums.SES_ROS.getSystemId())
                .password(DigestUtils.md5Hex(decryptPassword + salt))
                .salt(String.valueOf(salt))
                .status(SysUserStatusEnum.NORMAL.getCode())
                .loginName(decryptEamil)
                .lastLoginToken(null)
                .lastLoginIp(null)
                .lastLoginTime(null)
                .activationTime(new Date())
                .expireDate(null)
                .createdBy(0L)
                .createdTime(new Date())
                .updatedBy(0L)
                .updatedTime(new Date())
                .def1(SysUserSourceEnum.WEBSITE.getValue())
                .build();
    }

    /**
     * ????????????token ??????
     *
     * @param enter
     * @return
     */
    @Override
    public UserToken checkCustomerToken(GeneralEnter enter) {
        UserToken userToken = getUserToken(enter.getToken());
        if (!StringUtils.equals(userToken.getClientType(), enter.getClientType()) ||
                !StringUtils.equals(userToken.getSystemId(), enter.getSystemId()) ||
                !StringUtils.equals(userToken.getAppId(),
                        enter.getAppId())) {
            throw new SesWebRosException(ExceptionCodeEnums.TOKEN_NOT_EXIST.getCode(), ExceptionCodeEnums.TOKEN_NOT_EXIST.getMessage());
        }
        return userToken;
    }

    @Override
    public GeneralResult sendEmail(BaseSendMailEnter baseSendMailEnter) {

        if (Strings.isNullOrEmpty(baseSendMailEnter.getMail())) {
            throw new SesWebRosException(ExceptionCodeEnums.MAIL_NAME_CANNOT_EMPTY.getCode(), ExceptionCodeEnums.MAIL_NAME_CANNOT_EMPTY.getMessage());
        }
        String decryptMail = null;
        if (StringUtils.isNotEmpty(baseSendMailEnter.getMail())) {
            try {
                //????????????
                decryptMail = RsaUtils.decrypt(baseSendMailEnter.getMail(), privatekey);
            } catch (Exception e) {
                throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
            }
            baseSendMailEnter.setMail(decryptMail);

            //??????????????????
            checkString(baseSendMailEnter.getMail(), 2, 50);
        }
        //??????????????????????????????
        QueryWrapper<OpeSysUser> qw = new QueryWrapper<>();
        OpeSysUser opeSysUser = opeSysUserService.getOne(new LambdaQueryWrapper<OpeSysUser>().eq(OpeSysUser::getDef1, SysUserSourceEnum.WEBSITE.getValue()).eq(OpeSysUser::getLoginName,
                baseSendMailEnter.getMail()).last("limit 1"));
        if (StringManaConstant.entityIsNull(opeSysUser)) {
            throw new SesWebRosException(ExceptionCodeEnums.USER_NOT_EXIST.getCode(), ExceptionCodeEnums.USER_NOT_EXIST.getMessage());
        }
        BaseMailTaskEnter enter = new BaseMailTaskEnter();
        enter.setName(baseSendMailEnter.getMail().substring(0, decryptMail.indexOf("@")));
        enter.setEvent(MailTemplateEventEnums.FORGET_PSD_SEND_MAIL.getEvent());
        enter.setSystemId(SystemIDEnums.REDE_SES.getSystemId());
        enter.setAppId(AppIDEnums.SES_ROS.getValue());
        enter.setEmail(decryptMail);
        enter.setRequestId(baseSendMailEnter.getRequestId());
        enter.setUserId(opeSysUser.getId());
        mailMultiTaskService.addMultiMailTask(enter);
        return new GeneralResult(baseSendMailEnter.getRequestId());
    }


    /**
     * ???????????????token
     *
     * @param enter
     * @param opeSysUser
     * @return
     */
    private UserToken setToken(LoginEnter enter, OpeSysUser opeSysUser) {
        String token = UUID.randomUUID().toString().replaceAll("-", "");
        UserToken userToken = new UserToken();
        userToken.setToken(token);
        userToken.setUserId(opeSysUser.getId());
        userToken.setTenantId(new Long("0"));
        userToken.setSystemId(enter.getSystemId());
        userToken.setAppId(enter.getAppId());
        userToken.setClientIp(enter.getClientIp());
        userToken.setClientType(enter.getClientType());
        userToken.setCountry(enter.getCountry());
        userToken.setLanguage(enter.getLanguage());
        userToken.setTimestamp(enter.getTimestamp());
        userToken.setTimeZone(enter.getTimeZone());
        userToken.setVersion(enter.getVersion());
        userToken.setDeptId(opeSysUser.getDeptId());

        try {
            Map<String, String> map = org.apache.commons.beanutils.BeanUtils.describe(userToken);
            map.remove("requestId");
            jedisCluster.hmset(token, map);
            jedisCluster.expire(token, new Long(RedisExpireEnum.HOURS_24.getSeconds()).intValue());
        } catch (IllegalAccessException e) {
            log.error("setToken IllegalAccessException userSession:" + userToken, e);
        } catch (InvocationTargetException e) {
            log.error("setToken InvocationTargetException userSession:" + userToken, e);
        } catch (NoSuchMethodException e) {
            log.error("setToken NoSuchMethodException userSession:" + userToken, e);
        }
        return userToken;
    }

    private UserToken getUserToken(String token) {
        if (StringUtils.isBlank(token)) {
            throw new SesWebRosException(ExceptionCodeEnums.TOKEN_NOT_EXIST.getCode(), ExceptionCodeEnums.TOKEN_NOT_EXIST.getMessage());
        }
        Map<String, String> map = jedisCluster.hgetAll(token);
        if (StringManaConstant.entityIsNull(map)) {
            throw new SesWebRosException(ExceptionCodeEnums.TOKEN_NOT_EXIST.getCode(), ExceptionCodeEnums.TOKEN_NOT_EXIST.getMessage());
        }
        UserToken userToken = new UserToken();
        try {
            BeanUtils.populate(userToken, map);
        } catch (IllegalAccessException e) {
            log.error("checkToken IllegalAccessException sessionMap:" + map, e);
        } catch (InvocationTargetException e) {
            log.error("checkToken IllegalAccessException sessionMap:" + map, e);
        }
        return userToken;
    }


    @Override
    public GeneralResult forgetPassword(WebResetPasswordEnter enter) {
        //??????????????????????????????????????????????????????????????????
        if (!Strings.isNullOrEmpty(enter.getNewPassword()) && !Strings.isNullOrEmpty(enter.getConfirmPassword())) {
            String decrypt = null;
            String confirmDecrypt = null;
            try {
                //????????????
                decrypt = RsaUtils.decrypt(SesStringUtils.stringTrim(enter.getNewPassword()), privatekey);
                confirmDecrypt = RsaUtils.decrypt(SesStringUtils.stringTrim(enter.getConfirmPassword()), privatekey);
            } catch (Exception e) {
                throw new SesWebRosException(ExceptionCodeEnums.PASSROD_WRONG.getCode(), ExceptionCodeEnums.PASSROD_WRONG.getMessage());
            }
            enter.setNewPassword(decrypt);
            enter.setConfirmPassword(confirmDecrypt);

            //??????????????????
            checkString(enter.getNewPassword(), 8, 64);
            //??????????????????
            checkString(enter.getConfirmPassword(), 8, 64);
        }
        //??????????????????????????????
        if (!StringUtils.equals(enter.getNewPassword(), enter.getConfirmPassword())) {
            throw new SesWebRosException(ExceptionCodeEnums.INCONSISTENT_PASSWORD.getCode(), ExceptionCodeEnums.INCONSISTENT_PASSWORD.getMessage());
        }
        //??????????????????  ????????????????????????????????????  ???????????????
        if (!jedisCluster.exists(enter.getRequestId())) {
            throw new SesWebRosException(ExceptionCodeEnums.TOKEN_IS_EXPIRED.getCode(), ExceptionCodeEnums.TOKEN_IS_EXPIRED.getMessage());
        }
        Map<String, String> map = jedisCluster.hgetAll(enter.getRequestId());
        if (StringManaConstant.entityIsNull(map)) {
            throw new SesWebRosException(ExceptionCodeEnums.TOKEN_IS_EXPIRED.getCode(), ExceptionCodeEnums.TOKEN_IS_EXPIRED.getMessage());
        }

        OpeSysUser opeSysUser = opeSysUserService.getById(map.get("userId"));
        if (StringManaConstant.entityIsNull(opeSysUser)) {
            throw new SesWebRosException(ExceptionCodeEnums.USER_NOT_EXIST.getCode(), ExceptionCodeEnums.USER_NOT_EXIST.getMessage());
        }

        //?????????????????? ?????????
        if (StringUtils.equals(DigestUtils.md5Hex(enter.getNewPassword() + opeSysUser.getSalt()), opeSysUser.getPassword())) {
            throw new SesWebRosException(ExceptionCodeEnums.NEW_AND_OLD_PASSWORDS_ARE_THE_SAME.getCode(), ExceptionCodeEnums.NEW_AND_OLD_PASSWORDS_ARE_THE_SAME.getMessage());
        }

        int salt = RandomUtils.nextInt(10000, 99999);
        String newPassword = DigestUtils.md5Hex(enter.getNewPassword() + salt);
        opeSysUser.setPassword(newPassword);
        opeSysUser.setSalt(String.valueOf(salt));
        opeSysUserService.updateById(opeSysUser);

        //?????????????????????????????????????????????????????????
        jedisCluster.del(enter.getRequestId());
        return new GeneralResult(enter.getRequestId());
    }

    @Override
    public GeneralResult resetPassword(WebResetPasswordEnter enter) {
        //??????????????????????????????????????????????????????????????????
        if (!Strings.isNullOrEmpty(enter.getNewPassword()) && !Strings.isNullOrEmpty(enter.getConfirmPassword())) {
            String newPassword = null;
            String confirmDecrypt = null;
            String oldPsd = "";
            try {
                //????????????
                newPassword = RsaUtils.decrypt(SesStringUtils.stringTrim(enter.getNewPassword()), privatekey);
                confirmDecrypt = RsaUtils.decrypt(SesStringUtils.stringTrim(enter.getConfirmPassword()), privatekey);
                oldPsd = RsaUtils.decrypt(SesStringUtils.stringTrim(enter.getOldPassword()), privatekey);
            } catch (Exception e) {
                throw new SesWebRosException(ExceptionCodeEnums.PASSROD_WRONG.getCode(), ExceptionCodeEnums.PASSROD_WRONG.getMessage());
            }
            enter.setNewPassword(newPassword);
            enter.setConfirmPassword(confirmDecrypt);
            enter.setOldPassword(oldPsd);
            //??????????????????
            checkString(enter.getOldPassword(), 8, 64);
            //??????????????????
            checkString(enter.getNewPassword(), 8, 64);
            //??????????????????
            checkString(enter.getConfirmPassword(), 8, 64);
        }

        //??????????????????????????????
        if (StringUtils.equals(enter.getNewPassword(), enter.getOldPassword())) {
            throw new SesWebRosException(ExceptionCodeEnums.NEW_AND_OLD_PASSWORDS_ARE_THE_SAME.getCode(), ExceptionCodeEnums.NEW_AND_OLD_PASSWORDS_ARE_THE_SAME.getMessage());
        }
        if (!StringUtils.equals(enter.getNewPassword(), enter.getConfirmPassword())) {
            throw new SesWebRosException(ExceptionCodeEnums.INCONSISTENT_PASSWORD.getCode(), ExceptionCodeEnums.INCONSISTENT_PASSWORD.getMessage());
        }
        Map<String, String> stringStringMap = jedisCluster.hgetAll(enter.getToken());

        OpeSysUser opeSysUser = opeSysUserService.getById(stringStringMap.get("userId"));
        if (StringManaConstant.entityIsNull(opeSysUser)) {
            throw new SesWebRosException(ExceptionCodeEnums.USER_NOT_EXIST.getCode(), ExceptionCodeEnums.USER_NOT_EXIST.getMessage());
        }
        if (Strings.isNullOrEmpty(enter.getOldPassword())) {
            throw new SesWebRosException(ExceptionCodeEnums.PASSWORD_EMPTY.getCode(), ExceptionCodeEnums.PASSWORD_EMPTY.getMessage());
        }
        if (!DigestUtils.md5Hex(enter.getOldPassword() + opeSysUser.getSalt()).equals(opeSysUser.getPassword())) {
            throw new SesWebRosException(ExceptionCodeEnums.PASSROD_WRONG.getCode(), ExceptionCodeEnums.PASSROD_WRONG.getMessage());
        }
        int salt = RandomUtils.nextInt(10000, 99999);
        String newPassword = DigestUtils.md5Hex(enter.getNewPassword() + salt);
        opeSysUser.setPassword(newPassword);
        opeSysUser.setSalt(String.valueOf(salt));
        opeSysUser.setUpdatedTime(new Date());
        opeSysUserService.updateById(opeSysUser);
        return new GeneralResult(enter.getRequestId());
    }


    @Override
    public GeneralResult editCustomer(WebEditCustomerEnter enter) {
        if (StringManaConstant.entityIsNull(enter)) {
            throw new SesWebRosException(ExceptionCodeEnums.USER_NOT_EXIST.getCode(), ExceptionCodeEnums.USER_NOT_EXIST.getMessage());
        }
        // ???????????????  ?????????????????????????????????  ????????????
        OpeSysUser opeSysUser = opeSysUserService.getById(enter.getUserId());
        if (StringManaConstant.entityIsNull(opeSysUser)) {
            throw new SesWebRosException(ExceptionCodeEnums.USER_NOT_EXIST.getCode(), ExceptionCodeEnums.USER_NOT_EXIST.getMessage());
        }
        OpeCustomer customer = opeCustomerService.getOne(new LambdaQueryWrapper<OpeCustomer>().eq(OpeCustomer::getEmail, opeSysUser.getLoginName()).eq(OpeCustomer::getCustomerSource,
                CustomerSourceEnum.WEBSITE.getValue()));
        if (StringManaConstant.entityIsNull(customer)) {
            throw new SesWebRosException(ExceptionCodeEnums.CUSTOMER_NOT_EXIST.getCode(), ExceptionCodeEnums.CUSTOMER_NOT_EXIST.getMessage());
        }
        if (StringUtils.isNotEmpty(enter.getAddress())) {
            customer.setAddress(enter.getAddress());
        }
        customer.setDef1(enter.getCustomerCountry());
        customer.setDef2(enter.getCity());
        customer.setDistrust(Long.valueOf(enter.getDistrict()));
        customer.setCountry(enter.getCountryId());
        customer.setAddress(enter.getAddress());
        if (!StringUtils.isAllBlank(enter.getLat(), enter.getLng(), enter.getPlaceId())) {
            customer.setLatitude(new BigDecimal(enter.getLat()));
            customer.setLongitude(new BigDecimal(enter.getLng()));
            customer.setPlaceId(enter.getPlaceId());
        }
        customer.setCustomerFirstName(SesStringUtils.upperCaseString(enter.getFirstName()));
        customer.setCustomerLastName(SesStringUtils.upperCaseString(enter.getLastName()));
        customer.setCustomerFullName(new StringBuilder(SesStringUtils.upperCaseString(enter.getFirstName())).append(" ").append(SesStringUtils.upperCaseString(enter.getLastName())).toString());
        opeCustomerMapper.updateById(customer);
        QueryWrapper<OpeCustomerInquiry> qw = new QueryWrapper<>();
        qw.eq("customer_id", customer.getId());
        qw.eq("status", InquiryStatusEnums.UNPAY_DEPOSIT.getValue());
        qw.eq("customer_source", CustomerSourceEnum.WEBSITE.getValue());
        List<OpeCustomerInquiry> list = opeCustomerInquiryMapper.selectList(qw);
        if (CollectionUtils.isNotEmpty(list)) {
            List<OpeCustomerInquiry> update = new ArrayList<>();
            for (OpeCustomerInquiry inquiry : list) {
                inquiry.setFirstName(customer.getCustomerFirstName());
                inquiry.setLastName(customer.getCustomerLastName());
                inquiry.setFullName(customer.getCustomerFullName());
                inquiry.setCountry(enter.getCustomerCountry());
                inquiry.setAddress(customer.getAddress());
                update.add(inquiry);
            }
            opeCustomerInquiryMapper.updateBatch(update);
        }
        return new GeneralResult(enter.getRequestId());
    }

    private OpeCustomer buildCustomerSingle(SignUpEnter enter) {
        OpeCustomer saveCustomer = new OpeCustomer();
        saveCustomer.setId(idAppService.getId(SequenceName.OPE_CUSTOMER));
        saveCustomer.setDr(0);
        saveCustomer.setTenantId(enter.getTenantId() == null ? 0L : enter.getTenantId());
        saveCustomer.setTimeZone(enter.getTimeZone());
        saveCustomer.setCustomerFirstName(SesStringUtils.upperCaseString(enter.getFirstName()));
        saveCustomer.setCustomerLastName(SesStringUtils.upperCaseString(enter.getLastName()));
        saveCustomer.setCustomerFullName(new StringBuilder(SesStringUtils.upperCaseString(enter.getFirstName())).append(" ").append(SesStringUtils.upperCaseString(enter.getLastName())).toString());
        saveCustomer.setEmail(enter.getEmail());
        saveCustomer.setScooterQuantity(1);
        saveCustomer.setAssignationScooterQty(0);
        saveCustomer.setCustomerSource(CustomerSourceEnum.WEBSITE.getValue());
        saveCustomer.setStatus(CustomerStatusEnum.PREDESTINATE_CUSTOMER.getValue());
        saveCustomer.setUpdatedBy(0L);
        saveCustomer.setUpdatedTime(new Date());
        saveCustomer.setCreatedBy(0L);
        saveCustomer.setCreatedTime(new Date());
        saveCustomer.setAccountFlag("0");
        saveCustomer.setAddress(enter.getAddress());

        if (!StringUtils.isAllBlank(enter.getLat(), enter.getLng(), enter.getPlaceId())) {
            saveCustomer.setLatitude(new BigDecimal(enter.getLat()));
            saveCustomer.setLongitude(new BigDecimal(enter.getLng()));
            saveCustomer.setPlaceId(enter.getPlaceId());
        }
        saveCustomer.setDistrust(Long.valueOf(enter.getDistrict()));
        saveCustomer.setDef1(enter.getCustomerCountry());
        //???????????????def1?????????????????????  ??????def2???????????????
        saveCustomer.setDef2(enter.getCity());
        saveCustomer.setDef3(enter.getDistrict());
        saveCustomer.setCountry(enter.getCountryId());
        return saveCustomer;
    }


    private void checkString(String str, int min, int max) {
        if (StringUtils.isEmpty(str)) {
            throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
        }
        if (str.length() < min || str.length() > max) {
            throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
        }
    }


    @Override
    public List<CountryCityResult> countryAndCity() {
        return cityBaseService.countryAndCity();
    }

    @Override
    public List<CityPostResult> cityPostCode(String cityName) {
        return cityBaseService.cityPostCode(cityName);
    }

    @Override
    public List<CountryCityResult> countryCityPostCode(CityNameEnter cityNameEnter) {
        return cityBaseService.countryCityPostCode(cityNameEnter);
    }
}
