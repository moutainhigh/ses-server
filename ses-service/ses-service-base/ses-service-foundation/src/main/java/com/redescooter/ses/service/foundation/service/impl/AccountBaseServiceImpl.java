package com.redescooter.ses.service.foundation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.SetPasswordEnter;
import com.redescooter.ses.api.foundation.exception.FoundationException;
import com.redescooter.ses.api.foundation.service.MailMultiTaskService;
import com.redescooter.ses.api.foundation.service.base.AccountBaseService;
import com.redescooter.ses.api.foundation.service.base.TenantBaseService;
import com.redescooter.ses.api.foundation.service.base.UserBaseService;
import com.redescooter.ses.api.foundation.vo.account.SaveDriverAccountDto;
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
import com.redescooter.ses.starter.rabbitmq.config.RabbitConfig;
import com.redescooter.ses.starter.redis.enums.RedisExpireEnum;
import com.redescooter.ses.tool.utils.DateUtil;
import com.redescooter.ses.tool.utils.accountType.AccountTypeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
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
 * @Date: 20/12/2019 2:33 下午
 * @ClassName: AccountBaseServiceImpl
 * @Function: TODO
 */
@Slf4j
@Service
public class AccountBaseServiceImpl implements AccountBaseService {

    @Autowired
    private PlaTenantMapper tenantMapper;
    @Autowired
    private PlaUserMapper userMapper;
    @Autowired
    private PlaUserPasswordService userPasswordService;
    @Autowired
    private TenantBaseService tenantBaseService;

    @Autowired
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

    @Reference
    private MailMultiTaskService mailMultiTaskService;

    @Reference
    private UserProfileService userProfileService;

    @Autowired
    private PlaUserNodeService plaUserNodeService;

    @Reference
    private CustomerService customerService;

    @Autowired
    private PlaTenantNodeService plaTenantNodeService;

    @Autowired
    private PlaTenantConfigService plaTenantConfigService;

