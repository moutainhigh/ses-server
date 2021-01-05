package com.redescooter.ses.web.website.service;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.web.website.vo.product.AddPaymentTypeEnter;
import com.redescooter.ses.web.website.vo.product.PaymentTypeDetailsResult;

import java.util.List;

/**
 * @Author jerry
 * @Date 2021/1/6 3:25 上午
 * @Description 支付方式服务
 **/
public interface PaymentTypeService {
    /**
     * 创建产品配件
     *
     * @param enter
     * @return
     */
    Boolean addPaymentType(AddPaymentTypeEnter enter);

    /**
     * 获取产品配件详情
     *
     * @param enter
     */
    PaymentTypeDetailsResult getPaymentTypeDetails(IdEnter enter);


    /**
     * 获取产品配件列表
     *
     * @param enter
     */
    List<PaymentTypeDetailsResult> getPaymentTypeList(GeneralEnter enter);
}
