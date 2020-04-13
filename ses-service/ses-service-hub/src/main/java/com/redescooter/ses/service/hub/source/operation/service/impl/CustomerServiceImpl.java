package com.redescooter.ses.service.hub.source.operation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.vo.base.BaseCustomerEnter;
import com.redescooter.ses.api.common.vo.base.BaseCustomerResult;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.hub.exception.SeSHubException;
import com.redescooter.ses.api.hub.service.operation.CustomerService;
import com.redescooter.ses.service.common.service.CityAppService;
import com.redescooter.ses.service.hub.exception.ExceptionCodeEnums;
import com.redescooter.ses.service.hub.source.operation.dao.base.OpeCustomerMapper;
import com.redescooter.ses.service.hub.source.operation.dm.OpeCustomer;
import com.redescooter.ses.service.hub.source.operation.service.base.OpeCustomerService;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
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

    @Autowired
    private OpeCustomerService opeCustomerService;
    @Reference
    private CityAppService cityAppService;

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
        if (null != opeCustomer.getCity() && 0 != opeCustomer.getCity()) {
            baseCustomerResult.setCity(opeCustomer.getCity());
            String cityNameById = cityAppService.getCityNameById(opeCustomer.getCity());
            if (StringUtils.isNotBlank(cityNameById)) {
                baseCustomerResult.setCityName(cityNameById);
            }
        }
        if (null != opeCustomer.getDistrust() && 0 != opeCustomer.getDistrust()) {
            baseCustomerResult.setDistrust(opeCustomer.getDistrust());
            String cityNameById = cityAppService.getCityNameById(opeCustomer.getDistrust());
            if (StringUtils.isNotBlank(cityNameById)) {
                baseCustomerResult.setDistrustName(cityNameById);
            }
        }
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
        if (StringUtils.isNotBlank(enter.getContactFirstName()) && StringUtils.isNotBlank(enter.getContactLastName())) {
//            opeCustomer.setCustomerFirstName(enter.getCustomerFirstName());
//            opeCustomer.setCustomerLastName(enter.getCustomerLastName());
//            opeCustomer.setCustomerFullName(enter.getCustomerFullName());
            opeCustomer.setContactFirstName(enter.getContactFirstName());
            opeCustomer.setContactLastName(enter.getContactLastName());
            opeCustomer.setContactFullName(enter.getContactFullName());
        }
        if (StringUtils.isNotBlank(enter.getPicture())) {
            opeCustomer.setPicture(enter.getPicture());
        }
        if (StringUtils.isNotBlank(enter.getTelephone())) {
            opeCustomer.setTelephone(enter.getTelephone());
        }

        opeCustomerMapper.updateById(opeCustomer);
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 根据属性确定客户信息
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult updateCustomerInfoByAnyProperty(BaseCustomerEnter enter) {
        QueryWrapper<OpeCustomer> opeCustomerQueryWrapper = new QueryWrapper<>();
        opeCustomerQueryWrapper.eq(OpeCustomer.COL_DR, 0);
        opeCustomerQueryWrapper.eq(OpeCustomer.COL_TENANT_ID, 0);
        opeCustomerQueryWrapper.eq(OpeCustomer.COL_CUSTOMER_TYPE, enter.getCustomerType());
        opeCustomerQueryWrapper.eq(OpeCustomer.COL_INDUSTRY_TYPE, enter.getIndustryType());
        OpeCustomer opeCustomer = opeCustomerService.getOne(opeCustomerQueryWrapper);
        if (opeCustomer == null) {

        }

        return null;
    }
}
