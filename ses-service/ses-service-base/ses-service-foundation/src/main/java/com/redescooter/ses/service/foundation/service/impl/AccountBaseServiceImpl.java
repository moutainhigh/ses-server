package com.redescooter.ses.service.foundation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.constant.MaggessConstant;
import com.redescooter.ses.api.common.enums.base.AccountTypeEnums;
import com.redescooter.ses.api.common.enums.base.AppIDEnums;
import com.redescooter.ses.api.common.enums.customer.CustomerTypeEnum;
import com.redescooter.ses.api.common.enums.driver.DriverLoginTypeEnum;
import com.redescooter.ses.api.common.enums.proxy.mail.MailTemplateEventEnums;
import com.redescooter.ses.api.common.enums.tenant.TenanNodeEventEnum;
import com.redescooter.ses.api.common.enums.tenant.TenantStatusEnum;
import com.redescooter.ses.api.common.enums.user.UserEventEnum;
import com.redescooter.ses.api.common.enums.user.UserStatusEnum;
import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.base.BaseCustomerResult;
import com.redescooter.ses.api.common.vo.base.BaseMailTaskEnter;
import com.redescooter.ses.api.common.vo.base.BaseUserResult;
import com.redescooter.ses.api.common.vo.base.BooleanResult;
import com.redescooter.ses.api.common.vo.base.DateTimeParmEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.SetPasswordEnter;
import com.redescooter.ses.api.foundation.exception.FoundationException;
import com.redescooter.ses.api.foundation.service.MailMultiTaskService;
import com.redescooter.ses.api.foundation.service.base.AccountBaseService;
import com.redescooter.ses.api.foundation.service.base.TenantBaseService;
import com.redescooter.ses.api.foundation.service.base.UserBaseService;
import com.redescooter.ses.api.foundation.vo.account.CheckOpenAccountEnter;
import com.redescooter.ses.api.foundation.vo.account.SaveDriverAccountDto;
import com.redescooter.ses.api.foundation.vo.tenant.QueryAccountCountStatusEnter;
import com.redescooter.ses.api.foundation.vo.tenant.QueryAccountListEnter;
import com.redescooter.ses.api.foundation.vo.tenant.QueryAccountResult;
import com.redescooter.ses.api.foundation.vo.user.DeleteUserEnter;
import com.redescooter.ses.api.foundation.vo.user.SaveAccountNodeEnter;
import com.redescooter.ses.api.hub.common.UserProfileService;
import com.redescooter.ses.api.hub.service.operation.CustomerService;
import com.redescooter.ses.api.hub.vo.SaveUserProfileHubEnter;
import com.redescooter.ses.service.foundation.constant.SequenceName;
import com.redescooter.ses.service.foundation.constant.TenantDefaultValue;
import com.redescooter.ses.service.foundation.dao.AccountBaseServiceMapper;
import com.redescooter.ses.service.foundation.dao.base.PlaTenantMapper;
import com.redescooter.ses.service.foundation.dao.base.PlaUserMapper;
import com.redescooter.ses.service.foundation.dao.base.PlaUserPasswordMapper;
import com.redescooter.ses.service.foundation.dao.base.PlaUserPermissionMapper;
import com.redescooter.ses.service.foundation.dm.base.PlaTenant;
import com.redescooter.ses.service.foundation.dm.base.PlaTenantConfig;
import com.redescooter.ses.service.foundation.dm.base.PlaTenantNode;
import com.redescooter.ses.service.foundation.dm.base.PlaUser;
import com.redescooter.ses.service.foundation.dm.base.PlaUserNode;
import com.redescooter.ses.service.foundation.dm.base.PlaUserPassword;
import com.redescooter.ses.service.foundation.dm.base.PlaUserPermission;
import com.redescooter.ses.service.foundation.exception.ExceptionCodeEnums;
import com.redescooter.ses.service.foundation.service.base.PlaTenantConfigService;
import com.redescooter.ses.service.foundation.service.base.PlaTenantNodeService;
import com.redescooter.ses.service.foundation.service.base.PlaUserNodeService;
import com.redescooter.ses.service.foundation.service.base.PlaUserPasswordService;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.starter.redis.enums.RedisExpireEnum;
import com.redescooter.ses.tool.utils.accountType.AccountTypeUtils;
import com.redescooter.ses.tool.utils.date.DateUtil;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisCluster;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 20/12/2019 2:33 ??????
 * @ClassName: AccountBaseServiceImpl
 * @Function: TODO
 */
@Slf4j
@DubboService
public class AccountBaseServiceImpl implements AccountBaseService {

    @Autowired
    private PlaTenantMapper tenantMapper;
    @Autowired
    private PlaUserMapper userMapper;
    @Autowired
    private PlaUserPasswordService userPasswordService;
    @Autowired
    private TenantBaseService tenantBaseService;

    @DubboReference
    private IdAppService idAppService;

    @Autowired
    private PlaUserMapper plaUserMapper;

    @Autowired
    private PlaUserPasswordMapper userPasswordMapper;

    @Autowired
    private PlaUserPermissionMapper userPermissionMapper;

    @Autowired
    private PlaTenantMapper plaTenantMapper;

    @Autowired
    private AccountBaseServiceMapper accountBaseServiceMapper;

    @Autowired
    private JedisCluster jedisCluster;

    @Autowired
    private UserBaseService userBaseService;

    @DubboReference
    private MailMultiTaskService mailMultiTaskService;

    @DubboReference
    private UserProfileService userProfileService;

    @Autowired
    private PlaUserNodeService plaUserNodeService;

    @DubboReference
    private CustomerService customerService;

    @Autowired
    private PlaTenantNodeService plaTenantNodeService;

    @Autowired
    private PlaTenantConfigService plaTenantConfigService;

