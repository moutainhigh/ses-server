package com.redescooter.ses.web.ros.service.restproductionorder.number;

import com.redescooter.ses.api.common.vo.base.StringResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.number.OrderNumberEnter;

public interface OrderNumberService {
    /**
     * @Description
     * @Author: alex
     * @Date: 2020/10/28 12:51
     * @Param: enter
     * @Return: StringResult
     * @desc: 订单编号
     */
    StringResult orderNumber(OrderNumberEnter enter);
}
