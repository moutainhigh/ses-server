package com.redescooter.ses.web.ros.dao.restproductionorder;

import com.redescooter.ses.web.ros.vo.restproductionorder.allocateorder.*;
import com.redescooter.ses.web.ros.vo.restproductionorder.optrace.OpTraceResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.purchaseorder.PurchaseRelationOrderResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassNameAllocateOrderServiceMapper
 * @Description
 * @Author Aleks
 * @Date2020/10/26 15:25
 * @Version V1.0
 **/
public interface AllocateOrderServiceMapper {

    int allocateTotal(@Param("enter") AllocateOrderListEnter enter);


    List<AllocateOrderListResult> allocateList(@Param("enter") AllocateOrderListEnter enter);

    List<WhDataResult> whData(@Param("enter")WhDataEnter enter);


    AllocateOrderDetailResult allocateDateil(@Param("id") Long id);


    List<AllocateOrderScooterDetailResult> allocateScooter(@Param("id") Long id);


    List<AllocateOrderCombinDetailResult> allocateCombin(@Param("id") Long id);


    List<AllocateOrderPartsDetailResult> allocateParts(@Param("id") Long id);


    List<AllocateEntrustResult> allocateEntrust(@Param("id") Long id);


    List<OpTraceResult> allocateTrace(@Param("id") Long id,@Param("orderTypeEnum")Integer orderTypeEnum);


   Integer allocateOutWh(@Param("id") Long id);


   /**
    * @Author Aleks
    * @Description
    * @Date  2020/10/28 19:26
    * @Param [purchaseId]
    * @return
    **/
    List<PurchaseRelationOrderResult> purchaseAllocate(@Param("id") Long id);


}
