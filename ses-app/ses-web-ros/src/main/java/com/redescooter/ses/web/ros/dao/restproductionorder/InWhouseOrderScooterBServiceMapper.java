package com.redescooter.ses.web.ros.dao.restproductionorder;

import com.redescooter.ses.web.ros.vo.restproductionorder.inwhouse.InWhouseDetailScooterResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface InWhouseOrderScooterBServiceMapper {

    /**
     * @Author Aleks
     * @Description  通过入库单id  找入库单的整车明细
     * @Date  2020/11/12 15:12
     * @Param
     * @return
     **/
    List<InWhouseDetailScooterResult> scooterBs(@Param("inWhId")Long inWhId);
}
