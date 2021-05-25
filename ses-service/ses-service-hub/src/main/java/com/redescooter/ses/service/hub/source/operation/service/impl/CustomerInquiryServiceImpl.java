package com.redescooter.ses.service.hub.source.operation.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.redescooter.ses.api.common.enums.customer.CustomerSourceEnum;
import com.redescooter.ses.api.common.enums.customer.CustomerStatusEnum;
import com.redescooter.ses.api.common.enums.inquiry.InquiryPayStatusEnums;
import com.redescooter.ses.api.common.enums.inquiry.InquiryStatusEnums;
import com.redescooter.ses.api.common.vo.base.BooleanResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.hub.exception.SeSHubException;
import com.redescooter.ses.api.hub.service.operation.CustomerInquiryService;
import com.redescooter.ses.api.hub.vo.website.SyncOrderDataEnter;
import com.redescooter.ses.service.hub.exception.ExceptionCodeEnums;
import com.redescooter.ses.service.hub.source.operation.dm.OpeCustomer;
import com.redescooter.ses.service.hub.source.operation.dm.OpeCustomerInquiry;
import com.redescooter.ses.service.hub.source.operation.service.base.OpeCustomerInquiryService;
import com.redescooter.ses.service.hub.source.operation.service.base.OpeCustomerService;
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
    @Autowired
    private OpeCustomerService opeCustomerService;

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
    public BooleanResult synchronizationOfRosSuccess(IdEnter enter, SyncOrderDataEnter syncOrderDataEnter) {
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>进入hub的synchronizationOfRosSuccess");
        OpeCustomerInquiry opeCustomerInquiry = opeCustomerInquiryService.getById(enter.getId());
        if (opeCustomerInquiry == null) {
            throw new SeSHubException(ExceptionCodeEnums.INQUIRY_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.INQUIRY_IS_NOT_EXIST.getMessage());
        }
        if (syncOrderDataEnter.getIsInstallment().equals("1") || syncOrderDataEnter.getIsInstallment().equals("3")) {
            if (InquiryPayStatusEnums.UNPAY_DEPOSIT.getValue().equals(opeCustomerInquiry.getPayStatus())) {
                opeCustomerInquiry.setStatus(InquiryStatusEnums.PAY_DEPOSIT.getValue());
                opeCustomerInquiry.setPayStatus(InquiryPayStatusEnums.PAY_DEPOSIT.getValue());
            } else if (InquiryPayStatusEnums.ON_INSTALMENT.getValue().equals(opeCustomerInquiry.getPayStatus()) || InquiryPayStatusEnums.PAY_DEPOSIT.getValue().equals(opeCustomerInquiry.getPayStatus())) {
                if (syncOrderDataEnter.getPayStatus().toString().equals(InquiryPayStatusEnums.FINISHED_INSTALMENT.getValue())) {
                    opeCustomerInquiry.setStatus(InquiryStatusEnums.FINISH_PAYMENT_INSTALLMENTS.getValue());
                    opeCustomerInquiry.setPayStatus(InquiryPayStatusEnums.FINISHED_INSTALMENT.getValue());
                }else {
                    opeCustomerInquiry.setStatus(InquiryStatusEnums.START_PAYMENT_INSTALLMENTS.getValue());
                    opeCustomerInquiry.setPayStatus(InquiryPayStatusEnums.ON_INSTALMENT.getValue());
                }
            } else if (InquiryPayStatusEnums.FINISHED_INSTALMENT.getValue().equals(opeCustomerInquiry.getPayStatus())) {
                opeCustomerInquiry.setStatus(InquiryStatusEnums.FINISH_PAYMENT_INSTALLMENTS.getValue());
                opeCustomerInquiry.setPayStatus(InquiryPayStatusEnums.FINISHED_INSTALMENT.getValue());
            }
            opeCustomerInquiry.setAmountPaid(syncOrderDataEnter.getAmountPaid());
            opeCustomerInquiry.setAmountObligation(syncOrderDataEnter.getAmountObligation());
            opeCustomerInquiry.setTotalPrice(syncOrderDataEnter.getTotalPrice());
        } else {
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
        }
        boolean inquiryResult = opeCustomerInquiryService.updateById(opeCustomerInquiry);
        if (inquiryResult) {
            //订单同步完成之后，就同步customer的状态变为潜在用户
            OpeCustomer opeCustomer = opeCustomerService.getById(opeCustomerInquiry.getCustomerId());
            if (opeCustomer == null) {
                throw new SeSHubException(ExceptionCodeEnums.CUSTOMER_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.CUSTOMER_IS_NOT_EXIST.getMessage());
            }
            opeCustomer.setStatus(CustomerStatusEnum.POTENTIAL_CUSTOMERS.getValue());
            opeCustomer.setCustomerSource(CustomerSourceEnum.WEBSITE.getValue());
            opeCustomerService.updateById(opeCustomer);
        }
        log.info(inquiryResult + "{>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>inquiryResult}");
        return new BooleanResult(inquiryResult);
    }

    @Override
    @DS("operation")
    public BooleanResult synchronizationOfRosFail(IdEnter idEnter) {
        OpeCustomerInquiry opeCustomerInquiry = opeCustomerInquiryService.getById(idEnter.getId());
        if (opeCustomerInquiry == null) {
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
