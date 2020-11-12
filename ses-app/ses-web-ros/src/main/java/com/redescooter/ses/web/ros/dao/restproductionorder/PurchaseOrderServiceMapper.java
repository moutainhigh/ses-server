package com.redescooter.ses.web.ros.dao.restproductionorder;

import com.redescooter.ses.web.ros.dm.OpePurchaseCombinB;
import com.redescooter.ses.web.ros.dm.OpePurchasePartsB;
import com.redescooter.ses.web.ros.dm.OpePurchaseScooterB;
import com.redescooter.ses.web.ros.vo.restproductionorder.allocateorder.AllocateNoDataResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.optrace.OpTraceResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.purchaseorder.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassNamePurchaseServiceMapper
 * @Description
 * @Author Aleks
 * @Date2020/10/26 15:25
 * @Version V1.0
 **/
public interface PurchaseOrderServiceMapper {

    int purchaseListTotal(@Param("enter") PuraseListEnter enter);

    List<PuraseListResult> purchaseList(@Param("enter") PuraseListEnter enter);

    PurchaseDetailResult purchaseDetail(@Param("id") Long id);

    List<PurchaseScooterDetailResult> purchaseScooter(@Param("id") Long id);

    List<PurchaseCombineDetailResult> purchaseCombin(@Param("id") Long id);

    List<PurchasePartsDetailResult> purchaseParts(@Param("id") Long id);

    List<OpTraceResult> purchaseTrace(@Param("id") Long id);

    List<AllocateNoDataResult> allocateNoData(@Param("enter") KeywordEnter enter);

    List<PurchaseCalendarResult> purchaseCalendar(@Param("enter")PurchaseCalendarEnter enter);

    /**
     * @Author Aleks
     * @Description  查采购单下的出库单状态为“质检中”或“已出库”的数量
     * @Date  2020/10/30 15:49
     * @Param
     * @return
     **/
     int whNum(@Param("purchaseId") Long purchaseId);

     /**
      * @Author Aleks
      * @Description  查询调拨单下面所有的已签收或完成的采购单的组装件明细
      * @Date  2020/11/11 19:51
      * @Param
      * @return
      **/
    List<OpePurchaseCombinB> purchaseCombinB(@Param("allocateId") Long allocateId,@Param("purchaseId") Long purchaseId);


    /**
     * @Author Aleks
     * @Description  查询调拨单下面所有的已签收或完成的采购单的部件明细
     * @Date  2020/11/12 19:12
     * @Param [allocateId, purchaseId]
     * @return
     **/
    List<OpePurchasePartsB> purchasePartsBS(@Param("allocateId") Long allocateId, @Param("purchaseId") Long purchaseId);


    /**
     * @Author Aleks
     * @Description  查询调拨单下面所有的已签收或完成的采购单的整车明细
     * @Date  2020/11/12 19:44
     * @Param [allocateId, purchaseId]
     * @return
     **/
    List<OpePurchaseScooterB> purchaseScooterBS(@Param("allocateId") Long allocateId, @Param("purchaseId") Long purchaseId);
}
