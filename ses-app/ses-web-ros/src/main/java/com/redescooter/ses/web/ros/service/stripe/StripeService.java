package com.redescooter.ses.web.ros.service.stripe;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.StringResult;
import spark.Request;
import spark.Response;

public interface StripeService {

    /**
     * 支付
     *
     * @param enter
     * @return
     */
    StringResult paymentIntent(IdEnter enter);

    /**
     * 网络钩子
     * 成功时
     *
     * @param request
     * @param response
     */
    GeneralResult succeeHooks(Request request, Response response);

    /**
     * 网络钩子
     * 失败时
     *
     * @param request
     * @param response
     * @return
     */
    GeneralResult failHooks(Request request, Response response);
}
