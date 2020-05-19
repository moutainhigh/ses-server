package com.redescooter.ses.web.ros.service.stripe;

import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.StringResult;

public interface StripeService {

    /**
     * 支付
     *
     * @param enter
     * @return
     */
    StringResult paymentIntent(IdEnter enter);

//    /**
//     * 验证 交易成功
//     * @param request
//     * @param response
//     */
//    void webhookPaymentIntent(Request request, Response response);
}
