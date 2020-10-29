package com.redescooter.ses.web.ros.dao.restproductionorder;

import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.Invoiceorder.InvoiceOrderDetailResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.Invoiceorder.InvoiceOrderListEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.Invoiceorder.InvoiceOrderListResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.Invoiceorder.InvoiceSnResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.OrderProductDetailResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.purchaseorder.PurchaseRelationOrderResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface InvoiceOrderServiceMapper {
    /**
     * @Description
     * @Author: alex
     * @Date: 2020/10/27 10:43
     * @Param: enter
     * @Return: Map
     * @desc: 类型数量统计
     */
    List<CountByStatusResult> countByType(GeneralEnter enter);

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

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/10/27 13:16
     * @Param: enter
     * @Return: InvoiceOrderDetailResult
     * @desc: 详情
     */
    InvoiceOrderDetailResult detail(IdEnter enter);

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/10/27 16:16
     * @Param: id
     * @Return: OrderProductDetailResult
     * @desc: 整车产品列表
     */
    List<OrderProductDetailResult> scooterProductList(Long id);

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/10/27 16:16
     * @Param: id
     * @Return: OrderProductDetailResult
     * @desc: 组合产品列表
     */
    List<OrderProductDetailResult> combinationProductList(Long id);

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/10/27 16:17
     * @Param: id
     * @Return: OrderProductDetailResult
     * @desc: 部件产品列表
     */
    List<OrderProductDetailResult> partProductList(Long id);

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/10/27 16:53
     * @Param: id
     * @Return: InvoiceSnResult
     * @desc: 车辆序列号集合
     */
    List<InvoiceSnResult> scooterSnList(Long id);

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/10/27 16:54
     * @Param: id
     * @Return: InvoiceSnResult
     * @desc: 组合序列号结合
     */
    List<InvoiceSnResult> combinationSnList(Long id);

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/10/27 16:55
     * @Param: id
     * @Return: InvoiceSnResult
     * @desc: 部件序列号集合
     */
    List<InvoiceSnResult> partSnList(Long id);


    /**
     * @Author Aleks
     * @Description  通过采购单id 查询发货单
     * @Date  2020/10/28 19:32
     * @Param
     * @return
     **/
    List<PurchaseRelationOrderResult> purchaseInvoice(@Param("purchaseId")Long purchaseId);
}
