package com.redescooter.ses.web.ros.service.stripe;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
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

    /**
     * 网络钩子 成功时
     *
     * @param enter
     */
    GeneralResult succeeHooks(String enter);

    /**
     * 取消支付的勾子
     *
     * @param enter
     * @return
     */
    GeneralResult cancelledPaymentIntent(String enter);

    /**
     * 网络钩子 失败时
     *
     * @param enter
     * @return
     */
    GeneralResult failHooks(String enter);
}
