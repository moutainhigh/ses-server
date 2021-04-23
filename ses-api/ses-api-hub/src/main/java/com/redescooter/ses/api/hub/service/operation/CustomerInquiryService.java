package com.redescooter.ses.api.hub.service.operation;

import com.redescooter.ses.api.common.vo.base.BooleanResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.inquiry.SiteWebInquiryEnter;

public interface CustomerInquiryService {
    /**
     * 再次支付校验的接口
     * @param enter
     * @return
     */
    BooleanResult payAgainCheck(IdEnter enter);

    /**
     * 同步ros支付成功的订单状态
     */
    BooleanResult synchronizationOfRosSuccess(IdEnter idEnter);

    /**
     * 同步ros支付失败的订单状态
     */
    BooleanResult synchronizationOfRosFail(IdEnter idEnter);
}
