package com.redescooter.ses.service.foundation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.enums.account.UserStatusEnum;
import com.redescooter.ses.api.common.enums.base.AppIDEnums;
import com.redescooter.ses.api.common.enums.proxy.mail.MailTemplateEventEnums;
import com.redescooter.ses.api.common.enums.ros.customer.CustomerTypeEnum;
import com.redescooter.ses.api.common.enums.tenant.TenanNodeEventEnum;
import com.redescooter.ses.api.common.enums.tenant.TenantStatusEnum;
import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.api.foundation.exception.FoundationException;
import com.redescooter.ses.api.foundation.service.MailMultiTaskService;
import com.redescooter.ses.api.foundation.service.base.AccountBaseService;
import com.redescooter.ses.api.foundation.service.base.TenantBaseService;
import com.redescooter.ses.api.foundation.vo.tenant.QueryAccountListEnter;
import com.redescooter.ses.api.foundation.vo.tenant.QueryAccountListResult;
import com.redescooter.ses.api.hub.common.UserProfileService;
import com.redescooter.ses.api.hub.vo.SaveUserProfileHubEnter;
import com.redescooter.ses.service.foundation.constant.SequenceName;
import com.redescooter.ses.service.foundation.dao.AccountBaseServiceMapper;
import com.redescooter.ses.service.foundation.dao.base.PlaTenantMapper;
import com.redescooter.ses.service.foundation.dao.base.PlaUserMapper;
import com.redescooter.ses.service.foundation.dao.base.PlaUserPasswordMapper;
import com.redescooter.ses.service.foundation.dao.base.PlaUserPermissionMapper;
import com.redescooter.ses.service.foundation.dm.base.PlaTenant;
import com.redescooter.ses.service.foundation.dm.base.PlaUser;
import com.redescooter.ses.service.foundation.dm.base.PlaUserPassword;
import com.redescooter.ses.service.foundation.dm.base.PlaUserPermission;
import com.redescooter.ses.service.foundation.exception.ExceptionCodeEnums;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.utils.DateUtil;
import com.redescooter.ses.tool.utils.bussinessutils.AccountTypeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisCluster;

