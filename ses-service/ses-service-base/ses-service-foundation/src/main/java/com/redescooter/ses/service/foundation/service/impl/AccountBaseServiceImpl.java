package com.redescooter.ses.service.foundation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.enums.base.SystemIDEnums;
import com.redescooter.ses.api.common.enums.ros.account.UserStatusEnum;
import com.redescooter.ses.api.common.enums.ros.customer.CustomerTypeEnum;
import com.redescooter.ses.api.common.enums.tenant.TenantStatus;
import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.base.BaseCustomerResult;
import com.redescooter.ses.api.common.vo.base.BaseUserResult;
import com.redescooter.ses.api.common.vo.base.DateTimeParmEnter;
import com.redescooter.ses.api.foundation.exception.FoundationException;
import com.redescooter.ses.api.foundation.service.base.AccountBaseService;
import com.redescooter.ses.api.foundation.service.base.TenantBaseService;
import com.redescooter.ses.api.foundation.vo.tenant.QueryAccountListEnter;
import com.redescooter.ses.api.foundation.vo.tenant.QueryAccountListResult;
import com.redescooter.ses.api.hub.service.corporate.CorporateAccountProService;
import com.redescooter.ses.api.hub.service.customer.ConsumerAccountProService;
import com.redescooter.ses.api.hub.vo.UserProfileHubEnter;
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
import com.redescooter.ses.tool.utils.bussinessutils.AccountTypeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Reference
    private CorporateAccountProService corporateAccountProService;

    @Reference
    private ConsumerAccountProService consumerAccountProService;

    @Autowired
    private AccountBaseServiceMapper accountBaseServiceMapper;


    /**
     * 账号开通
     *
     * @param enter
     * @return
     */
    @Override
    public BaseUserResult open(DateTimeParmEnter<BaseCustomerResult> enter) {
        BaseUserResult result = new BaseUserResult();
        BaseCustomerResult customer = enter.getT();
        if (chectMail(customer.getEmail())) {
            // 开通账户
            //1、 创建租户
            Long tenantId = tenantBaseService.saveTenant(enter);
            //2、 创建账户
            Long userId = saveUserSingle(enter, tenantId);
            //3、 创建个人信息
            UserProfileHubEnter userProfileHubEnter = UserProfileHubEnter.builder()
                    .inputUserId(tenantId)
                    .inputTenantId(userId)
                    .firstName(enter.getT().getContactFirstName())
                    .lastName(enter.getT().getCustomerLastName())
                    .fullName(enter.getT().getContactFirstName()+" " +enter.getT().getCustomerLastName())
                    .address(enter.getT().getAddress())
                    .certificateType(enter.getT().getCertificateType())
                    .certificateNegativeAnnex(enter.getT().getCertificateNegativeAnnex())
                    .certificatePositiveAnnex(enter.getT().getCertificatePositiveAnnex())
                    .telNumber1(enter.getT().getTelephone())
                    .email1(enter.getT().getEmail())
                    .build();
            userProfileHubEnter.setUserId(enter.getUserId());
            if (StringUtils.equals(CustomerTypeEnum.PERSONAL.getValue(),enter.getT().getCustomerType())){
//                consumerAccountProService.saveUserProfileHub(userProfileHubEnter);
            }
            if (StringUtils.equals(CustomerTypeEnum.ENTERPRISE.getValue(),enter.getT().getCustomerType())){
                corporateAccountProService.saveUserProfileHub(userProfileHubEnter);
            }
            result.setId(userId);
            result.setTenantId(tenantId);
        } else {
            throw new FoundationException(ExceptionCodeEnums.TENANT_NOT_EXIST.getCode(), ExceptionCodeEnums.TENANT_NOT_EXIST.getMessage());
        }
        // 发送邮件
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
        List<CountByStatusResult>  countByStatusResult = accountBaseServiceMapper.accountCountStatus();
        Map<String, Integer> map = new HashMap<>();
        for (CountByStatusResult item : countByStatusResult) {
            map.put(item.getStatus(), item.getTotalCount());
        }
        for (TenantStatus status : TenantStatus.values()) {
            if (!map.containsKey(status.getValue())) {
                map.put(status.getValue(), 0);
            }
        }
        return map;
    }

    private Long saveUserSingle(DateTimeParmEnter<BaseCustomerResult> enter, Long tenantId) {

        // 保存 user 信息
        PlaUser plaUser=new PlaUser();
        plaUser.setId(idAppService.getId(SequenceName.PLA_USER));
        plaUser.setDr(0);
        plaUser.setTenantId(tenantId);
        plaUser.setSystemId(SystemIDEnums.REDE_SAAS.getSystemId());
        plaUser.setLoginName(enter.getT().getEmail());
        plaUser.setUserType(AccountTypeUtils.queryAccountType(enter.getT().getCustomerType(),enter.getT().getIndustryType()));
        plaUser.setStatus(UserStatusEnum.NORMAL.getCode());
        plaUser.setCreatedBy(enter.getUserId());
        plaUser.setCreatedTime(new Date());
        plaUser.setUpdatedBy(enter.getUserId());
        plaUser.setUpdatedTime(new Date());

        plaUserMapper.insert(plaUser);

        // 保存密码
        QueryWrapper<PlaUserPassword> plaUserPasswordQueryWrapper=new QueryWrapper<>();
        plaUserPasswordQueryWrapper.eq(PlaUserPassword.COL_LOGIN_NAME,enter.getT().getEmail());
        PlaUserPassword plaUserPassword = plaUserPasswordMapper.selectOne(plaUserPasswordQueryWrapper);

        // 密码不存在创建，已存在 跳过
        if (plaUserPassword==null){
            PlaUserPassword savePassWord = new PlaUserPassword();
            savePassWord.setId(idAppService.getId(SequenceName.PLA_USER_PASSWORD));
            savePassWord.setLoginName(enter.getT().getEmail());
            savePassWord.setSalt(String.valueOf(RandomUtils.nextInt(10000, 99999)));
            savePassWord.setPassword(null);
            savePassWord.setCreatedBy(enter.getUserId());
            savePassWord.setCreatedTime(new Date());
            savePassWord.setUpdatedBy(enter.getUserId());
            savePassWord.setUpdatedTime(new Date());
            //2.2用户激活时，有客户输入密码进行设置密码流程
            plaUserPasswordMapper.insert(savePassWord);
        }

        // 开通权限
        PlaUserPermission plaUserPermission = new PlaUserPermission();
        plaUserPermission.setId(idAppService.getId(SequenceName.PLA_USER_PERMISSION));
        plaUserPermission.setUserId(plaUser.getId());
        plaUserPermission.setSystemId(enter.getSystemId());
        plaUserPermission.setAppId(AccountTypeUtils.queryAppId(AccountTypeUtils.queryAccountType(enter.getT().getCustomerType(),enter.getT().getIndustryType())));
        plaUserPermission.setStatus(UserStatusEnum.NORMAL.getValue());
        plaUserPermission.setCreatedBy(enter.getUserId());
        plaUserPermission.setCreatedTime(new Date());
        plaUserPermission.setUpdatedBy(enter.getUserId());
        plaUserPermission.setUpdatedTime(new Date());

        plaUserPermissionMapper.insert(plaUserPermission);
        return plaUser.getId();
    }
}
