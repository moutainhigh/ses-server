package com.redescooter.ses.web.ros.dao.restproductionorder;

import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.web.ros.dm.OpeProductionParts;
import com.redescooter.ses.web.ros.vo.restproductionorder.OrderProductDetailResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.inwhouse.InWhRelationOrderResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.outboundorder.*;
import com.redescooter.ses.web.ros.vo.restproductionorder.purchaseorder.KeywordEnter;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OutboundOrderServiceMapper {
    /**
     * @Description
     * @Author: alex
     * @Date: 2020/10/28 15:25
     * @Param: enter
     * @Return: CountByStatusResult
     * @desc: 状态分组
     */
    List<CountByStatusResult> countByType(GeneralEnter enter);

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/10/28 15:27
     * @Param: enter
     * @Return: countByType
     * @desc: 列表统计
     */
    int listCount(OutboundOrderListEnter enter);

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/10/28 15:28
     * @Param: enter
     * @Return: OutboundOrderListResult
     * @desc: 列表
     */
    List<OutboundOrderListResult> list(OutboundOrderListEnter enter);

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/10/28 15:29
     * @Param: enter
     * @Return: OutboundOrderDetailResult
     * @desc: 详情
     */
    OutboundOrderDetailResult detail(IdEnter enter);

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/10/28 19:44
     * @Param: id
     * @Return: OrderProductDetailResult
     * @desc: 整车产品列表
     */
    List<OrderProductDetailResult> productionScooterByBussId(Long id);

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/10/28 19:45
     * @Param: id
     * @Return: OrderProductDetailResult
     * @desc: 组合产品列表
     */
    List<OrderProductDetailResult> productionCombinByBussId(Long id);

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/10/28 19:50
     * @Param: id
     * @Return: OrderProductDetailResult
     * @desc: 部件列表
     */
    List<OrderProductDetailResult> productionPartByBussId(Long id);


    /**
     * 关联的发货单号下拉接口
     * @param enter
     * @return
     */
    List<InWhRelationOrderResult> invoiceData(@Param("enter") KeywordEnter enter);


    /**
     * @Param: invoiceId
     * @Return: SaveOrUpdateOutScooterBEnter
     * @desc: 整车产品列表
     */
    List<SaveOrUpdateOutScooterBEnter> relationInvoiceScooterData(@Param("invoiceId") Long id);


    /**
     * 组装件产品列表
     * @param id
     * @return
     */
    List<SaveOrUpdateOutCombinBEnter> relationInvoiceCombinData(@Param("invoiceId") Long id);


    /**
     * 部件产品列表
     * @param id
     * @return
     */
    List<SaveOrUpdateOutPartsBEnter> relationInvoicePartsData(@Param("invoiceId") Long id);
}
