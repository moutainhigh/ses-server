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
     *
     * @param request
     * @param response
     */
    GeneralResult hooks(Request request, Response response);
}
