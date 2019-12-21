package com.redescooter.ses.service.foundation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.vo.base.BaseCustomerResult;
import com.redescooter.ses.api.common.vo.base.BaseUserResult;
import com.redescooter.ses.api.common.vo.base.DateTimeParmEnter;
import com.redescooter.ses.api.foundation.exception.FoundationException;
import com.redescooter.ses.api.foundation.service.base.AccountBaseService;
import com.redescooter.ses.service.foundation.dao.base.PlaTenantMapper;
import com.redescooter.ses.service.foundation.dao.base.PlaUserMapper;
import com.redescooter.ses.service.foundation.dao.base.PlaUserPasswordMapper;
import com.redescooter.ses.service.foundation.dm.base.PlaTenant;
import com.redescooter.ses.service.foundation.exception.ExceptionCodeEnums;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

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
        if (!chectMail(customer.getEmail())) {

        } else {
            throw new FoundationException(ExceptionCodeEnums.TENANT_NOT_EXIST.getCode(), ExceptionCodeEnums.TENANT_NOT_EXIST.getMessage());
        }
        return result;
    }

    @Override
    public Boolean chectMail(String mail) {

        QueryWrapper<PlaTenant> wrapper = new QueryWrapper<>();
        wrapper.eq(PlaTenant.COL_EMAIL, mail);
        wrapper.eq(PlaTenant.COL_DR, 0);

        return tenantMapper.selectCount(wrapper) == 0 ? Boolean.TRUE : Boolean.FALSE;
    }
}
