package com.redescooter.ses.web.ros.service.restproductionorder.invoice;

import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.web.ros.vo.restproductionorder.AssociatedOrderResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.Invoiceorder.InvoiceOrderDetailResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.Invoiceorder.InvoiceOrderListEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.Invoiceorder.InvoiceOrderListResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.Invoiceorder.SaveInvoiceEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.OrderProductDetailResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.QueryStaffResult;

import java.util.List;
import java.util.Map;

/**
 *  @author: alex
 *  @Date: 2020/10/23 12:12
 *  @version：V ROS 1.8.3
 *  @Description:
 */
public interface InvoiceOrderService {
    /**
     * @Description
     * @Author: alex
     * @Date: 2020/10/23 12:13
     * @Param: enter
     * @Return: Map
     * @desc: 状态分组
     */
    Map<Integer, Integer> countByType(GeneralEnter enter);

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/10/23 12:16
     * @Param: enter
     * @Return: Map
     * @desc: 状态列表
     */
    Map<Integer, Integer> statusList(GeneralEnter enter);

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/10/23 17:32
     * @Param: enter
     * @Return: ShippingListResult
     * @desc: 发货单列表
     */
    PageResult<InvoiceOrderListResult> list(InvoiceOrderListEnter enter);

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/10/26 12:28
     * @Param: enter
     * @Return: ShippingDetailResult
     * @desc: 详情
     */
    InvoiceOrderDetailResult detail(IdEnter enter);

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/10/27 17:37
     * @Param: enter
     * @Return: OrderProductDetailResult
     * @desc: 查询发货单产品列表
     */
    List<OrderProductDetailResult> productListById(IdEnter enter);

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/10/27 18:14
     * @Param: enter
     * @Return: AssociatedOrderResult
     * @desc: 关联订单列表
     */
    List<AssociatedOrderResult> associatedOrderList(IdEnter enter);

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/10/26 14:00
     * @Param: enter
     * @Return: GeneralResult
     * @desc: 备货
     */
    GeneralResult stockUp(IdEnter enter);

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/10/26 15:49
     * @Param: enter
     * @Return: GeneralResult
     * @desc:
     */
    GeneralResult save(SaveInvoiceEnter enter);

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/10/28 10:28
     * @Param: enter
     * @Return: QueryStaffResult
     * @desc: 员工列表
     */
    List<QueryStaffResult> staffList(StringEnter enter);

}
