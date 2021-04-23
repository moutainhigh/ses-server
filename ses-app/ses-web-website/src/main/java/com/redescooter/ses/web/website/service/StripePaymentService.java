package com.redescooter.ses.web.website.service;

import com.redescooter.ses.api.common.vo.base.*;

/**
 * @Author jerry
 * @Date 2021/1/23 11:53 下午
 * @Description stripe第三方支付
 **/
public interface StripePaymentService {

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


    /**
     * @Author Aleks
     * @Description  获取加密之后的公钥
     * @Date  2020/7/20 15:52
     * @Param
     * @return
     **/
    PublicSecretResult publicSecret();

    /**
     * 在次支付校验
     *
     * @param enter
     * @return
     */
    BooleanResult payAgainCheck(IdEnter enter);
}
