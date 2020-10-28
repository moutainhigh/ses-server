package com.redescooter.ses.web.ros.dao.restproductionorder;

import com.redescooter.ses.web.ros.vo.restproductionorder.allocateorder.*;
import com.redescooter.ses.web.ros.vo.restproductionorder.optrace.OpTraceResult;
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

    // todo
    List<AllocateEntrustResult> allocateEntrust(@Param("id") Long id);


    List<OpTraceResult> allocateTrace(@Param("id") Long id);


   Integer allocateOutWh(@Param("id") Long id);


}
