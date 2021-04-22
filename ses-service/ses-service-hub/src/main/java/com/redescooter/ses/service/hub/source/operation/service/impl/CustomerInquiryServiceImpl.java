package com.redescooter.ses.service.hub.source.operation.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.redescooter.ses.api.common.enums.inquiry.InquiryPayStatusEnums;
import com.redescooter.ses.api.common.enums.inquiry.InquiryStatusEnums;
import com.redescooter.ses.api.common.vo.base.BooleanResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.hub.exception.SeSHubException;
import com.redescooter.ses.api.hub.service.operation.CustomerInquiryService;
import com.redescooter.ses.service.hub.exception.ExceptionCodeEnums;
import com.redescooter.ses.service.hub.source.operation.dm.OpeCustomerInquiry;
import com.redescooter.ses.service.hub.source.operation.service.base.OpeCustomerInquiryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

@DubboService
@DS("operation")
@Slf4j
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
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>进入hub的synchronizationOfRosSuccess");
        OpeCustomerInquiry opeCustomerInquiry = opeCustomerInquiryService.getById(enter.getId());
        if (opeCustomerInquiry == null) {
            throw new SeSHubException(ExceptionCodeEnums.INQUIRY_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.INQUIRY_IS_NOT_EXIST.getMessage());
        }
        // 判断这次是预定金的支付 还是尾款的支付
        if (InquiryPayStatusEnums.UNPAY_DEPOSIT.getValue().equals(opeCustomerInquiry.getPayStatus())) {
            log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>这个时候是预定金的支付 需要改动已付金额 待付金额");
            // 这个时候是预定金的支付 需要改动已付金额 待付金额
            opeCustomerInquiry.setAmountPaid(opeCustomerInquiry.getPrepaidDeposit());
            opeCustomerInquiry.setAmountObligation(opeCustomerInquiry.getAmountObligation().subtract(opeCustomerInquiry.getPrepaidDeposit()));
            opeCustomerInquiry.setStatus(InquiryStatusEnums.PAY_DEPOSIT.getValue());
            opeCustomerInquiry.setPayStatus(InquiryPayStatusEnums.PAY_DEPOSIT.getValue());

        } else if (InquiryPayStatusEnums.PAY_DEPOSIT.getValue().equals(opeCustomerInquiry.getPayStatus())) {
            log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>这个时候是尾款的支付 需要改动已付金额 待付金额");
            // 这个时候是尾款的支付 需要改动已付金额 待付金额
            opeCustomerInquiry.setAmountPaid(opeCustomerInquiry.getTotalPrice());
            opeCustomerInquiry.setAmountObligation(opeCustomerInquiry.getAmountObligation().subtract(opeCustomerInquiry.getAmountObligation()));
            opeCustomerInquiry.setStatus(InquiryStatusEnums.PAY_LAST_PARAGRAPH.getValue());
            opeCustomerInquiry.setPayStatus(InquiryPayStatusEnums.PAY_LAST_PARAGRAPH.getValue());
        }
        boolean inquiryResult = opeCustomerInquiryService.updateById(opeCustomerInquiry);
        log.info(inquiryResult + "{>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>inquiryResult}");
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
        opeCustomerInquiry.setStatus(InquiryStatusEnums.UNPAY_DEPOSIT.getValue());
//        opeCustomerInquiry.setPayStatus();
        boolean inquiryResult = opeCustomerInquiryService.updateById(opeCustomerInquiry);
        return new BooleanResult(inquiryResult);
    }
}