    /**
     * ????????????
     *
     * @param enter
     * @return
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public BaseUserResult open(DateTimeParmEnter<BaseCustomerResult> enter) {
        Boolean chectMail = chectMail(enter.getT().getEmail());
        if (!chectMail) {
            throw new FoundationException(ExceptionCodeEnums.ACCOUNT_ALREADY_EXIST.getCode(), ExceptionCodeEnums.ACCOUNT_ALREADY_EXIST.getMessage());
        }
        BaseUserResult result = new BaseUserResult();
        // ????????????
        // 1??? ????????????
        Long tenantId = TenantDefaultValue.DEFAULT_MOBILEC_TENANTID;
        if (StringUtils.equals(enter.getT().getCustomerType(), CustomerTypeEnum.ENTERPRISE.getValue())) {
            tenantId = tenantBaseService.saveTenant(enter);
        }
        // 2??? ????????????
        Long userId = saveUserSingle(enter, tenantId);

        result.setId(userId);
        result.setTenantId(tenantId);
        // 3??? ??????????????????
        SaveUserProfileHubEnter saveUserProfileHubEnter =
                SaveUserProfileHubEnter.builder().inputUserId(tenantId).inputTenantId(userId)
                        .firstName(enter.getT().getCustomerFirstName()).lastName(enter.getT().getCustomerLastName())
                        .fullName(new StringBuilder().append(enter.getT().getCustomerFirstName()).append(" ")
                                .append(enter.getT().getCustomerLastName()).toString())
                        .address(enter.getT().getAddress()).certificateType(enter.getT().getCertificateType())
                        .certificateNegativeAnnex(enter.getT().getCertificateNegativeAnnex())
                        .certificatePositiveAnnex(enter.getT().getCertificatePositiveAnnex())
                        .telNumber1(enter.getT().getTelephone()).email1(enter.getT().getEmail()).countryCode1(enter.getT().getCountryCode()).build();
        saveUserProfileHubEnter.setUserId(userId);
        saveUserProfileHubEnter.setTenantId(tenantId);

        if (StringUtils.equals(enter.getT().getCustomerType(), CustomerTypeEnum.PERSONAL.getValue())) {
            userProfileService.saveUserProfile2C(saveUserProfileHubEnter);
        } else {
            userProfileService.saveUserProfile2B(saveUserProfileHubEnter);
        }
        // ??????????????????
        BaseMailTaskEnter baseMailTaskEnter = new BaseMailTaskEnter();
        baseMailTaskEnter.setToMail(enter.getT().getEmail());
        baseMailTaskEnter.setToUserId(userId);
        baseMailTaskEnter.setUserRequestId(enter.getRequestId());
        baseMailTaskEnter.setRequestId(enter.getRequestId());

        if (StringUtils.equals(CustomerTypeEnum.PERSONAL.getValue(), enter.getT().getCustomerType())) {
            baseMailTaskEnter.setEvent(MailTemplateEventEnums.MOBILE_ACTIVATE.getEvent());
            baseMailTaskEnter.setMailAppId(AppIDEnums.SAAS_APP.getValue());
            baseMailTaskEnter.setMailSystemId(AppIDEnums.SAAS_APP.getSystemId());
            baseMailTaskEnter.setName(enter.getT().getCustomerFullName());
        }
        if (StringUtils.equals(CustomerTypeEnum.ENTERPRISE.getValue(), enter.getT().getCustomerType())) {
            baseMailTaskEnter.setEvent(MailTemplateEventEnums.WEB_ACTIVATE.getEvent());
            baseMailTaskEnter.setMailAppId(AppIDEnums.SAAS_WEB.getValue());
            baseMailTaskEnter.setMailSystemId(AppIDEnums.SAAS_WEB.getSystemId());
            baseMailTaskEnter.setName(enter.getT().getContactFullName());
        }
        mailMultiTaskService.addActivateMobileUserTask(baseMailTaskEnter);
        return result;
    }

    @Override
    public Boolean chectMail(String mail) {
        QueryWrapper<PlaUser> wrapper = new QueryWrapper<>();
        wrapper.in(PlaUser.COL_USER_TYPE, AccountTypeEnums.WEB_RESTAURANT.getAccountType(), AccountTypeEnums.WEB_EXPRESS.getAccountType(), AccountTypeEnums.APP_PERSONAL.getAccountType());
        wrapper.eq(PlaUser.COL_LOGIN_NAME, mail);
        return plaUserMapper.selectCount(wrapper) == 0 ? Boolean.TRUE : Boolean.FALSE;
    }

    /**
     * ??????????????????
     *
     * @param nickname
     * @return
     */
    @Override
    public Boolean checkNaickname(String nickname) {

        QueryWrapper<PlaUser> wrapper = new QueryWrapper<>();
        wrapper.eq(PlaUser.COL_LOGIN_NAME, nickname);
        wrapper.eq(PlaUser.COL_DR, Constant.DR_FALSE);

        return userMapper.selectCount(wrapper) == 0 ? Boolean.TRUE : Boolean.FALSE;
    }