import java.util.*;
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
    private PlaUserPasswordMapper passwordMapper;

    @Autowired
    private TenantBaseService tenantBaseService;

    @Autowired
    private IdAppService idAppService;

    @Autowired
    private PlaUserMapper plaUserMapper;

    @Autowired
    private PlaUserPasswordMapper plaUserPasswordMapper;

    @Autowired
    private PlaUserPermissionMapper plaUserPermissionMapper;

    @Autowired
    private PlaTenantMapper plaTenantMapper;

    @Autowired
    private AccountBaseServiceMapper accountBaseServiceMapper;

    @Autowired
    private JedisCluster jedisCluster;

    @Reference
    private MailMultiTaskService mailMultiTaskService;

    @Reference
    private UserProfileService userProfileService;

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
        Long tenantId = tenantBaseService.saveTenant(enter);
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
                        .telNumber1(enter.getT().getTelephone()).email1(enter.getT().getEmail()).build();
        saveUserProfileHubEnter.setUserId(userId);
        saveUserProfileHubEnter.setTenantId(tenantId);

        if (StringUtils.equals(enter.getT().getCustomerType(), CustomerTypeEnum.PERSONAL.getValue())) {
            userProfileService.saveUserProfile2C(saveUserProfileHubEnter);
        } else {
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
            baseMailTaskEnter.setMailAppId(AppIDEnums.SAAS_APP.getAppId());
            baseMailTaskEnter.setMailSystemId(AppIDEnums.SAAS_APP.getSystemId());
        }
        if (StringUtils.equals(CustomerTypeEnum.ENTERPRISE.getValue(), enter.getT().getCustomerType())) {
            baseMailTaskEnter.setEvent(MailTemplateEventEnums.WEB_ACTIVATE.getEvent());
            baseMailTaskEnter.setMailAppId(AppIDEnums.SAAS_WEB.getAppId());
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
    public List<QueryAccountListResult> tenantAccountRecords(QueryAccountListEnter enter) {
        return accountBaseServiceMapper.queryAccountList(enter);
    }

    /**
     * 查询租户状态
     *
     * @return
     */
    @Override
    public Map<String, Integer> accountCountStatus() {
        List<CountByStatusResult> countByStatusResult = accountBaseServiceMapper.accountCountStatus();
        Map<String, Integer> map = new HashMap<>();
        for (CountByStatusResult item : countByStatusResult) {
            map.put(item.getStatus(), item.getTotalCount());
        }
        for (TenantStatusEnum status : TenantStatusEnum.values()) {
            if (!map.containsKey(status.getValue())) {
                map.put(status.getValue(), 0);
            }
        }
        return map;
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
        PlaTenant plaTenant = plaTenantMapper.selectById(enter.getT().getTenantId());
        if (plaTenant == null) {
            throw new FoundationException(ExceptionCodeEnums.TENANT_NOT_EXIST.getCode(),
                    ExceptionCodeEnums.TENANT_NOT_EXIST.getMessage());
        }
        if (!StringUtils.equals(TenantStatusEnum.INOPERATION.getValue(), plaTenant.getStatus())) {
            throw new FoundationException(ExceptionCodeEnums.STATUS_IS_REASONABLE.getCode(),
                    ExceptionCodeEnums.STATUS_IS_REASONABLE.getMessage());
        }
        plaTenant.setStatus(TenantStatusEnum.FROZEN.getValue());
        plaTenant.setUpdatedBy(enter.getUserId());
        plaTenant.setUpdatedTime(new Date());
        plaTenantMapper.updateById(plaTenant);

        // user
        QueryWrapper<PlaUser> plaUserQueryWrapper = new QueryWrapper<>();
        plaUserQueryWrapper.eq(PlaUser.COL_LOGIN_NAME, enter.getT().getEmail());
        plaUserQueryWrapper.eq(PlaUser.COL_USER_TYPE, accountType);
        PlaUser plaUser = plaUserMapper.selectOne(plaUserQueryWrapper);
        if (plaUser == null) {
            throw new FoundationException(ExceptionCodeEnums.USER_NOT_EXIST.getCode(),
                    ExceptionCodeEnums.USER_NOT_EXIST.getMessage());
        }
        if (!StringUtils.equals(UserStatusEnum.NORMAL.getValue(), plaUser.getStatus())) {
            throw new FoundationException(ExceptionCodeEnums.STATUS_IS_REASONABLE.getCode(),
                    ExceptionCodeEnums.STATUS_IS_REASONABLE.getMessage());
        }
        plaUser.setStatus(UserStatusEnum.LOCK.getValue());
        plaUser.setUpdatedBy(enter.getUserId());
        plaUser.setUpdatedTime(new Date());
        plaUserMapper.updateById(plaUser);
        // 权限
        QueryWrapper<PlaUserPermission> plaUserPermissionQueryWrapper = new QueryWrapper<>();
        plaUserPermissionQueryWrapper.eq(PlaUserPermission.COL_USER_ID, plaUser.getId());
        plaUserPermissionQueryWrapper.eq(PlaUserPermission.COL_APP_ID, appId);
        PlaUserPermission plaUserPermission = plaUserPermissionMapper.selectOne(plaUserPermissionQueryWrapper);
        if (plaUserPermission == null) {
            throw new FoundationException(ExceptionCodeEnums.USERPERMISSION_IS_NOT_EXIST.getCode(),
                    ExceptionCodeEnums.USERPERMISSION_IS_NOT_EXIST.getMessage());
        }
        if (!StringUtils.equals(UserStatusEnum.NORMAL.getValue(), plaUserPermission.getStatus())) {
            throw new FoundationException(ExceptionCodeEnums.STATUS_IS_REASONABLE.getCode(),
                    ExceptionCodeEnums.STATUS_IS_REASONABLE.getMessage());
        }
        plaUserPermission.setStatus(UserStatusEnum.LOCK.getValue());
        plaUserPermission.setUpdatedBy(enter.getUserId());
        plaUserPermission.setUpdatedTime(new Date());
        plaUserPermissionMapper.updateById(plaUserPermission);

        // 账户节点
        tenantBaseService.saveTenantNode(enter, TenanNodeEventEnum.FROZEN.getValue());

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
        String appId = AccountTypeUtils.getAppId(accountType);

        // 租户
        PlaTenant plaTenant = plaTenantMapper.selectById(enter.getT().getTenantId());
        if (plaTenant == null) {
            throw new FoundationException(ExceptionCodeEnums.TENANT_NOT_EXIST.getCode(),
                    ExceptionCodeEnums.TENANT_NOT_EXIST.getMessage());
        }
        if (!StringUtils.equals(TenantStatusEnum.FROZEN.getValue(), plaTenant.getStatus())) {
            throw new FoundationException(ExceptionCodeEnums.STATUS_IS_REASONABLE.getCode(),
                    ExceptionCodeEnums.STATUS_IS_REASONABLE.getMessage());
        }
        plaTenant.setStatus(TenantStatusEnum.INOPERATION.getValue());
        plaTenant.setUpdatedBy(enter.getUserId());
        plaTenant.setUpdatedTime(new Date());
        plaTenantMapper.updateById(plaTenant);

        // user
        QueryWrapper<PlaUser> plaUserQueryWrapper = new QueryWrapper<>();
        plaUserQueryWrapper.eq(PlaUser.COL_LOGIN_NAME, enter.getT().getEmail());
        plaUserQueryWrapper.eq(PlaUser.COL_USER_TYPE, accountType);
        PlaUser plaUser = plaUserMapper.selectOne(plaUserQueryWrapper);
        if (plaUser == null) {
            throw new FoundationException(ExceptionCodeEnums.USER_NOT_EXIST.getCode(),
                    ExceptionCodeEnums.USER_NOT_EXIST.getMessage());
        }
        if (!StringUtils.equals(UserStatusEnum.LOCK.getValue(), plaUser.getStatus())) {
            throw new FoundationException(ExceptionCodeEnums.STATUS_IS_REASONABLE.getCode(),
                    ExceptionCodeEnums.STATUS_IS_REASONABLE.getMessage());
        }
        plaUser.setStatus(UserStatusEnum.NORMAL.getValue());
        plaUser.setUpdatedBy(enter.getUserId());
        plaUser.setUpdatedTime(new Date());
        plaUserMapper.updateById(plaUser);
        // 权限
        QueryWrapper<PlaUserPermission> plaUserPermissionQueryWrapper = new QueryWrapper<>();
        plaUserPermissionQueryWrapper.eq(PlaUserPermission.COL_USER_ID, plaUser.getId());
        plaUserPermissionQueryWrapper.eq(PlaUserPermission.COL_APP_ID, appId);
        PlaUserPermission plaUserPermission = plaUserPermissionMapper.selectOne(plaUserPermissionQueryWrapper);
        if (!StringUtils.equals(UserStatusEnum.LOCK.getValue(), plaUserPermission.getStatus())) {
            throw new FoundationException(ExceptionCodeEnums.STATUS_IS_REASONABLE.getCode(),
                    ExceptionCodeEnums.STATUS_IS_REASONABLE.getMessage());
        }
        Objects.requireNonNull(plaUserPermission).setStatus(UserStatusEnum.NORMAL.getValue());
        plaUserPermission.setUpdatedBy(enter.getUserId());
        plaUserPermission.setUpdatedTime(new Date());
        plaUserPermissionMapper.updateById(plaUserPermission);

        // 账户节点
        tenantBaseService.saveTenantNode(enter, TenanNodeEventEnum.UNFREEZE.getValue());
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 账户续期
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public GeneralResult renewAccont(DateTimeParmEnter<BaseCustomerResult> enter) {
        int accountType =
                AccountTypeUtils.getAccountType(enter.getT().getCustomerType(), enter.getT().getIndustryType());
        String appId = AccountTypeUtils.getAppId(accountType);

        // 租户
        PlaTenant plaTenant = plaTenantMapper.selectById(enter.getT().getTenantId());
        if (plaTenant == null) {
            throw new FoundationException(ExceptionCodeEnums.TENANT_NOT_EXIST.getCode(),
                    ExceptionCodeEnums.TENANT_NOT_EXIST.getMessage());
        }
        // 冻结不可续费
        if (StringUtils.equals(TenantStatusEnum.FROZEN.getValue(), plaTenant.getStatus())) {
            throw new FoundationException(ExceptionCodeEnums.STATUS_IS_REASONABLE.getCode(),
                    ExceptionCodeEnums.STATUS_IS_REASONABLE.getMessage());
        }

        // 1、 续期开始时间 必须在开通时间之后
        // 2、续期结束时间 必须在到期时间之后
        // 3、 续期结束时间 必须在开始时间之后
        if (DateUtil.timeComolete(enter.getStartDateTime(), enter.getEndDateTime()) < 0) {
            throw new FoundationException(ExceptionCodeEnums.RENEW_END_DATETIME_IS_NOT_AVAILABLE.getCode(),
                    ExceptionCodeEnums.RENEW_END_DATETIME_IS_NOT_AVAILABLE.getMessage());
        }

        if (DateUtil.timeComolete(plaTenant.getExpireTime(), enter.getEndDateTime()) < 0) {
            throw new FoundationException(ExceptionCodeEnums.RENEW_END_DATETIME_IS_NOT_AVAILABLE.getCode(),
                    ExceptionCodeEnums.RENEW_END_DATETIME_IS_NOT_AVAILABLE.getMessage());
        }

        if (DateUtil.timeComolete(plaTenant.getEffectiveTime(), enter.getStartDateTime()) < 0) {
            throw new FoundationException(ExceptionCodeEnums.RENEW_START_DATETIME_IS_NOT_AVAILABLE.getCode(),
                    ExceptionCodeEnums.RENEW_END_DATETIME_IS_NOT_AVAILABLE.getMessage());
        }
        plaTenant.setExpireTime(enter.getEndDateTime());
        plaTenant.setUpdatedTime(new Date());
        plaTenant.setUpdatedBy(enter.getUserId());

        plaTenantMapper.updateById(plaTenant);

        // 账户节点
        tenantBaseService.saveTenantNode(enter, TenanNodeEventEnum.RENEW.getValue());
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
        PlaUserPassword plaUserPassword = plaUserPasswordMapper.selectOne(plaUserPasswordQueryWrapper);
        if (plaUserPassword == null) {
            throw new FoundationException(ExceptionCodeEnums.ACCOUNT_IS_NOT_EXIST.getCode(),
                    ExceptionCodeEnums.ACCOUNT_IS_NOT_EXIST.getMessage());
        }

        plaUserPassword.setPassword(DigestUtils.md5Hex(Constant.DEFAULT_PASSWORD + plaUserPassword.getSalt()));
        plaUserPassword.setUpdatedBy(enter.getUserId());
        plaUserPassword.setUpdatedTime(new Date());
        plaUserPasswordMapper.updateById(plaUserPassword);
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
    public GeneralResult deleteUserbyTenantId(IdEnter enter) {
        /**
         * 1.租户删除 2.用户删除 3.用户信息删除
         */
        PlaTenant tenant = plaTenantMapper.selectById(enter.getId());

        QueryWrapper<PlaUser> wrapper = new QueryWrapper<>();
        wrapper.eq(PlaUser.COL_TENANT_ID, tenant.getId());
        wrapper.eq(PlaUser.COL_DR, 0);
        List<PlaUser> userList = plaUserMapper.selectList(wrapper);
        List<Long> idList = new ArrayList<>();
        idList = userList.stream().map(user -> user.getId()).collect(Collectors.toList());
        plaUserMapper.deleteBatchIds(idList);

        tenantMapper.deleteById(tenant.getId());

        if (tenant.getTenantType().equals(CustomerTypeEnum.ENTERPRISE.getValue())) {
            // 删除公司--2B信息 TODO

        } else {
            // 删除个人--2C信息 TODO
        }

        return new GeneralResult(enter.getRequestId());
    }

    private Long saveUserSingle(DateTimeParmEnter<BaseCustomerResult> enter, Long tenantId) {
        Integer accountType =
                AccountTypeUtils.getAccountType(enter.getT().getCustomerType(), enter.getT().getIndustryType());
        // 保存 user 信息
        PlaUser plaUser = new PlaUser();
        plaUser.setId(idAppService.getId(SequenceName.PLA_USER));
        plaUser.setDr(0);
        plaUser.setTenantId(tenantId);
        plaUser.setAppId(AccountTypeUtils.getAppId(accountType));
        plaUser.setSystemId(AccountTypeUtils.getSystemId(accountType));
        plaUser.setLoginName(enter.getT().getEmail());
        plaUser.setUserType(accountType);
        plaUser.setStatus(UserStatusEnum.NORMAL.getValue());
        plaUser.setCreatedBy(enter.getUserId());
        plaUser.setCreatedTime(new Date());
        plaUser.setUpdatedBy(enter.getUserId());
        plaUser.setUpdatedTime(new Date());

        plaUserMapper.insert(plaUser);

        // 保存密码
        QueryWrapper<PlaUserPassword> plaUserPasswordQueryWrapper = new QueryWrapper<>();
        plaUserPasswordQueryWrapper.eq(PlaUserPassword.COL_LOGIN_NAME, enter.getT().getEmail());
        PlaUserPassword plaUserPassword = plaUserPasswordMapper.selectOne(plaUserPasswordQueryWrapper);

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
            plaUserPasswordMapper.insert(savePassWord);
        }

        // 开通权限
        PlaUserPermission plaUserPermission = new PlaUserPermission();
        plaUserPermission.setId(idAppService.getId(SequenceName.PLA_USER_PERMISSION));
        plaUserPermission.setUserId(plaUser.getId());
        plaUserPermission.setSystemId(AccountTypeUtils.getSystemId(accountType));
        plaUserPermission.setAppId(AccountTypeUtils.getAppId(accountType));
        plaUserPermission.setStatus(UserStatusEnum.NORMAL.getValue());
        plaUserPermission.setCreatedBy(enter.getUserId());
        plaUserPermission.setCreatedTime(new Date());
        plaUserPermission.setUpdatedBy(enter.getUserId());
        plaUserPermission.setUpdatedTime(new Date());

        plaUserPermissionMapper.insert(plaUserPermission);
        return plaUser.getId();
    }
}
