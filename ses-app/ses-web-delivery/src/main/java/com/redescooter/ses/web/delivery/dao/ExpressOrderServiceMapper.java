package com.redescooter.ses.web.delivery.dao;

import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.web.delivery.dm.CorExpressDelivery;
import com.redescooter.ses.web.delivery.dm.CorExpressOrder;
import com.redescooter.ses.web.delivery.vo.*;
import com.redescooter.ses.web.delivery.vo.edorder.AttribuableDriverListEnter;
import com.redescooter.ses.web.delivery.vo.edorder.DiverOrderInforResult;
import com.redescooter.ses.web.delivery.vo.edorder.ExpressOrderMapEnter;
import com.redescooter.ses.web.delivery.vo.edorder.RefuseOrderDetailResult;
import com.redescooter.ses.web.delivery.vo.task.DriverListResult;

import java.util.List;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 2/2/2020 11:09 上午
 * @ClassName: ExpressOrderServiceMapper
 * @Function: TODO
 */
public interface ExpressOrderServiceMapper {

    /**
     * 订单状态统计
     *
     * @param enter
     * @return
     */
    List<CountByStatusResult> countByStatus(GeneralEnter enter);

    /**
     * 快递订单列表分页总数查询
     *
     * @param enter
     * @return
     */
    int listCount(QueryExpressOrderByPageEnter enter);

    /**
     * 快递订单列表查询
     *
     * @param enter
     * @return
     */
    List<QueryExpressOrderByPageResult> list(QueryExpressOrderByPageEnter enter);

    /**
     * 订单详情查询
     *
     * @param enter
     * @return
     */
    QueryOrderDetailResult detail(IdEnter enter);

    /**
     * 获取订单节点
     *
     * @param enter
     * @return
     */
    List<QueryExpressOrderTraceResult> getOrderNode(IdEnter enter);
    /**
    * @Description
    * @Author  AlexLi
    * @Date   2020/2/3 13:51
    * @Param  ExpressOrderMapEnter
    * @Return List<ScooterMapResult>
    * @method   ExpressOrderServiceMapper
    */
    List<ScooterMapResult> scooterMap(ExpressOrderMapEnter enter);
    /**
    * @Description
    * @Author  AlexLi
    * @Date   2020/2/3 13:56
    * @Param  ExpressOrderMapEnter
    * @Return List<CorExpressOrder>
    * @method   ExpressOrderServiceMapper
    */
    List<CorExpressOrder> mapOrderList(ExpressOrderMapEnter enter);
    /**
    * @Description
    * @Author:  AlexLi
    * @Date:   2020/2/3 15:45
    * @Param:  enter
    * @Return: DiverOrderInforResult
    * @desc: 地图的司机信息
    */
    DiverOrderInforResult diverInfor(IdEnter enter);
    /**
    * @Description
    * @Author:  AlexLi
    * @Date:   2020/2/3 16:07
    * @Param:  enter
    * @Return: List<QueryOrderDetailResult>
    * @desc: 司机的订单列表
    */
    List<QueryOrderDetailResult> driverOrderList(IdEnter enter);
    /**
    * @Description
    * @Author:  AlexLi
    * @Date:   2020/2/13 22:50
    * @Param:  enter
    * @Return: RefuseOrderDetailResult
    * @desc:  拒绝订单详情
    */
    List<RefuseOrderDetailResult> refuseOrderDetail(IdEnter enter);
    /**
    * @Description
    * @Author:  AlexLi
    * @Date:   2020/2/13 23:09
    * @Param:  enter
    * @Return: DriverListResult
    * @desc: 可分配司机列表
    */
    List<DriverListResult> attribuableDriverList(AttribuableDriverListEnter enter);
    /**
    * @Description
    * @Author:  AlexLi
    * @Date:   2020/2/14 00:15
    * @Param:  driverId
    * @Return: CorExpressDelivery
    * @desc: 查询正在配送中订单 若无 返回当天已完成的订单
    */
    CorExpressDelivery expressDeliveryShippingByDriverId(Long driverId);
}