    /**
     * ????????????
     *
     * @param enter
     * @return
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult freeze(DateTimeParmEnter<BaseCustomerResult> enter) {
        int accountType =
                AccountTypeUtils.getAccountType(enter.getT().getCustomerType(), enter.getT().getIndustryType());
        String appId = AccountTypeUtils.getAppId(accountType);

        // ??????
        Long customerTenantId = 0L;
        if (accountType == AccountTypeEnums.WEB_EXPRESS.getAccountType() || accountType == AccountTypeEnums.WEB_RESTAURANT.getAccountType()) {
            QueryWrapper<PlaTenant> plaTenantQueryWrapper = new QueryWrapper<>();
            plaTenantQueryWrapper.eq(PlaTenant.COL_DR, 0);
            plaTenantQueryWrapper.eq(PlaTenant.COL_EMAIL, enter.getT().getEmail());
            PlaTenant plaTenant = plaTenantMapper.selectOne(plaTenantQueryWrapper);
            if (null == plaTenant) {
                throw new FoundationException(ExceptionCodeEnums.TENANT_NOT_EXIST.getCode(), ExceptionCodeEnums.TENANT_NOT_EXIST.getMessage());
            }
            if (!StringUtils.equals(TenantStatusEnum.INOPERATION.getValue(), plaTenant.getStatus())) {
                throw new FoundationException(ExceptionCodeEnums.STATUS_IS_REASONABLE.getCode(), ExceptionCodeEnums.STATUS_IS_REASONABLE.getMessage());
            }
            plaTenant.setStatus(TenantStatusEnum.FROZEN.getValue());
            plaTenant.setUpdatedBy(enter.getUserId());
            plaTenant.setUpdatedTime(new Date());
            plaTenantMapper.updateById(plaTenant);
            // ????????????
            tenantBaseService.saveTenantNode(enter, TenanNodeEventEnum.FROZEN.getValue());

            customerTenantId = plaTenant.getId();
        }

        //user ????????????
        List<PlaUser> plaUserList = new ArrayList<>();
        if (accountType == AccountTypeEnums.WEB_EXPRESS.getAccountType() || accountType == AccountTypeEnums.WEB_RESTAURANT.getAccountType()) {
            QueryWrapper<PlaUser> plaUserQueryWrapper = new QueryWrapper<>();
            plaUserQueryWrapper.eq(PlaUser.COL_DR, Constant.DR_FALSE);
            plaUserQueryWrapper.eq(PlaUser.COL_TENANT_ID, customerTenantId);
            plaUserList = plaUserMapper.selectList(plaUserQueryWrapper);
        } else {
            QueryWrapper<PlaUser> plaUserQueryWrapper = new QueryWrapper<>();
            plaUserQueryWrapper.eq(PlaUser.COL_DR, Constant.DR_FALSE);
            plaUserQueryWrapper.eq(PlaUser.COL_LOGIN_NAME, enter.getT().getEmail());
            plaUserQueryWrapper.in(PlaUser.COL_USER_TYPE, customerTypeList());
            plaUserQueryWrapper.last("limit 1");
            PlaUser plaUser = plaUserMapper.selectOne(plaUserQueryWrapper);
            plaUserList.add(plaUser);
        }

        Long tennatUserId = null;
        Set<Long> userIdList = new HashSet<>();
        if (CollectionUtils.isNotEmpty(plaUserList)) {
            // ??? ??????????????????
            List<SaveAccountNodeEnter> userAccountNodeList = new ArrayList<>();

            Boolean tenantAccount = Boolean.FALSE;
            for (PlaUser item : plaUserList) {
                if (StringUtils.equals(item.getLoginName(), enter.getT().getEmail())) {
                    tenantAccount = Boolean.TRUE;
                    tennatUserId = item.getId();
                    if (!StringUtils.equals(UserStatusEnum.NORMAL.getValue(), item.getStatus())) {
                        throw new FoundationException(ExceptionCodeEnums.STATUS_IS_REASONABLE.getCode(), ExceptionCodeEnums.STATUS_IS_REASONABLE.getMessage());
                    }
                }
                item.setStatus(UserStatusEnum.LOCK.getValue());
                item.setUpdatedBy(enter.getUserId());
                item.setUpdatedTime(new Date());

                //??????????????????
                SaveAccountNodeEnter saveAccountNodeEnter = new SaveAccountNodeEnter();
                BeanUtils.copyProperties(enter, saveAccountNodeEnter);
                saveAccountNodeEnter.setInputUserId(item.getId());
                saveAccountNodeEnter.setEvent(UserEventEnum.FROZEN.getValue());
                userAccountNodeList.add(saveAccountNodeEnter);

                //????????????token ???token?????? ??????Token
                if (StringUtils.isNotBlank(item.getLastLoginToken())) {
                    jedisCluster.del(item.getLastLoginToken());
                }
                // ??????emailList ??????????????????
                userIdList.add(item.getId());
            }
            if (!tenantAccount) {
                throw new FoundationException(ExceptionCodeEnums.USER_NOT_EXIST.getCode(), ExceptionCodeEnums.USER_NOT_EXIST.getMessage());
            }
            // ??????????????????
            plaUserMapper.updateBatch(plaUserList);
            // ??????????????????
            userBaseService.saveAccountNodeList(userAccountNodeList);
        }

        // ??????
        QueryWrapper<PlaUserPermission> plaUserPermissionQueryWrapper = new QueryWrapper<>();
        plaUserPermissionQueryWrapper.in(PlaUserPermission.COL_USER_ID, new ArrayList<>(userIdList));
        List<PlaUserPermission> plaUserPermissionList = userPermissionMapper.selectList(plaUserPermissionQueryWrapper);
        // ?????? ???????????????????????????
        if (CollectionUtils.isNotEmpty(plaUserPermissionList)) {
            Boolean tenantAccountPermission = Boolean.FALSE;
            for (PlaUserPermission item : plaUserPermissionList) {
                if (null != tennatUserId && 0 != tennatUserId && item.getUserId().equals(tennatUserId)) {
                    tenantAccountPermission = Boolean.TRUE;

                    if (!StringUtils.equals(UserStatusEnum.NORMAL.getValue(), item.getStatus())) {
                        throw new FoundationException(ExceptionCodeEnums.STATUS_IS_REASONABLE.getCode(), ExceptionCodeEnums.STATUS_IS_REASONABLE.getMessage());
                    }
                }

                item.setStatus(UserStatusEnum.LOCK.getValue());
                item.setUpdatedBy(enter.getUserId());
                item.setUpdatedTime(new Date());
            }
            if (!tenantAccountPermission) {
                throw new FoundationException(ExceptionCodeEnums.USERPERMISSION_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.USERPERMISSION_IS_NOT_EXIST.getMessage());
            }

            // ????????????
            userPermissionMapper.updateBatch(plaUserPermissionList);
        }
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
    public GeneralResult unFreezeAccount(DateTimeParmEnter<BaseCustomerResult> enter) {
        int accountType =
                AccountTypeUtils.getAccountType(enter.getT().getCustomerType(), enter.getT().getIndustryType());

        // ??????
        Long customerTenantId = 0L;
        if (accountType == AccountTypeEnums.WEB_EXPRESS.getAccountType() || accountType == AccountTypeEnums.WEB_RESTAURANT.getAccountType()) {
            QueryWrapper<PlaTenant> plaTenantQueryWrapper = new QueryWrapper<>();
            plaTenantQueryWrapper.eq(PlaTenant.COL_DR, Constant.DR_FALSE);
            plaTenantQueryWrapper.eq(PlaTenant.COL_EMAIL, enter.getT().getEmail());
            PlaTenant plaTenant = plaTenantMapper.selectOne(plaTenantQueryWrapper);
            if (null == plaTenant) {
                throw new FoundationException(ExceptionCodeEnums.TENANT_NOT_EXIST.getCode(), ExceptionCodeEnums.TENANT_NOT_EXIST.getMessage());
            }
            if (!StringUtils.equals(TenantStatusEnum.FROZEN.getValue(), plaTenant.getStatus())) {
                throw new FoundationException(ExceptionCodeEnums.STATUS_IS_REASONABLE.getCode(), ExceptionCodeEnums.STATUS_IS_REASONABLE.getMessage());
            }
            plaTenant.setStatus(TenantStatusEnum.INOPERATION.getValue());
            plaTenant.setUpdatedBy(enter.getUserId());
            plaTenant.setUpdatedTime(new Date());
            plaTenantMapper.updateById(plaTenant);
            // ????????????
            tenantBaseService.saveTenantNode(enter, TenanNodeEventEnum.UNFREEZE.getValue());

            customerTenantId = plaTenant.getId();
        }

        //user ????????????
        List<PlaUser> plaUserList = new ArrayList<>();
        if (accountType == AccountTypeEnums.WEB_EXPRESS.getAccountType() || accountType == AccountTypeEnums.WEB_RESTAURANT.getAccountType()) {
            QueryWrapper<PlaUser> plaUserQueryWrapper = new QueryWrapper<>();
            plaUserQueryWrapper.eq(PlaUser.COL_DR, Constant.DR_FALSE);
            plaUserQueryWrapper.eq(PlaUser.COL_TENANT_ID, customerTenantId);
            plaUserList = plaUserMapper.selectList(plaUserQueryWrapper);
        } else {
            QueryWrapper<PlaUser> plaUserQueryWrapper = new QueryWrapper<>();
            plaUserQueryWrapper.eq(PlaUser.COL_DR, Constant.DR_FALSE);
            plaUserQueryWrapper.eq(PlaUser.COL_LOGIN_NAME, enter.getT().getEmail());
            plaUserQueryWrapper.in(PlaUser.COL_USER_TYPE, customerTypeList());
            PlaUser plaUser = plaUserMapper.selectOne(plaUserQueryWrapper);
            plaUserList.add(plaUser);
        }

        Long tennatUserId = null;
        Set<Long> userIdList = new HashSet<>();
        if (CollectionUtils.isNotEmpty(plaUserList)) {
            // ??? ??????????????????
            List<SaveAccountNodeEnter> userAccountNodeList = new ArrayList<>();

            Boolean tenantAccount = Boolean.FALSE;
            for (PlaUser item : plaUserList) {
                if (StringUtils.equals(item.getLoginName(), enter.getT().getEmail())) {
                    tenantAccount = Boolean.TRUE;
                    tennatUserId = item.getId();
                    if (!StringUtils.equals(UserStatusEnum.LOCK.getValue(), item.getStatus())) {
                        throw new FoundationException(ExceptionCodeEnums.STATUS_IS_REASONABLE.getCode(), ExceptionCodeEnums.STATUS_IS_REASONABLE.getMessage());
                    }
                }
                item.setStatus(UserStatusEnum.NORMAL.getValue());
                item.setUpdatedBy(enter.getUserId());
                item.setUpdatedTime(new Date());

                //??????????????????
                SaveAccountNodeEnter saveAccountNodeEnter = new SaveAccountNodeEnter();
                BeanUtils.copyProperties(enter, saveAccountNodeEnter);
                saveAccountNodeEnter.setInputUserId(item.getId());
                saveAccountNodeEnter.setEvent(UserEventEnum.UNFREEZE.getValue());
                userAccountNodeList.add(saveAccountNodeEnter);

                // ??????emailList ??????????????????
                userIdList.add(item.getId());
            }
            if (!tenantAccount) {
                throw new FoundationException(ExceptionCodeEnums.USER_NOT_EXIST.getCode(), ExceptionCodeEnums.USER_NOT_EXIST.getMessage());
            }
            // ??????????????????
            plaUserMapper.updateBatch(plaUserList);
            // ??????????????????
            userBaseService.saveAccountNodeList(userAccountNodeList);
        }

        // ??????
        QueryWrapper<PlaUserPermission> plaUserPermissionQueryWrapper = new QueryWrapper<>();
        plaUserPermissionQueryWrapper.in(PlaUserPermission.COL_USER_ID, new ArrayList<>(userIdList));
        List<PlaUserPermission> plaUserPermissionList = userPermissionMapper.selectList(plaUserPermissionQueryWrapper);
        // ?????? ???????????????????????????
        if (CollectionUtils.isNotEmpty(plaUserPermissionList)) {
            Boolean tenantAccountPermission = Boolean.FALSE;
            for (PlaUserPermission item : plaUserPermissionList) {
                if (null != tennatUserId && 0 != tennatUserId && item.getUserId().equals(tennatUserId)) {
                    tenantAccountPermission = Boolean.TRUE;

                    if (!StringUtils.equals(UserStatusEnum.LOCK.getValue(), item.getStatus())) {
                        throw new FoundationException(ExceptionCodeEnums.STATUS_IS_REASONABLE.getCode(), ExceptionCodeEnums.STATUS_IS_REASONABLE.getMessage());
                    }
                }

                item.setStatus(UserStatusEnum.NORMAL.getValue());
                item.setUpdatedBy(enter.getUserId());
                item.setUpdatedTime(new Date());
            }
            if (!tenantAccountPermission) {
                throw new FoundationException(ExceptionCodeEnums.USERPERMISSION_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.USERPERMISSION_IS_NOT_EXIST.getMessage());
            }
            // ????????????
            userPermissionMapper.updateBatch(plaUserPermissionList);
        }
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * ????????????
     * // 1??? ?????????????????? ???????????????????????????
     * // 2????????????????????? ???????????????????????????
     * // 3??? ?????????????????? ???????????????????????????
     *
     * @param enter
     * @return
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult renewAccont(DateTimeParmEnter<BaseCustomerResult> enter) {
        if (0 > DateUtil.timeComolete(enter.getStartDateTime(), enter.getEndDateTime())) {
            throw new FoundationException(ExceptionCodeEnums.RENEW_END_DATETIME_IS_NOT_AVAILABLE.getCode(),
                    ExceptionCodeEnums.RENEW_END_DATETIME_IS_NOT_AVAILABLE.getMessage());
        }

        int accountType =
                AccountTypeUtils.getAccountType(enter.getT().getCustomerType(), enter.getT().getIndustryType());

        // ??????
        PlaTenant plaTenant = null;
        if (accountType == AccountTypeEnums.WEB_EXPRESS.getAccountType() || accountType == AccountTypeEnums.WEB_RESTAURANT.getAccountType()) {
            plaTenant = plaTenantMapper.selectById(enter.getT().getTenantId());
            if (null == plaTenant) {
                throw new FoundationException(ExceptionCodeEnums.TENANT_NOT_EXIST.getCode(),
                        ExceptionCodeEnums.TENANT_NOT_EXIST.getMessage());
            }
            // ??????????????????
            if (StringUtils.equals(TenantStatusEnum.FROZEN.getValue(), plaTenant.getStatus())) {
                throw new FoundationException(ExceptionCodeEnums.STATUS_IS_REASONABLE.getCode(),
                        ExceptionCodeEnums.STATUS_IS_REASONABLE.getMessage());
            }
            if (0 > DateUtil.timeComolete(plaTenant.getExpireTime(), enter.getEndDateTime())) {
                throw new FoundationException(ExceptionCodeEnums.RENEW_END_DATETIME_IS_NOT_AVAILABLE.getCode(),
                        ExceptionCodeEnums.RENEW_END_DATETIME_IS_NOT_AVAILABLE.getMessage());
            }

            if (0 >= DateUtil.timeComolete(plaTenant.getEffectiveTime(), enter.getStartDateTime())) {
                throw new FoundationException(ExceptionCodeEnums.RENEW_START_DATETIME_IS_NOT_AVAILABLE.getCode(),
                        ExceptionCodeEnums.RENEW_END_DATETIME_IS_NOT_AVAILABLE.getMessage());
            }
            plaTenant.setExpireTime(enter.getEndDateTime());
            plaTenant.setUpdatedTime(new Date());
            plaTenant.setUpdatedBy(enter.getUserId());
            // ????????????
            plaTenantMapper.updateById(plaTenant);
            // ????????????
            tenantBaseService.saveTenantNode(enter, TenanNodeEventEnum.RENEW.getValue());
        }
        // ????????????
        QueryWrapper<PlaUser> plaUserQueryWrapper = new QueryWrapper<>();
        plaUserQueryWrapper.eq(PlaUser.COL_DR, Constant.DR_FALSE);
        plaUserQueryWrapper.eq(PlaUser.COL_LOGIN_NAME, enter.getT().getEmail());
        plaUserQueryWrapper.in(PlaUser.COL_USER_TYPE, customerTypeList());
        PlaUser plaUser = plaUserMapper.selectOne(plaUserQueryWrapper);
        if (null == plaUser) {
            throw new FoundationException(ExceptionCodeEnums.USER_NOT_EXIST.getCode(),
                    ExceptionCodeEnums.USER_NOT_EXIST.getMessage());
        }
        // ??????????????????
        if (StringUtils.equals(UserStatusEnum.LOCK.getValue(), plaUser.getStatus())) {
            throw new FoundationException(ExceptionCodeEnums.STATUS_IS_REASONABLE.getCode(),
                    ExceptionCodeEnums.STATUS_IS_REASONABLE.getMessage());
        }
        if (0 > DateUtil.timeComolete(plaUser.getExpireTime(), enter.getEndDateTime())) {
            throw new FoundationException(ExceptionCodeEnums.RENEW_END_DATETIME_IS_NOT_AVAILABLE.getCode(),
                    ExceptionCodeEnums.RENEW_END_DATETIME_IS_NOT_AVAILABLE.getMessage());
        }

        if (0 >= DateUtil.timeComolete(plaUser.getEffectiveTime(), enter.getStartDateTime())) {
            throw new FoundationException(ExceptionCodeEnums.RENEW_START_DATETIME_IS_NOT_AVAILABLE.getCode(),
                    ExceptionCodeEnums.RENEW_END_DATETIME_IS_NOT_AVAILABLE.getMessage());
        }
        plaUser.setExpireTime(enter.getEndDateTime());
        plaUser.setUpdatedTime(new Date());
        plaUser.setUpdatedBy(enter.getUserId());
        // ????????????
        plaUserMapper.updateById(plaUser);

        // ????????????
        SaveAccountNodeEnter saveAccountNodeEnter = new SaveAccountNodeEnter();
        BeanUtils.copyProperties(enter, saveAccountNodeEnter);
        saveAccountNodeEnter.setInputUserId(plaUser.getId());
        saveAccountNodeEnter.setEvent(UserEventEnum.RENEW.getValue());
        userBaseService.saveAccountNode(saveAccountNodeEnter);
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * @param enter
     * @return
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult setPassword(SetPasswordEnter<BaseCustomerResult> enter) {
        QueryWrapper<PlaUserPassword> plaUserPasswordQueryWrapper = new QueryWrapper<>();
        plaUserPasswordQueryWrapper.eq(PlaUserPassword.COL_LOGIN_NAME, enter.getT().getEmail());
        PlaUserPassword plaUserPassword = userPasswordMapper.selectOne(plaUserPasswordQueryWrapper);
        if (null == plaUserPassword) {
            throw new FoundationException(ExceptionCodeEnums.ACCOUNT_IS_NOT_EXIST.getCode(),
                    ExceptionCodeEnums.ACCOUNT_IS_NOT_EXIST.getMessage());
        }

        String newPassword = DigestUtils.md5Hex(enter.getConfirmPassword() + plaUserPassword.getSalt());
        //????????????????????????
        if (StringUtils.equals(newPassword, plaUserPassword.getPassword())) {
            throw new FoundationException(ExceptionCodeEnums.NEW_AND_OLD_PASSWORDS_ARE_THE_SAME.getCode(), ExceptionCodeEnums.NEW_AND_OLD_PASSWORDS_ARE_THE_SAME.getMessage());
        }

        plaUserPassword.setPassword(newPassword);
        plaUserPassword.setUpdatedBy(enter.getUserId());
        plaUserPassword.setUpdatedTime(new Date());
        userPasswordMapper.updateById(plaUserPassword);
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * ????????????ID??????????????????
     *
     * @param enter
     * @return
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult deleteUser(DeleteUserEnter enter) {
        /**
         * 1.???????????? 2.???????????? 3.??????????????????
         */
        List<Long> ids = new ArrayList<>();
        Long tenantId = 0L;
        QueryWrapper<PlaTenant> plaTenantQueryWrapper = new QueryWrapper<>();
        plaTenantQueryWrapper.eq(PlaTenant.COL_EMAIL, enter.getEmail());
        plaTenantQueryWrapper.eq(PlaTenant.COL_DR, Constant.DR_FALSE);
        PlaTenant tenant = plaTenantMapper.selectOne(plaTenantQueryWrapper);
        if (tenant != null) {
            tenantId = tenant.getId();
            plaTenantMapper.deleteById(tenant);
        }
        if (tenantId.equals(0)) {
            QueryWrapper<PlaUser> wrapper = new QueryWrapper<>();
            wrapper.eq(PlaUser.COL_TENANT_ID, tenantId);
            wrapper.eq(PlaUser.COL_DR, Constant.DR_FALSE);
            List<PlaUser> userList = plaUserMapper.selectList(wrapper);
            if (CollectionUtils.isNotEmpty(userList)) {
                ids = userList.stream().map(user -> user.getId()).collect(Collectors.toList());
            }
        } else {
            QueryWrapper<PlaUser> wrapper = new QueryWrapper<>();
            wrapper.eq(PlaUser.COL_LOGIN_NAME, enter.getEmail());
            wrapper.eq(PlaUser.COL_DR, Constant.DR_FALSE);
            wrapper.in(PlaUser.COL_USER_TYPE, customerTypeList());
            PlaUser plaUser = plaUserMapper.selectOne(wrapper);
            if (plaUser == null) {
                return new GeneralResult(enter.getRequestId());
            }
            ids.add(plaUser.getId());
        }

