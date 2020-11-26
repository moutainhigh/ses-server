package com.redescooter.ses.api.hub.service.operation;

import com.redescooter.ses.api.common.vo.base.*;

/**
 * @ClassName:CustomerService
 * @description: CustomerService
 * @author: Alex
 * @Version：1.3
 * @create: 2020/01/10 12:01
 */
public interface CustomerService {
    /**
     * id 为 租户Id
     *
     * @param enter
     * @return
     */
    BaseCustomerResult customerInfo(IdEnter enter);

    /**
     * 更新 客户信息
     *
     * @param enter
     * @return
     */
    GeneralResult updateCustomerInfo(BaseCustomerEnter enter);

    /**
     * 根据属性确定客户信息
     *
     * @param enter
     * @return
     */
    GeneralResult updateCustomerInfoByAnyProperty(BaseCustomerEnter enter);
    /**
    * @Description
    * @Author: alex
    * @Date:   2020/11/26 10:51 上午
    * @Param:  enter
    * @Return: GeneralResult
    * @desc: 更新客户个人信息
    */
    GeneralResult updateCustomerInfoByEmail(BaseCustomerEnter enter);
}
