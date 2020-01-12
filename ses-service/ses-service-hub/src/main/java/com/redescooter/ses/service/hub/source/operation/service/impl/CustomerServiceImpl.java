package com.redescooter.ses.service.hub.source.operation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.vo.base.BaseCustomerEnter;
import com.redescooter.ses.api.common.vo.base.BaseCustomerResult;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.hub.exception.SeSHubException;
import com.redescooter.ses.api.hub.service.operation.CustomerService;
import com.redescooter.ses.service.hub.exception.ExceptionCodeEnums;
import com.redescooter.ses.service.hub.source.operation.dao.base.OpeCustomerMapper;
import com.redescooter.ses.service.hub.source.operation.dm.OpeCustomer;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ClassName:CustomerServiceImpl
 * @description: CustomerServiceImpl
 * @author: Alex
 * @Version：1.3
 * @create: 2020/01/10 13:28
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private OpeCustomerMapper opeCustomerMapper;

    /**
     * id 为 租户Id
     *
     * @param enter
     * @return
     */
    @Override
    public BaseCustomerResult customerInfo(IdEnter enter) {
        QueryWrapper<OpeCustomer> opeCustomerQueryWrapper = new QueryWrapper<>();
        opeCustomerQueryWrapper.eq(OpeCustomer.COL_DR, 0);
        opeCustomerQueryWrapper.eq(OpeCustomer.COL_TENANT_ID, enter.getId());
        OpeCustomer opeCustomer = opeCustomerMapper.selectOne(opeCustomerQueryWrapper);
        if (opeCustomer == null) {
            throw new SeSHubException(ExceptionCodeEnums.CUSTOMER_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.CUSTOMER_IS_NOT_EXIST.getMessage());
        }
        BaseCustomerResult baseCustomerResult = new BaseCustomerResult();
        BeanUtils.copyProperties(opeCustomer, baseCustomerResult);
        return baseCustomerResult;
    }

    /**
     * 更新 客户信息
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult updateCustomerInfo(BaseCustomerEnter enter) {

        OpeCustomer opeCustomer = opeCustomerMapper.selectById(enter.getId());
        if (opeCustomer == null) {
            throw new SeSHubException(ExceptionCodeEnums.CUSTOMER_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.CUSTOMER_IS_NOT_EXIST.getMessage());
        }
        opeCustomer.setCustomerFirstName(enter.getCustomerFirstName());
        opeCustomer.setCustomerLastName(enter.getCustomerLastName());
        opeCustomer.setCustomerFullName(enter.getCustomerFullName());
        opeCustomer.setContactFirstName(enter.getContactFirstName());
        opeCustomer.setContactLastName(enter.getContactLastName());
        opeCustomer.setContactFullName(enter.getContactFullName());
        opeCustomer.setPicture(enter.getPicture());
        opeCustomer.setTelephone(enter.getTelephone());

        opeCustomerMapper.updateById(opeCustomer);
        return new GeneralResult(enter.getRequestId());
    }
}