        if (!tenantId.equals(0L)) {
            //????????????--2B??????
            tenantMapper.deleteById(tenant.getId());
            //????????????
            userProfileService.deleteUserProfile2B(ids);
            //??????????????????
            QueryWrapper<PlaTenantNode> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq(PlaTenantNode.COL_TENANT_ID, tenantId);
            queryWrapper.eq(PlaTenantNode.COL_DR, Constant.DR_FALSE);
            plaTenantNodeService.remove(queryWrapper);

            // ??????????????????
            QueryWrapper<PlaTenantConfig> plaTenantConfigQueryWrapper = new QueryWrapper<>();
            plaTenantConfigQueryWrapper.eq(PlaTenantConfig.COL_TENANT_ID, tenantId);
            plaTenantConfigQueryWrapper.eq(PlaTenantConfig.COL_DR, Constant.DR_FALSE);
            plaTenantConfigService.remove(plaTenantConfigQueryWrapper);
        } else {
            // ????????????--2C?????? TODO
            userProfileService.deleteUserProfile2C(ids);
        }
        //??????????????????
        plaUserMapper.deleteBatchIds(ids);

        //????????????????????????
        QueryWrapper<PlaUserNode> plaUserNodeQueryWrapper = new QueryWrapper<>();
        plaUserNodeQueryWrapper.eq(PlaUserNode.COL_DR, Constant.DR_FALSE);
        plaUserNodeQueryWrapper.in(PlaUserNode.COL_USER_ID, ids);
        plaUserNodeService.remove(plaUserNodeQueryWrapper);


