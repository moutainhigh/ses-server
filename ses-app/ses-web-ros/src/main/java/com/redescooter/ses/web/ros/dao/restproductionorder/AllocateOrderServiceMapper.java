package com.redescooter.ses.web.ros.dao.restproductionorder;

import com.redescooter.ses.web.ros.vo.restproductionorder.allocateorder.AllocateOrderListEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.allocateorder.AllocateOrderListResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.allocateorder.WhDataEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.allocateorder.WhDataResult;
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


}
