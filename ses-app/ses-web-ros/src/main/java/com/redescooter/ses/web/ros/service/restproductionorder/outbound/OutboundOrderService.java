package com.redescooter.ses.web.ros.service.restproductionorder.outbound;

import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.web.ros.dm.OpeOutWhouseOrder;
import com.redescooter.ses.web.ros.vo.restproductionorder.AssociatedOrderResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.OrderProductDetailResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.inwhouse.InWhRelationOrderResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.outboundorder.*;
import com.redescooter.ses.web.ros.vo.restproductionorder.purchaseorder.KeywordEnter;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;
import java.util.Map;

/**
 *  @author: alex
 *  @Date: 2020/10/22 13:26
 *  @version：V ROS 1.8.3
 *  @Description:
 */
public interface OutboundOrderService {
    /**
     * @Description
     * @Author: alex
     * @Date: 2020/10/22 13:29
     * @Param: enter
     * @Return: Map
     * @desc: 出库单产品类型统计
     */
    Map<Integer, Integer> countByType(GeneralEnter enter);

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/10/22 13:42
     * @Param: enter
     * @Return: Map
     * @desc: 状态列表
     */
    Map<Integer, String> statusList(GeneralEnter enter);

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/10/22 13:50
     * @Param: enter
     * @Return: OutboundOrderListResult
     * @desc: 出库单列表
     */
    PageResult<OutboundOrderListResult> list(OutboundOrderListEnter enter);

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/10/22 13:52
     * @Param: enter
     * @Return: OutboundOrderDetailResult
     * @desc: 出库单列表
     */
    OutboundOrderDetailResult detail(IdEnter enter);


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
    List<AssociatedOrderResult> associatedOrderList(OpeOutWhouseOrder opeOutWhouseOrder);

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/10/26 16:52
     * @Param: enter
     * @Return: GeneralResult
     * @desc: 保存
     */
    GeneralResult save(SaveOutboundOrderEnter enter);


    /**
     * 关联的发货单号下拉接口
     * @param enter
     * @return
     */
    List<InWhRelationOrderResult> invoiceData(KeywordEnter enter);


    /**
     * 关联的整车产品信息
     * @param enter
     * @return
     */
    List<SaveOrUpdateOutScooterBEnter> relationInvoiceScooterData(IdEnter enter);


    /**
     * 关联的组装单的组装件产品信息
     * @param enter
     * @return
     */
    List<SaveOrUpdateOutCombinBEnter> relationInvoiceCombinData(IdEnter enter);


    /**
     * 关联的部件的组装件产品信息
     * @param enter
     * @return
     */
    List<SaveOrUpdateOutPartsBEnter> relationInvoicePartsData(IdEnter enter);


    /**
     * 出库单新增
     * @param enter
     * @return
     */
    GeneralResult outOrderSave(SaveOrUpdateOutOrderEnter enter);


    /**
     * 出库单编辑
     * @param enter
     * @return
     */
    GeneralResult outOrderEdit(SaveOrUpdateOutOrderEnter enter);


    /**
     * 出库单提交
     * @param enter
     * @return
     */
    GeneralResult outOrderSubmit(IdEnter enter);



    /**
     * @Author Aleks
     * @Description  发货单取消 下面的出库单也要取消
     * @Date  2020/10/30 16:19
     * @Param [purchaseId, userId, remark]
     * @return
     **/
    void cancelOutWh(Long invoiceId,Long userId,String remark);


    /**
     * @Author Aleks
     * @Description  开始质检
     * @Date  2020/11/5 10:12
     * @Param [enter]
     * @return
     **/
    GeneralResult startQc(IdEnter enter);


    /**
     * @Author Aleks
     * @Description  质检完成
     * @Date  2020/11/5 10:12
     * @Param [enter]
     * @return
     **/
    GeneralResult endQc(IdEnter enter);

}
