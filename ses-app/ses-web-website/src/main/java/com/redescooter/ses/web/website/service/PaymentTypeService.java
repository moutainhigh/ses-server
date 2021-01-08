package com.redescooter.ses.web.website.service;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.web.website.vo.payment.AddPaymentTypeEnter;
import com.redescooter.ses.web.website.vo.payment.PaymentTypeDetailsResult;

import java.util.List;

/**
 * @Author jerry
 * @Date 2021/1/6 3:25 上午
 * @Description 支付方式服务
 **/
public interface PaymentTypeService {
    /**
     * 创建支付方式
     *
     * @param enter
     * @return
     */
    GeneralResult addPaymentType(AddPaymentTypeEnter enter);

    /**
     * 获取支付方式详情
     *
     * @param enter
     */
    PaymentTypeDetailsResult getPaymentTypeDetails(IdEnter enter);


    /**
     * 获取支付方式列表
     *
     * @param enter
     */
    List<PaymentTypeDetailsResult> getPaymentTypeList(GeneralEnter enter);
}
