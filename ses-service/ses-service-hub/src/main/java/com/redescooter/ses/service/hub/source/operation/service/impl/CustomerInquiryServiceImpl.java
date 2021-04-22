package com.redescooter.ses.service.hub.source.operation.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.enums.inquiry.InquiryStatusEnums;
import com.redescooter.ses.api.common.vo.base.BooleanResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.inquiry.SiteWebInquiryEnter;
import com.redescooter.ses.api.hub.exception.SeSHubException;
import com.redescooter.ses.api.hub.service.operation.CustomerInquiryService;
import com.redescooter.ses.service.hub.exception.ExceptionCodeEnums;
import com.redescooter.ses.service.hub.source.operation.dm.OpeCustomerInquiry;
import com.redescooter.ses.service.hub.source.operation.service.base.OpeCustomerInquiryService;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

@DubboService
@DS("operation")
public class CustomerInquiryServiceImpl implements CustomerInquiryService {

    @Autowired
    private OpeCustomerInquiryService opeCustomerInquiryService;

    @Override
    @DS("operation")
    public BooleanResult payAgainCheck(IdEnter enter) {
        OpeCustomerInquiry customerInquiry = opeCustomerInquiryService.getById(enter.getId());
        if (customerInquiry == null) {
            throw new SeSHubException(ExceptionCodeEnums.INQUIRY_IS_NOT_EXIST.getCode(),
                    ExceptionCodeEnums.INQUIRY_IS_NOT_EXIST.getMessage());
        }
        if (!StringUtils.equals(customerInquiry.getStatus(), InquiryStatusEnums.UNPAY_DEPOSIT.getValue())) {
            throw new SeSHubException(ExceptionCodeEnums.STATUS_ILLEGAL.getCode(),
                    ExceptionCodeEnums.STATUS_ILLEGAL.getMessage());
        }
        if (!StringUtils.equals(customerInquiry.getPayStatus(), InquiryStatusEnums.UNPAY_DEPOSIT.getValue())) {

        }
        return new BooleanResult(Boolean.TRUE);
    }

    @Override
    @DS("operation")
    public BooleanResult synchronizationOfRosSuccess(IdEnter enter) {
        OpeCustomerInquiry opeCustomerInquiry = opeCustomerInquiryService.getById(enter.getId());
        if (opeCustomerInquiry==null){
            throw new SeSHubException(ExceptionCodeEnums.INQUIRY_IS_NOT_EXIST.getCode(),
                    ExceptionCodeEnums.INQUIRY_IS_NOT_EXIST.getMessage());
        }
        opeCustomerInquiry.setId(enter.getId());
        opeCustomerInquiry.setStatus("5");
        opeCustomerInquiry.setPayStatus("2");
        boolean inquiryResult = opeCustomerInquiryService.updateById(opeCustomerInquiry);
        return new BooleanResult(inquiryResult);
    }

    @Override
    @DS("operation")
    public BooleanResult synchronizationOfRosFail(IdEnter idEnter) {
        OpeCustomerInquiry opeCustomerInquiry = opeCustomerInquiryService.getById(idEnter.getId());
        if (opeCustomerInquiry==null){
            throw new SeSHubException(ExceptionCodeEnums.INQUIRY_IS_NOT_EXIST.getCode(),
                    ExceptionCodeEnums.INQUIRY_IS_NOT_EXIST.getMessage());
        }
        opeCustomerInquiry.setId(idEnter.getId());
        opeCustomerInquiry.setStatus("4");
        opeCustomerInquiry.setPayStatus("1");
        boolean inquiryResult = opeCustomerInquiryService.updateById(opeCustomerInquiry);
        return new BooleanResult(inquiryResult);
    }
}
