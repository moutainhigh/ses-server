package com.redescooter.ses.web.ros.dao.restproductionorder;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.Invoiceorder.InvoiceOrderListEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.Invoiceorder.InvoiceOrderListResult;

import java.util.List;
import java.util.Map;

public interface InvoiceOrderServiceMapper {
    /**
     * @Description
     * @Author: alex
     * @Date: 2020/10/27 10:43
     * @Param: enter
     * @Return: Map
     * @desc: 类型数量统计
     */
    Map<Integer, Integer> countByType(GeneralEnter enter);

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/10/27 10:52
     * @Param: enter
     * @Return: int
     * @desc: 列表统计
     */
    int listCount(InvoiceOrderListEnter enter);

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/10/27 10:53
     * @Param: enter
     * @Return: list
     * @desc: 列表
     */
    List<InvoiceOrderListResult> list(InvoiceOrderListEnter enter);
}