    /**
     * 账号开通
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public BaseUserResult open(DateTimeParmEnter<BaseCustomerResult> enter) {
        Boolean chectMail = chectMail(enter.getT().getEmail());
        if (!chectMail) {
            throw new FoundationException(ExceptionCodeEnums.ACCOUNT_ALREADY_EXIST.getCode(),
                    ExceptionCodeEnums.ACCOUNT_ALREADY_EXIST.getMessage());
        }
        BaseUserResult result = new BaseUserResult();
        // 开通账户
        // 1、 创建租户
        Long tenantId = TenantDefaultValue.DEFAULT_MOBILEC_TENANTID;
        if (StringUtils.equals(enter.getT().getCustomerType(), CustomerTypeEnum.ENTERPRISE.getValue())) {
            tenantId = tenantBaseService.saveTenant(enter);
        }
        // 2、 创建账户
        Long userId = saveUserSingle(enter, tenantId);

        result.setId(userId);
        result.setTenantId(tenantId);
        // 3、 创建个人信息
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
/*            //hub 调用生产者
          rabbitTemplate.convertAndSend(ExchangeName.EXCHANGE_TOPICS_INFORM, CustomizeRoutingKey.CUSTOMER_SAVE_USER_PROFILE,saveUserProfileHubEnter);
          System.out.println("发送 open 保存用户个人资料2 c");*/
        } else {
    /*      rabbitTemplate.convertAndSend(ExchangeName.EXCHANGE_TOPICS_INFORM, CustomizeRoutingKey.CUSTOMER_SAVE_USER_PROFILE,saveUserProfileHubEnter);
          System.out.println("发送 open 保存用户个人资料2 B");*/
            userProfileService.saveUserProfile2B(saveUserProfileHubEnter);

        }
        // 创建邮件任务
        BaseMailTaskEnter baseMailTaskEnter = new BaseMailTaskEnter();
        baseMailTaskEnter.setName(enter.getT().getCustomerFullName());
        baseMailTaskEnter.setToMail(enter.getT().getEmail());
        baseMailTaskEnter.setToUserId(userId);
        baseMailTaskEnter.setUserRequestId(enter.getRequestId());
        baseMailTaskEnter.setRequestId(enter.getRequestId());

        if (StringUtils.equals(CustomerTypeEnum.PERSONAL.getValue(), enter.getT().getCustomerType())) {
            baseMailTaskEnter.setEvent(MailTemplateEventEnums.MOBILE_ACTIVATE.getEvent());
            baseMailTaskEnter.setMailAppId(AppIDEnums.SAAS_APP.getValue());
            baseMailTaskEnter.setMailSystemId(AppIDEnums.SAAS_APP.getSystemId());
        }
        if (StringUtils.equals(CustomerTypeEnum.ENTERPRISE.getValue(), enter.getT().getCustomerType())) {
            baseMailTaskEnter.setEvent(MailTemplateEventEnums.WEB_ACTIVATE.getEvent());
            baseMailTaskEnter.setMailAppId(AppIDEnums.SAAS_WEB.getValue());
            baseMailTaskEnter.setMailSystemId(AppIDEnums.SAAS_WEB.getSystemId());
        }
        mailMultiTaskService.addActivateMobileUserTask(baseMailTaskEnter);
        return result;
    }

    @Override
    public Boolean chectMail(String mail) {

        QueryWrapper<PlaTenant> wrapper = new QueryWrapper<>();
        wrapper.eq(PlaTenant.COL_EMAIL, mail);
        wrapper.eq(PlaTenant.COL_DR, 0);

        return tenantMapper.selectCount(wrapper) == 0 ? Boolean.TRUE : Boolean.FALSE;
    }

    /**
     * 用户昵称验证
     *
     * @param nickname
     * @return
     */
    @Override
    public Boolean checkNaickname(String nickname) {

        QueryWrapper<PlaUser> wrapper = new QueryWrapper<>();
        wrapper.eq(PlaUser.COL_LOGIN_NAME, nickname);
        wrapper.eq(PlaUser.COL_DR, 0);

        return userMapper.selectCount(wrapper) == 0 ? Boolean.TRUE : Boolean.FALSE;
    }

    /**
     * 查询已创建 的账户
     *
     * @param enter
     * @return
     */
    @Override
    public int countTenantAccount(QueryAccountListEnter enter) {
        return accountBaseServiceMapper.queryAccountListCount(enter);
    }

    /**
     * 查询 已创建的 账户记录
     *
     * @param enter
     * @return
     */
    @Override
    public List<QueryAccountResult> tenantAccountRecords(QueryAccountListEnter enter) {
        return accountBaseServiceMapper.queryAccountList(enter);
    }

    /**
     * 账户冻结
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public GeneralResult freeze(DateTimeParmEnter<BaseCustomerResult> enter) {
        int accountType =
                AccountTypeUtils.getAccountType(enter.getT().getCustomerType(), enter.getT().getIndustryType());
        String appId = AccountTypeUtils.getAppId(accountType);

        // 租户
        Long customerTenantId = 0L;
        if (accountType == AccountTypeEnums.WEB_EXPRESS.getAccountType() || accountType == AccountTypeEnums.WEB_RESTAURANT.getAccountType()) {
            QueryWrapper<PlaTenant> plaTenantQueryWrapper = new QueryWrapper<>();
            plaTenantQueryWrapper.eq(PlaTenant.COL_DR, 0);
            plaTenantQueryWrapper.eq(PlaTenant.COL_EMAIL, enter.getT().getEmail());
            PlaTenant plaTenant = plaTenantMapper.selectOne(plaTenantQueryWrapper);
            if (plaTenant == null) {
                throw new FoundationException(ExceptionCodeEnums.TENANT_NOT_EXIST.getCode(), ExceptionCodeEnums.TENANT_NOT_EXIST.getMessage());
            }
            if (!StringUtils.equals(TenantStatusEnum.INOPERATION.getValue(), plaTenant.getStatus())) {
                throw new FoundationException(ExceptionCodeEnums.STATUS_IS_REASONABLE.getCode(), ExceptionCodeEnums.STATUS_IS_REASONABLE.getMessage());
            }
            plaTenant.setStatus(TenantStatusEnum.FROZEN.getValue());
            plaTenant.setUpdatedBy(enter.getUserId());
            plaTenant.setUpdatedTime(new Date());
            plaTenantMapper.updateById(plaTenant);
            // 租户节点
            tenantBaseService.saveTenantNode(enter, TenanNodeEventEnum.FROZEN.getValue());

            customerTenantId = plaTenant.getId();
        }

        //user 及子账户
        List<PlaUser> plaUserList = new ArrayList<>();
        if (accountType == AccountTypeEnums.WEB_EXPRESS.getAccountType() || accountType == AccountTypeEnums.WEB_RESTAURANT.getAccountType()) {
            QueryWrapper<PlaUser> plaUserQueryWrapper = new QueryWrapper<>();
            plaUserQueryWrapper.eq(PlaUser.COL_DR, 0);
            plaUserQueryWrapper.eq(PlaUser.COL_TENANT_ID, customerTenantId);
            plaUserList = plaUserMapper.selectList(plaUserQueryWrapper);
        } else {
            QueryWrapper<PlaUser> plaUserQueryWrapper = new QueryWrapper<>();
            plaUserQueryWrapper.eq(PlaUser.COL_DR, 0);
            plaUserQueryWrapper.eq(PlaUser.COL_LOGIN_NAME, enter.getT().getEmail());
            plaUserQueryWrapper.in(PlaUser.COL_USER_TYPE, customerTypeList());
            PlaUser plaUser = plaUserMapper.selectOne(plaUserQueryWrapper);
            plaUserList.add(plaUser);
        }

        Long tennatUserId = null;
        Set<Long> userIdList = new HashSet<>();
        if (CollectionUtils.isNotEmpty(plaUserList)) {
            // 放 账户节点列表
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

                //生成账户节点
                SaveAccountNodeEnter saveAccountNodeEnter = new SaveAccountNodeEnter();
                BeanUtils.copyProperties(enter, saveAccountNodeEnter);
                saveAccountNodeEnter.setInputUserId(item.getId());
                saveAccountNodeEnter.setEvent(UserEventEnum.FROZEN.getValue());
                userAccountNodeList.add(saveAccountNodeEnter);

                //批量清楚token 若token存在 清空Token
                if (StringUtils.isNotBlank(item.getLastLoginToken())) {
                    jedisCluster.del(item.getLastLoginToken());
                }
                // 添加emailList 用于过滤权限
                userIdList.add(item.getId());
            }
            if (!tenantAccount) {
                throw new FoundationException(ExceptionCodeEnums.USER_NOT_EXIST.getCode(), ExceptionCodeEnums.USER_NOT_EXIST.getMessage());
            }
            // 账户信息更新
            plaUserMapper.updateBatch(plaUserList);
            // 账户节点保存
            userBaseService.saveAccountNodeList(userAccountNodeList);
        }

        // 权限
        QueryWrapper<PlaUserPermission> plaUserPermissionQueryWrapper = new QueryWrapper<>();
        plaUserPermissionQueryWrapper.in(PlaUserPermission.COL_USER_ID, new ArrayList<>(userIdList));
        List<PlaUserPermission> plaUserPermissionList = userPermissionMapper.selectList(plaUserPermissionQueryWrapper);
        // 非空 进行权限验证和更新
        if (CollectionUtils.isNotEmpty(plaUserPermissionList)) {
            Boolean tenantAccountPermission = Boolean.FALSE;
            for (PlaUserPermission item : plaUserPermissionList) {
                if (tennatUserId != null && tennatUserId != 0 && item.getUserId().equals(tennatUserId)) {
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

            // 权限更新
            userPermissionMapper.updateBatch(plaUserPermissionList);
        }
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 账户解冻
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public GeneralResult unFreezeAccount(DateTimeParmEnter<BaseCustomerResult> enter) {
        int accountType =
                AccountTypeUtils.getAccountType(enter.getT().getCustomerType(), enter.getT().getIndustryType());

        // 租户
        Long customerTenantId = 0L;
        if (accountType == AccountTypeEnums.WEB_EXPRESS.getAccountType() || accountType == AccountTypeEnums.WEB_RESTAURANT.getAccountType()) {
            QueryWrapper<PlaTenant> plaTenantQueryWrapper = new QueryWrapper<>();
            plaTenantQueryWrapper.eq(PlaTenant.COL_DR, 0);
            plaTenantQueryWrapper.eq(PlaTenant.COL_EMAIL, enter.getT().getEmail());
            PlaTenant plaTenant = plaTenantMapper.selectOne(plaTenantQueryWrapper);
            if (plaTenant == null) {
                throw new FoundationException(ExceptionCodeEnums.TENANT_NOT_EXIST.getCode(), ExceptionCodeEnums.TENANT_NOT_EXIST.getMessage());
            }
            if (!StringUtils.equals(TenantStatusEnum.FROZEN.getValue(), plaTenant.getStatus())) {
                throw new FoundationException(ExceptionCodeEnums.STATUS_IS_REASONABLE.getCode(), ExceptionCodeEnums.STATUS_IS_REASONABLE.getMessage());
            }
            plaTenant.setStatus(TenantStatusEnum.INOPERATION.getValue());
            plaTenant.setUpdatedBy(enter.getUserId());
            plaTenant.setUpdatedTime(new Date());
            plaTenantMapper.updateById(plaTenant);
            // 租户节点
            tenantBaseService.saveTenantNode(enter, TenanNodeEventEnum.UNFREEZE.getValue());

            customerTenantId = plaTenant.getId();
        }

        //user 及子账户
        List<PlaUser> plaUserList = new ArrayList<>();
        if (accountType == AccountTypeEnums.WEB_EXPRESS.getAccountType() || accountType == AccountTypeEnums.WEB_RESTAURANT.getAccountType()) {
            QueryWrapper<PlaUser> plaUserQueryWrapper = new QueryWrapper<>();
            plaUserQueryWrapper.eq(PlaUser.COL_DR, 0);
            plaUserQueryWrapper.eq(PlaUser.COL_TENANT_ID, customerTenantId);
            plaUserList = plaUserMapper.selectList(plaUserQueryWrapper);
        } else {
            QueryWrapper<PlaUser> plaUserQueryWrapper = new QueryWrapper<>();
            plaUserQueryWrapper.eq(PlaUser.COL_DR, 0);
            plaUserQueryWrapper.eq(PlaUser.COL_LOGIN_NAME, enter.getT().getEmail());
            plaUserQueryWrapper.in(PlaUser.COL_USER_TYPE, customerTypeList());
            PlaUser plaUser = plaUserMapper.selectOne(plaUserQueryWrapper);
            plaUserList.add(plaUser);
        }

        Long tennatUserId = null;
        Set<Long> userIdList = new HashSet<>();
        if (CollectionUtils.isNotEmpty(plaUserList)) {
            // 放 账户节点列表
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

                //生成账户节点
                SaveAccountNodeEnter saveAccountNodeEnter = new SaveAccountNodeEnter();
                BeanUtils.copyProperties(enter, saveAccountNodeEnter);
                saveAccountNodeEnter.setInputUserId(item.getId());
                saveAccountNodeEnter.setEvent(UserEventEnum.UNFREEZE.getValue());
                userAccountNodeList.add(saveAccountNodeEnter);

                // 添加emailList 用于过滤权限
                userIdList.add(item.getId());
            }
            if (!tenantAccount) {
                throw new FoundationException(ExceptionCodeEnums.USER_NOT_EXIST.getCode(), ExceptionCodeEnums.USER_NOT_EXIST.getMessage());
            }
            // 账户信息更新
            plaUserMapper.updateBatch(plaUserList);
            // 账户节点保存
            userBaseService.saveAccountNodeList(userAccountNodeList);
        }

        // 权限
        QueryWrapper<PlaUserPermission> plaUserPermissionQueryWrapper = new QueryWrapper<>();
        plaUserPermissionQueryWrapper.in(PlaUserPermission.COL_USER_ID, new ArrayList<>(userIdList));
        List<PlaUserPermission> plaUserPermissionList = userPermissionMapper.selectList(plaUserPermissionQueryWrapper);
        // 非空 进行权限验证和更新
        if (CollectionUtils.isNotEmpty(plaUserPermissionList)) {
            Boolean tenantAccountPermission = Boolean.FALSE;
            for (PlaUserPermission item : plaUserPermissionList) {
                if (tennatUserId != null && tennatUserId != 0 && item.getUserId().equals(tennatUserId)) {
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
            // 权限更新
            userPermissionMapper.updateBatch(plaUserPermissionList);
        }
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 账户续期
     * // 1、 续期开始时间 必须在开通时间之后
     * // 2、续期结束时间 必须在到期时间之后
     * // 3、 续期结束时间 必须在开始时间之后
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public GeneralResult renewAccont(DateTimeParmEnter<BaseCustomerResult> enter) {
        if (DateUtil.timeComolete(enter.getStartDateTime(), enter.getEndDateTime()) < 0) {
            throw new FoundationException(ExceptionCodeEnums.RENEW_END_DATETIME_IS_NOT_AVAILABLE.getCode(),
                    ExceptionCodeEnums.RENEW_END_DATETIME_IS_NOT_AVAILABLE.getMessage());
        }

        int accountType =
                AccountTypeUtils.getAccountType(enter.getT().getCustomerType(), enter.getT().getIndustryType());

        // 租户
        PlaTenant plaTenant = null;
        if (accountType == AccountTypeEnums.WEB_EXPRESS.getAccountType() || accountType == AccountTypeEnums.WEB_RESTAURANT.getAccountType()) {
            plaTenant = plaTenantMapper.selectById(enter.getT().getTenantId());
            if (plaTenant == null) {
                throw new FoundationException(ExceptionCodeEnums.TENANT_NOT_EXIST.getCode(),
                        ExceptionCodeEnums.TENANT_NOT_EXIST.getMessage());
            }
            // 冻结不可续费
            if (StringUtils.equals(TenantStatusEnum.FROZEN.getValue(), plaTenant.getStatus())) {
                throw new FoundationException(ExceptionCodeEnums.STATUS_IS_REASONABLE.getCode(),
                        ExceptionCodeEnums.STATUS_IS_REASONABLE.getMessage());
            }
            if (DateUtil.timeComolete(plaTenant.getExpireTime(), enter.getEndDateTime()) < 0) {
                throw new FoundationException(ExceptionCodeEnums.RENEW_END_DATETIME_IS_NOT_AVAILABLE.getCode(),
                        ExceptionCodeEnums.RENEW_END_DATETIME_IS_NOT_AVAILABLE.getMessage());
            }

            if (DateUtil.timeComolete(plaTenant.getEffectiveTime(), enter.getStartDateTime()) <= 0) {
                throw new FoundationException(ExceptionCodeEnums.RENEW_START_DATETIME_IS_NOT_AVAILABLE.getCode(),
                        ExceptionCodeEnums.RENEW_END_DATETIME_IS_NOT_AVAILABLE.getMessage());
            }
            plaTenant.setExpireTime(enter.getEndDateTime());
            plaTenant.setUpdatedTime(new Date());
            plaTenant.setUpdatedBy(enter.getUserId());
            // 租户续期
            plaTenantMapper.updateById(plaTenant);
            // 租户节点
            tenantBaseService.saveTenantNode(enter, TenanNodeEventEnum.RENEW.getValue());
        }
        // 账户信息
        QueryWrapper<PlaUser> plaUserQueryWrapper = new QueryWrapper<>();
        plaUserQueryWrapper.eq(PlaUser.COL_DR, 0);
        plaUserQueryWrapper.eq(PlaUser.COL_LOGIN_NAME, enter.getT().getEmail());
        plaUserQueryWrapper.in(PlaUser.COL_USER_TYPE, customerTypeList());
        PlaUser plaUser = plaUserMapper.selectOne(plaUserQueryWrapper);
        if (plaUser == null) {
            throw new FoundationException(ExceptionCodeEnums.USER_NOT_EXIST.getCode(),
                    ExceptionCodeEnums.USER_NOT_EXIST.getMessage());
        }
        // 冻结不可续费
        if (StringUtils.equals(UserStatusEnum.LOCK.getValue(), plaUser.getStatus())) {
            throw new FoundationException(ExceptionCodeEnums.STATUS_IS_REASONABLE.getCode(),
                    ExceptionCodeEnums.STATUS_IS_REASONABLE.getMessage());
        }
        if (DateUtil.timeComolete(plaUser.getExpireTime(), enter.getEndDateTime()) < 0) {
            throw new FoundationException(ExceptionCodeEnums.RENEW_END_DATETIME_IS_NOT_AVAILABLE.getCode(),
                    ExceptionCodeEnums.RENEW_END_DATETIME_IS_NOT_AVAILABLE.getMessage());
        }

        if (DateUtil.timeComolete(plaUser.getEffectiveTime(), enter.getStartDateTime()) <= 0) {
            throw new FoundationException(ExceptionCodeEnums.RENEW_START_DATETIME_IS_NOT_AVAILABLE.getCode(),
                    ExceptionCodeEnums.RENEW_END_DATETIME_IS_NOT_AVAILABLE.getMessage());
        }
        plaUser.setExpireTime(enter.getEndDateTime());
        plaUser.setUpdatedTime(new Date());
        plaUser.setUpdatedBy(enter.getUserId());
        // 租户续期
        plaUserMapper.updateById(plaUser);

        // 账户节点
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
    @Transactional
    @Override
    public GeneralResult setPassword(SetPasswordEnter<BaseCustomerResult> enter) {
        QueryWrapper<PlaUserPassword> plaUserPasswordQueryWrapper = new QueryWrapper<>();
        plaUserPasswordQueryWrapper.eq(PlaUserPassword.COL_LOGIN_NAME, enter.getT().getEmail());
        PlaUserPassword plaUserPassword = userPasswordMapper.selectOne(plaUserPasswordQueryWrapper);
        if (plaUserPassword == null) {
            throw new FoundationException(ExceptionCodeEnums.ACCOUNT_IS_NOT_EXIST.getCode(),
                    ExceptionCodeEnums.ACCOUNT_IS_NOT_EXIST.getMessage());
        }

        plaUserPassword.setPassword(DigestUtils.md5Hex(enter.getConfirmPassword() + plaUserPassword.getSalt()));
        plaUserPassword.setUpdatedBy(enter.getUserId());
        plaUserPassword.setUpdatedTime(new Date());
        userPasswordMapper.updateById(plaUserPassword);
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 根据租户ID删除所有用户
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public GeneralResult deleteUser(DeleteUserEnter enter) {
        /**
         * 1.租户删除 2.用户删除 3.用户信息删除
         */
        List<Long> ids = new ArrayList<>();
        Long tenantId = 0L;
        QueryWrapper<PlaTenant> plaTenantQueryWrapper = new QueryWrapper<>();
        plaTenantQueryWrapper.eq(PlaTenant.COL_EMAIL, enter.getEmail());
        plaTenantQueryWrapper.eq(PlaTenant.COL_DR, 0);
        PlaTenant tenant = plaTenantMapper.selectOne(plaTenantQueryWrapper);
        if (tenant != null) {
            tenantId = tenant.getId();
            plaTenantMapper.deleteById(tenant);
        }
        if (tenantId.equals(0)) {
            QueryWrapper<PlaUser> wrapper = new QueryWrapper<>();
            wrapper.eq(PlaUser.COL_TENANT_ID, tenantId);
            wrapper.eq(PlaUser.COL_DR, 0);
            List<PlaUser> userList = plaUserMapper.selectList(wrapper);
            if (CollectionUtils.isNotEmpty(userList)) {
                ids = userList.stream().map(user -> user.getId()).collect(Collectors.toList());
            }
        } else {
            QueryWrapper<PlaUser> wrapper = new QueryWrapper<>();
            wrapper.eq(PlaUser.COL_LOGIN_NAME, enter.getEmail());
            wrapper.eq(PlaUser.COL_DR, 0);
            wrapper.in(PlaUser.COL_USER_TYPE, customerTypeList());
            PlaUser plaUser = plaUserMapper.selectOne(wrapper);
            if (plaUser == null) {
                return new GeneralResult(enter.getRequestId());
            }
            ids.add(plaUser.getId());
        }

        if (!tenantId.equals(0L)) {
            //删除公司--2B信息
            tenantMapper.deleteById(tenant.getId());
            //个人信息
            userProfileService.deleteUserProfile2B(ids);
            //删除租户节点
            QueryWrapper<PlaTenantNode> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq(PlaTenantNode.COL_TENANT_ID, tenantId);
            queryWrapper.eq(PlaTenantNode.COL_DR, 0);
            plaTenantNodeService.remove(queryWrapper);

            // 删除租户配置
            QueryWrapper<PlaTenantConfig> plaTenantConfigQueryWrapper = new QueryWrapper<>();
            plaTenantConfigQueryWrapper.eq(PlaTenantConfig.COL_TENANT_ID, tenantId);
            plaTenantConfigQueryWrapper.eq(PlaTenantConfig.COL_DR, 0);
            plaTenantConfigService.remove(plaTenantConfigQueryWrapper);
        } else {
            // 删除个人--2C信息 TODO
            userProfileService.deleteUserProfile2C(ids);
        }
        //删除账户信息
        plaUserMapper.deleteBatchIds(ids);

        //删除账户节点信息
        QueryWrapper<PlaUserNode> plaUserNodeQueryWrapper = new QueryWrapper<>();
        plaUserNodeQueryWrapper.eq(PlaUserNode.COL_DR, 0);
        plaUserNodeQueryWrapper.in(PlaUserNode.COL_USER_ID, ids);
        plaUserNodeService.remove(plaUserNodeQueryWrapper);


        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 开通2B司机账户
     *
     * @param dto
     * @return
     */
    @Transactional
    @Override
    public BaseUserResult openDriver2BAccout(SaveDriverAccountDto dto) {

        PlaTenant tenant = tenantMapper.selectById(dto.getTenantId());

        //账号登录类型
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
        queryWrapper.eq(PlaUser.COL_DR, 0);

        List<PlaUser> userList = userMapper.selectList(queryWrapper);
        int count = userList.size();
        if (driverloginType == 1) {
            if (userList.size() > 0) {
                for (PlaUser user : userList) {
                    if (user.getUserType() == accountType) {
                        throw new FoundationException(ExceptionCodeEnums.ACCOUNT_ALREADY_EXIST.getCode(),
                                ExceptionCodeEnums.ACCOUNT_ALREADY_EXIST.getMessage());
                    }
                }
            }
        } else {
            if (count > 0) {
                throw new FoundationException(ExceptionCodeEnums.ACCOUNT_ALREADY_EXIST.getCode(),
                        ExceptionCodeEnums.ACCOUNT_ALREADY_EXIST.getMessage());
            }
        }
        //①、创建user信息
        PlaUser user = new PlaUser();
        user.setId(idAppService.getId(SequenceName.PLA_USER));
        user.setDr(0);
        user.setTenantId(tenant.getId());
        user.setAppId(AccountTypeUtils.getAppId(accountType));
        user.setSystemId(AccountTypeUtils.getSystemId(accountType));
        user.setLoginName(driverloginType == 1 ? dto.getEmail() : dto.getNickName());
        user.setLoginType(driverloginType);
        user.setUserType(accountType);
        if (count > 0) {
            user.setStatus(UserStatusEnum.NORMAL.getValue());
            //标识账号是否激活
            user.setDef1(MaggessConstant.ACCOUNT_ACTIVAT_AFTER);
        } else {
            user.setStatus(driverloginType == 1 ? UserStatusEnum.INACTIVATED.getValue() : UserStatusEnum.NORMAL.getValue());
            //标识账号是否激活
            user.setDef1(driverloginType == 1 ? MaggessConstant.ACCOUNT_ACTIVAT_BEFORE : MaggessConstant.ACCOUNT_ACTIVAT_AFTER);
        }

        user.setCreatedBy(dto.getUserId());
        user.setCreatedTime(new Date());
        user.setUpdatedBy(dto.getUserId());
        user.setUpdatedTime(new Date());
        plaUserMapper.insert(user);

        //②、创建密码记录
        QueryWrapper<PlaUserPassword> queryPassWord = new QueryWrapper<>();
        queryPassWord.eq(PlaUserPassword.COL_LOGIN_NAME, driverloginType == 1 ? dto.getEmail() : dto.getNickName());

        queryPassWord.eq(PlaUserPassword.COL_DR, 0);
        PlaUserPassword passwordServiceOne = userPasswordService.getOne(queryPassWord);

        if (passwordServiceOne == null) {
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
            //②.①、用户激活时，有客户输入密码进行设置密码流程
            userPasswordMapper.insert(savePassWord);
        }

        //③、开通权限
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

        //④、组织结果返回
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
     * 关闭2B司机账号
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public GeneralResult cancelDriver2BAccout(IdEnter enter) {
        PlaUser plaUser = userMapper.selectById(enter.getId());

        if (plaUser == null) {
            return new GeneralResult(enter.getRequestId());
        }
        userMapper.deleteById(enter.getId());

        QueryWrapper<PlaUserPassword> query = new QueryWrapper<>();
        query.eq(PlaUserPassword.COL_LOGIN_NAME, plaUser.getLoginName());
        query.eq(PlaUserPassword.COL_DR, 0);
        PlaUserPassword userPassword = userPasswordMapper.selectOne(query);
        if (userPassword == null) {
            return new GeneralResult(enter.getRequestId());
        }
        userPasswordMapper.deleteById(userPassword.getId());

        QueryWrapper<PlaUserPermission> wrapper = new QueryWrapper<>();
        wrapper.eq(PlaUserPermission.COL_USER_ID, plaUser.getId());
        wrapper.eq(PlaUserPermission.COL_DR, 0);
        PlaUserPermission permission = userPermissionMapper.selectOne(wrapper);

        if (permission == null) {
            return new GeneralResult(enter.getRequestId());
        }
        userPermissionMapper.deleteById(permission.getId());

        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 再次发生激活邮件
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public GeneralResult sendEmailActiv(IdEnter enter) {

        PlaUser user = userMapper.selectById(enter.getId());

        if (user == null) {
            throw new FoundationException(ExceptionCodeEnums.USER_NOT_EXIST.getCode(),
                    ExceptionCodeEnums.USER_NOT_EXIST.getMessage());
        }
        //验证是否可以再次发生邮件
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
        if (StringUtils.equalsAnyIgnoreCase(String.valueOf(user.getUserType()),
                String.valueOf(AccountTypeEnums.APP_EXPRESS.getAccountType()),
                String.valueOf(AccountTypeEnums.APP_EXPRESS.getAccountType()),
                String.valueOf(AccountTypeEnums.APP_RESTAURANT.getAccountType()))) {
            baseMailTaskEnter.setEvent(MailTemplateEventEnums.MOBILE_ACTIVATE.getEvent());
        } else {
            baseMailTaskEnter.setEvent(MailTemplateEventEnums.WEB_ACTIVATE.getEvent());
        }
        baseMailTaskEnter.setToMail(user.getLoginName());
        baseMailTaskEnter.setToUserId(enter.getUserId());
        baseMailTaskEnter.setUserRequestId(enter.getRequestId());
        mailMultiTaskService.addActivateMobileUserTask(baseMailTaskEnter);

        //设置邮箱发送有效时间
        String key = new StringBuffer().append("send::").append(user.getLoginName()).toString();
        jedisCluster.set(key, DateUtil.getDate());
        jedisCluster.expire(key, new Long(RedisExpireEnum.MINUTES_3.getSeconds()).intValue());

        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 客户账户状态
     *
     * @param enter
     * @return
     */
    @Override
    public Map<String, Integer> customerAccountCountByStatus(GeneralEnter enter) {
        // 只统计 个人端、企业端账户
        List<CountByStatusResult> countByStatusList = accountBaseServiceMapper.customerAccountCountByStatus(customerTypeList());

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
     * 客户账户列表统计
     *
     * @param enter
     * @return
     */
    @Override
    public Integer customerAccountCount(QueryAccountListEnter enter) {
        return accountBaseServiceMapper.customerAccountCount(enter, customerTypeList());
    }

    /**
     * 客户账户列表
     *
     * @param enter
     * @return
     */
    @Override
    public List<QueryAccountResult> customerAccountList(QueryAccountListEnter enter) {
        return accountBaseServiceMapper.customerAccountList(enter, customerTypeList());
    }

    /**
     * 账户详情
     *
     * @param email
     * @return
     */
    @Override
    public QueryAccountResult customerAccountDeatil(String email) {
        return accountBaseServiceMapper.customerAccountDeatil(email, customerTypeList());
    }

    private Long saveUserSingle(DateTimeParmEnter<BaseCustomerResult> enter, Long tenantId) {
        Integer accountType =
                AccountTypeUtils.getAccountType(enter.getT().getCustomerType(), enter.getT().getIndustryType());

        QueryWrapper<PlaUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(PlaUser.COL_LOGIN_NAME, enter.getT().getEmail());
        queryWrapper.eq(PlaUser.COL_DR, 0);
        queryWrapper.eq(PlaUser.COL_USER_TYPE, accountType);
        queryWrapper.last("LIMIT 1");
        PlaUser user = userMapper.selectOne(queryWrapper);

        if (user == null) {
            // 保存 user 信息
            user = new PlaUser();
            user.setId(idAppService.getId(SequenceName.PLA_USER));
            user.setDr(0);
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

        // 保存密码
        QueryWrapper<PlaUserPassword> plaUserPasswordQueryWrapper = new QueryWrapper<>();
        plaUserPasswordQueryWrapper.eq(PlaUserPassword.COL_LOGIN_NAME, enter.getT().getEmail());
        plaUserPasswordQueryWrapper.last("LIMIT 1");
        PlaUserPassword plaUserPassword = userPasswordMapper.selectOne(plaUserPasswordQueryWrapper);
        // 密码不存在创建，已存在 跳过
        if (plaUserPassword == null) {
            PlaUserPassword savePassWord = new PlaUserPassword();
            savePassWord.setId(idAppService.getId(SequenceName.PLA_USER_PASSWORD));
            savePassWord.setLoginName(enter.getT().getEmail());
            savePassWord.setSalt(String.valueOf(RandomUtils.nextInt(10000, 99999)));
            savePassWord.setPassword(null);
            savePassWord.setCreatedBy(enter.getUserId());
            savePassWord.setCreatedTime(new Date());
            savePassWord.setUpdatedBy(enter.getUserId());
            savePassWord.setUpdatedTime(new Date());
            // 2.2用户激活时，有客户输入密码进行设置密码流程
            userPasswordMapper.insert(savePassWord);
        }

        QueryWrapper<PlaUserPermission> queryuserPermission = new QueryWrapper<>();
        queryuserPermission.eq(PlaUserPermission.COL_USER_ID, user.getId());
        queryuserPermission.eq(PlaUserPermission.COL_SYSTEM_ID, AccountTypeUtils.getSystemId(accountType));
        queryuserPermission.eq(PlaUserPermission.COL_APP_ID, AccountTypeUtils.getAppId(accountType));
        queryuserPermission.last("LIMIT 1");
        PlaUserPermission userPermission = userPermissionMapper.selectOne(queryuserPermission);
        if (userPermission == null) {
            // 开通权限
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

        // 保存账户节点
        SaveAccountNodeEnter saveAccountNodeEnter = new SaveAccountNodeEnter();
        BeanUtils.copyProperties(enter, saveAccountNodeEnter);
        saveAccountNodeEnter.setInputUserId(user.getId());
        saveAccountNodeEnter.setEvent(UserEventEnum.CREATE.getValue());
        userBaseService.saveAccountNode(saveAccountNodeEnter);
        return user.getId();
    }

    /**
     * ros客户的账户信息 限制范围在 saasweb、personal内
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
