package com.redescooter.ses.web.ros.service.restproductionorder.purchaseorder;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.allocateorder.AllocateNoDataResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.purchaseorder.*;

import java.util.List;
import java.util.Map;

/**
 * @ClassNamePurchaseOrderService
 * @Description
 * @Author Aleks
 * @Date2020/10/23 18:20
 * @Version V1.0
 **/
public interface PurchaseOrderService {


    /**
     * @Author Aleks
     * @Description  采购单新增
     * @Date  2020/10/23 19:46
     * @Param
     * @return
     **/
    GeneralResult purchaseSave(PurchaseSaveOrUpdateEnter enter);


    /**
     * @Author Aleks
     * @Description  采购单编辑
     * @Date  2020/10/23 19:46
     * @Param
     * @return
     **/
    GeneralResult purchaseEdit(PurchaseSaveOrUpdateEnter enter);


    /**
     * @Author Aleks
     * @Description  采购单列表
     * @Date  2020/10/23 20:03
     * @Param [enter]
     * @return
     **/
    PageResult<PuraseListResult> purchaseList(PuraseListEnter enter);


    /**
     * @Author Aleks
     * @Description  采购单详情
     * @Date  2020/10/23 20:16
     * @Param
     * @return
     **/
    PurchaseDetailResult purchaseDetail(IdEnter enter);


    /**
     * @Author Aleks
     * @Description  立即下单
     * @Date  2020/10/23 20:32
     * @Param [enter]
     * @return
     **/
    GeneralResult confirmOrder(IdEnter enter);


    /**
     * @Author Aleks
     * @Description  取消订单
     * @Date  2020/10/23 20:32
     * @Param [enter]
     * @return
     **/
    GeneralResult cancelOrder(CancelOrderEnter enter);


    /**
     * @Author Aleks
     * @Description  关闭订单
     * @Date  2020/10/23 20:32
     * @Param [enter]
     * @return
     **/
    GeneralResult closeOrder(IdEnter enter);


    /**
     * @Author Aleks
     * @Description  删除采购单
     * @Date  2020/10/28 9:52
     * @Param [enter]
     * @return
     **/
    GeneralResult deleteOrder(IdEnter enter);


    /**
     * @Author Aleks
     * @Description  关闭订单
     * @Date  2020/10/23 20:32
     * @Param [enter]
     * @return
     **/
    List<AllocateNoDataResult> allocateNoData(KeywordEnter enter);


    /**
     * @Author Aleks
     * @Description  列表统计
     * @Date  2020/10/26 10:16
     * @Param [enter]
     * @return
     **/
    Map<String,Integer> listCount(GeneralEnter enter);


    /**
     * @Author Aleks
     * @Description  采购单日历
     * @Date  2020/10/28 15:06
     * @Param [enter]
     * @return
     **/
    List<PurchaseCalendarResult> purchaseCalendar(PurchaseCalendarEnter enter);



}
