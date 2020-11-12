package com.redescooter.ses.web.ros.dao.restproductionorder;

import com.redescooter.ses.web.ros.vo.restproductionorder.inwhouse.InWhouseDetailCombinResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface InWhouseOrderCombinBServiceMapper {

    /**
     * @Author Aleks
     * @Description  通过入库单id 找到下面的组装件明细
     * @Date  2020/11/12 15:20
     * @Param
     * @return
     **/
    List<InWhouseDetailCombinResult> combinBs(@Param("inWhId")Long inWhId);
}
