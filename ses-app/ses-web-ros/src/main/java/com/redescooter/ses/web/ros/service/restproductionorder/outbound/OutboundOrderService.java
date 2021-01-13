package com.redescooter.ses.web.ros.service.restproductionorder.outbound;

import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.web.ros.dm.OpeOutWhouseOrder;
import com.redescooter.ses.web.ros.vo.bom.combination.CombinationListEnter;
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


    GeneralResult delete(@ModelAttribute @ApiParam("请求参数") IdEnter enter);


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
     * 关联的整车发货单产品信息
     * @param enter
     * @return
     */
    List<SaveOrUpdateOutScooterBEnter> relationInvoiceScooterData(IdEnter enter);


    /**
     * 关联的组装件发货单产品信息
     * @param enter
     * @return
     */
    List<SaveOrUpdateOutCombinBEnter> relationInvoiceCombinData(IdEnter enter);


    /**
     * 关联的发货单部件信息
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
     * 关联的组装单单据号下拉数据源,由组装单创建的出库单，只可能是部件出库单
     * @param enter
     * @return
     */
    List<InWhRelationOrderResult> relationCombinOrderData(@ModelAttribute @ApiParam("请求参数") CombinationListEnter enter);


    /**
     * 关联的组装单的产品信息，转为部件数据,由组装单创建的出库单，只可能是部件出库单
     * @param enter
     * @return
     */
    List<SaveOrUpdateOutPartsBEnter> relationCombinOrderDataPartsData(@ModelAttribute @ApiParam("请求参数") IdEnter enter);


    /**
     * 出库单确认出库
     * @param enter
     * @return
     */
    GeneralResult outWhConfirm(IdEnter enter);



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


    /**
     * 组装件点击备料  产生出库单（不管是哪种组装单都是生成部件出库单）
     * @param combinId
     */
    void createOutWhByCombin(Long combinId,Long userId);

}