        return new GeneralResult(enter.getRequestId());
    }

    /**
     * ??????2B????????????
     *
     * @param dto
     * @return
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public BaseUserResult openDriver2BAccout(SaveDriverAccountDto dto) {

        PlaTenant tenant = tenantMapper.selectById(dto.getTenantId());

        //??????????????????
        int driverloginType = dto.getDriverLoginType().equals(DriverLoginTypeEnum.EMAIL.getValue()) ?
                Integer.parseInt(DriverLoginTypeEnum.EMAIL.getValue()) :
                Integer.parseInt(DriverLoginTypeEnum.NICKNAME.getValue());


        int accountType = AccountTypeUtils.getAccountType(tenant.getTenantType(), tenant.getTenantIndustry());

        if (accountType == AccountTypeEnums.WEB_EXPRESS.getAccountType()) {
            accountType = AccountTypeEnums.APP_EXPRESS.getAccountType();
        } else {
            accountType = AccountTypeEnums.APP_RESTAURANT.getAccountType();
        }
        QueryWrapper<PlaUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(PlaUser.COL_LOGIN_NAME, driverloginType == 1 ? dto.getEmail() : dto.getNickName());
        queryWrapper.eq(PlaUser.COL_DR, Constant.DR_FALSE);

        List<PlaUser> userList = userMapper.selectList(queryWrapper);
        int count = userList.size();
        if (1 == driverloginType) {
            if (0 < userList.size()) {
                for (PlaUser user : userList) {
                    if (user.getUserType() == accountType) {
                        throw new FoundationException(ExceptionCodeEnums.ACCOUNT_ALREADY_EXIST.getCode(),
                                ExceptionCodeEnums.ACCOUNT_ALREADY_EXIST.getMessage());
                    }
                }
            }
        } else {
            if (0 < count) {
                throw new FoundationException(ExceptionCodeEnums.ACCOUNT_ALREADY_EXIST.getCode(),
                        ExceptionCodeEnums.ACCOUNT_ALREADY_EXIST.getMessage());
            }
        }
        //????????????user??????
        PlaUser user = new PlaUser();
        user.setId(idAppService.getId(SequenceName.PLA_USER));
        user.setDr(Constant.DR_FALSE);
        user.setTenantId(tenant.getId());
        user.setAppId(AccountTypeUtils.getAppId(accountType));
        user.setSystemId(AccountTypeUtils.getSystemId(accountType));
        user.setLoginName(driverloginType == 1 ? dto.getEmail() : dto.getNickName());
        user.setLoginType(driverloginType);
        user.setUserType(accountType);
        if (0 < count) {
            user.setStatus(UserStatusEnum.NORMAL.getValue());
            //????????????????????????
            user.setDef1(MaggessConstant.ACCOUNT_ACTIVAT_AFTER);
        } else {
            user.setStatus(driverloginType == 1 ? UserStatusEnum.INACTIVATED.getValue() : UserStatusEnum.NORMAL.getValue());
            //????????????????????????
            user.setDef1(driverloginType == 1 ? MaggessConstant.ACCOUNT_ACTIVAT_BEFORE : MaggessConstant.ACCOUNT_ACTIVAT_AFTER);
        }

        user.setCreatedBy(dto.getUserId());
        user.setCreatedTime(new Date());
        user.setUpdatedBy(dto.getUserId());
        user.setUpdatedTime(new Date());
        plaUserMapper.insert(user);

        //????????????????????????
        QueryWrapper<PlaUserPassword> queryPassWord = new QueryWrapper<>();
        queryPassWord.eq(PlaUserPassword.COL_LOGIN_NAME, driverloginType == 1 ? dto.getEmail() : dto.getNickName());

        queryPassWord.eq(PlaUserPassword.COL_DR, Constant.DR_FALSE);
        PlaUserPassword passwordServiceOne = userPasswordService.getOne(queryPassWord);

        if (null == passwordServiceOne) {
            String salt = String.valueOf(RandomUtils.nextInt(10000, 99999));
            PlaUserPassword savePassWord = new PlaUserPassword();
            savePassWord.setId(idAppService.getId(SequenceName.PLA_USER_PASSWORD));
            savePassWord.setSalt(salt);

            savePassWord.setLoginName(driverloginType == 1 ? dto.getEmail() : dto.getNickName());
            savePassWord.setPassword(driverloginType == 1 ? null : DigestUtils.md5Hex(dto.getPasswordAgain() + salt));

            savePassWord.setCreatedBy(dto.getUserId());
            savePassWord.setCreatedTime(new Date());
            savePassWord.setUpdatedBy(dto.getUserId());
            savePassWord.setUpdatedTime(new Date());
            //???.?????????????????????????????????????????????????????????????????????
            userPasswordMapper.insert(savePassWord);
        }

        //??????????????????
        PlaUserPermission plaUserPermission = new PlaUserPermission();
        plaUserPermission.setId(idAppService.getId(SequenceName.PLA_USER_PERMISSION));
        plaUserPermission.setUserId(user.getId());
        plaUserPermission.setSystemId(AccountTypeUtils.getSystemId(accountType));
        plaUserPermission.setAppId(AccountTypeUtils.getAppId(accountType));
        plaUserPermission.setStatus(UserStatusEnum.NORMAL.getValue());
        plaUserPermission.setCreatedBy(dto.getUserId());
        plaUserPermission.setCreatedTime(new Date());
        plaUserPermission.setUpdatedBy(dto.getUserId());
        plaUserPermission.setUpdatedTime(new Date());

        userPermissionMapper.insert(plaUserPermission);

        //????????????????????????
        BaseUserResult result = new BaseUserResult();
        result.setId(user.getId());
        result.setTenantId(dto.getTenantId());
        result.setLoginName(user.getLoginName());
        result.setLoginType(driverloginType);
        result.setStatus(user.getStatus());
        result.setUserType(user.getUserType());
        result.setCreatedBy(dto.getUserId());
        result.setCreatedTime(new Date());
        result.setUpdatedBy(dto.getUserId());
        result.setUpdatedTime(new Date());
        return result;
    }

    /**
     * ??????2B????????????
     *
     * @param enter
     * @return
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult cancelDriver2BAccout(IdEnter enter) {
        PlaUser plaUser = userMapper.selectById(enter.getId());

        if (null == plaUser) {
            return new GeneralResult(enter.getRequestId());
        }
        userMapper.deleteById(enter.getId());

        //????????????  ?????????????????????????????????????????????????????????
        if (userMapper.selectCount(new LambdaQueryWrapper<PlaUser>().eq(PlaUser::getLoginName, plaUser.getLoginName()).eq(PlaUser::getDr, 0)) == 0) {
            QueryWrapper<PlaUserPassword> query = new QueryWrapper<>();
            query.eq(PlaUserPassword.COL_LOGIN_NAME, plaUser.getLoginName());
            query.eq(PlaUserPassword.COL_DR, Constant.DR_FALSE);
            PlaUserPassword userPassword = userPasswordMapper.selectOne(query);
            if (null == userPassword) {
                return new GeneralResult(enter.getRequestId());
            }
            userPasswordMapper.deleteById(userPassword.getId());
        }


        QueryWrapper<PlaUserPermission> wrapper = new QueryWrapper<>();
        wrapper.eq(PlaUserPermission.COL_USER_ID, plaUser.getId());
        wrapper.eq(PlaUserPermission.COL_DR, Constant.DR_FALSE);
        PlaUserPermission permission = userPermissionMapper.selectOne(wrapper);

        if (null == permission) {
            return new GeneralResult(enter.getRequestId());
        }
        userPermissionMapper.deleteById(permission.getId());

        return new GeneralResult(enter.getRequestId());
    }

    /**
     * ????????????????????????
     *
     * @param enter
     * @return
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult sendEmailActiv(IdEnter enter) {

        PlaUser user = userMapper.selectById(enter.getId());

        if (null == user) {
            throw new FoundationException(ExceptionCodeEnums.USER_NOT_EXIST.getCode(),
                    ExceptionCodeEnums.USER_NOT_EXIST.getMessage());
        }
        //????????????????????????????????????
        if (jedisCluster.exists(new StringBuffer().append("send::").append(user.getLoginName()).toString())) {
            return new BooleanResult(true);
        }

        BaseMailTaskEnter baseMailTaskEnter = new BaseMailTaskEnter();

        BeanUtils.copyProperties(enter, baseMailTaskEnter);

        baseMailTaskEnter.setMailAppId(AccountTypeUtils.getAppId(user.getUserType()));
        baseMailTaskEnter.setMailSystemId(AccountTypeUtils.getSystemId(user.getUserType()));
        if (user.getLoginName().contains("@")) {
            String str = user.getLoginName().substring(0, user.getLoginName().indexOf("@"));
            baseMailTaskEnter.setName(str);
        } else {
            baseMailTaskEnter.setName(user.getLoginName());
        }
        /*if (StringUtils.equalsAnyIgnoreCase(String.valueOf(user.getUserType()),
                String.valueOf(AccountTypeEnums.APP_EXPRESS.getAccountType()),
                String.valueOf(AccountTypeEnums.APP_EXPRESS.getAccountType()),
                String.valueOf(AccountTypeEnums.APP_RESTAURANT.getAccountType()))) {
            baseMailTaskEnter.setEvent(MailTemplateEventEnums.MOBILE_ACTIVATE.getEvent());
        } else {
            baseMailTaskEnter.setEvent(MailTemplateEventEnums.WEB_ACTIVATE.getEvent());
        }*/
        baseMailTaskEnter.setEvent(MailTemplateEventEnums.MOBILE_ACTIVATE.getEvent());
        baseMailTaskEnter.setToMail(user.getLoginName());
        baseMailTaskEnter.setToUserId(enter.getUserId());
        baseMailTaskEnter.setUserRequestId(enter.getRequestId());
        mailMultiTaskService.addActivateMobileUserTask(baseMailTaskEnter);

        //??????????????????????????????
        String key = new StringBuffer().append("send::").append(user.getLoginName()).toString();
        jedisCluster.set(key, DateUtil.getDate());
        jedisCluster.expire(key, new Long(RedisExpireEnum.MINUTES_3.getSeconds()).intValue());

        return new GeneralResult(enter.getRequestId());
    }

    /**
     * ??????????????????
     *
     * @param enter
     * @return
     */
    @Override
    public Map<String, Integer> customerAccountCountByStatus(QueryAccountCountStatusEnter enter) {
        // ????????? ???????????????????????????
        List<CountByStatusResult> countByStatusList = accountBaseServiceMapper.customerAccountCountByStatus(enter, customerTypeList());

        Map<String, Integer> map = new HashMap<>();
        for (CountByStatusResult item : countByStatusList) {
            map.put(item.getStatus(), item.getTotalCount());
        }
        for (UserStatusEnum item : UserStatusEnum.values()) {
            if (!map.containsKey(item.getValue())) {
                map.put(item.getValue(), 0);
            }
        }
        map.remove(UserStatusEnum.INACTIVATED.getValue());
        map.remove(UserStatusEnum.CANCEL.getValue());
        return map;
    }

    /**
     * ????????????????????????
     *
     * @param enter
     * @return
     */
    @Override
    public Integer customerAccountCount(QueryAccountListEnter enter) {
        return accountBaseServiceMapper.customerAccountCount(enter, customerTypeList());
    }

    /**
     * ??????????????????
     *
     * @param enter
     * @return
     */
    @Override
    public List<QueryAccountResult> customerAccountList(QueryAccountListEnter enter) {
        return accountBaseServiceMapper.customerAccountList(enter, customerTypeList());
    }

    /**
     * ????????????
     *
     * @param email
     * @return
     */
    @Override
    public QueryAccountResult customerAccountDeatil(String email) {
        return accountBaseServiceMapper.customerAccountDeatil(email, customerTypeList());
    }

    /**
     * ??????????????????????????????
     *
     * @param enter
     * @return
     */
    @Override
    public BooleanResult checkOpenAccount(CheckOpenAccountEnter enter) {

        PlaUser plaUser = plaUserMapper.selectOne(new LambdaQueryWrapper<PlaUser>().eq(PlaUser::getUserType, enter.getAccountType()).eq(PlaUser::getLoginName, enter.getEmail()));
        if (null == plaUser) {
            return BooleanResult.builder().success(Boolean.FALSE).build();
        }
        return BooleanResult.builder().success(plaUser.getActivationTime() == null ? Boolean.FALSE : Boolean.TRUE).build();
    }

    private Long saveUserSingle(DateTimeParmEnter<BaseCustomerResult> enter, Long tenantId) {
        Integer accountType =
                AccountTypeUtils.getAccountType(enter.getT().getCustomerType(), enter.getT().getIndustryType());

        QueryWrapper<PlaUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(PlaUser.COL_LOGIN_NAME, enter.getT().getEmail());
        queryWrapper.eq(PlaUser.COL_DR, Constant.DR_FALSE);
        queryWrapper.eq(PlaUser.COL_USER_TYPE, accountType);
        queryWrapper.last("LIMIT 1");
        PlaUser user = userMapper.selectOne(queryWrapper);

        if (null == user) {
            // ?????? user ??????
            user = new PlaUser();
            user.setId(idAppService.getId(SequenceName.PLA_USER));
            user.setDr(Constant.DR_FALSE);
            user.setTenantId(tenantId);
            user.setAppId(AccountTypeUtils.getAppId(accountType));
            user.setSystemId(AccountTypeUtils.getSystemId(accountType));
            user.setLoginName(enter.getT().getEmail());
            user.setUserType(accountType);
            user.setStatus(UserStatusEnum.NORMAL.getValue());
            user.setEffectiveTime(enter.getStartDateTime());
            user.setExpireTime(enter.getEndDateTime());
            user.setCreatedBy(enter.getUserId());
            user.setCreatedTime(new Date());
            user.setUpdatedBy(enter.getUserId());
            user.setUpdatedTime(new Date());

            plaUserMapper.insert(user);
        }

        // ????????????
        QueryWrapper<PlaUserPassword> plaUserPasswordQueryWrapper = new QueryWrapper<>();
        plaUserPasswordQueryWrapper.eq(PlaUserPassword.COL_LOGIN_NAME, enter.getT().getEmail());
        plaUserPasswordQueryWrapper.last("LIMIT 1");
        PlaUserPassword plaUserPassword = userPasswordMapper.selectOne(plaUserPasswordQueryWrapper);
        // ????????????????????????????????? ??????
        if (null == plaUserPassword) {
            PlaUserPassword savePassWord = new PlaUserPassword();
            savePassWord.setId(idAppService.getId(SequenceName.PLA_USER_PASSWORD));
            savePassWord.setLoginName(enter.getT().getEmail());
            savePassWord.setSalt(String.valueOf(RandomUtils.nextInt(10000, 99999)));
            savePassWord.setPassword(null);
            savePassWord.setCreatedBy(enter.getUserId());
            savePassWord.setCreatedTime(new Date());
            savePassWord.setUpdatedBy(enter.getUserId());
            savePassWord.setUpdatedTime(new Date());
            // 2.2???????????????????????????????????????????????????????????????
            userPasswordMapper.insert(savePassWord);
        }

        QueryWrapper<PlaUserPermission> queryuserPermission = new QueryWrapper<>();
        queryuserPermission.eq(PlaUserPermission.COL_USER_ID, user.getId());
        queryuserPermission.eq(PlaUserPermission.COL_SYSTEM_ID, AccountTypeUtils.getSystemId(accountType));
        queryuserPermission.eq(PlaUserPermission.COL_APP_ID, AccountTypeUtils.getAppId(accountType));
        queryuserPermission.last("LIMIT 1");
        PlaUserPermission userPermission = userPermissionMapper.selectOne(queryuserPermission);
        if (null == userPermission) {
            // ????????????
            userPermission = new PlaUserPermission();
            userPermission.setId(idAppService.getId(SequenceName.PLA_USER_PERMISSION));
            userPermission.setUserId(user.getId());
            userPermission.setSystemId(AccountTypeUtils.getSystemId(accountType));
            userPermission.setAppId(AccountTypeUtils.getAppId(accountType));
            userPermission.setStatus(UserStatusEnum.NORMAL.getValue());
            userPermission.setCreatedBy(enter.getUserId());
            userPermission.setCreatedTime(new Date());
            userPermission.setUpdatedBy(enter.getUserId());
            userPermission.setUpdatedTime(new Date());
            userPermissionMapper.insert(userPermission);
        }

        // ??????????????????
        SaveAccountNodeEnter saveAccountNodeEnter = new SaveAccountNodeEnter();
        BeanUtils.copyProperties(enter, saveAccountNodeEnter);
        saveAccountNodeEnter.setInputUserId(user.getId());
        saveAccountNodeEnter.setEvent(UserEventEnum.CREATE.getValue());
        userBaseService.saveAccountNode(saveAccountNodeEnter);
        return user.getId();
    }

    /**
     * ros????????????????????? ??????????????? saasweb???personal???
     *
     * @return
     */
    private List<Integer> customerTypeList() {
        List<Integer> accountType = new ArrayList<>();
        accountType.add(AccountTypeEnums.WEB_RESTAURANT.getAccountType());
        accountType.add(AccountTypeEnums.WEB_EXPRESS.getAccountType());
        accountType.add(AccountTypeEnums.APP_PERSONAL.getAccountType());
        return accountType;
    }

}
