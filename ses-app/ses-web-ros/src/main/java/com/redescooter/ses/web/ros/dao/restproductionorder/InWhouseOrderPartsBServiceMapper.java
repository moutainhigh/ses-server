package com.redescooter.ses.web.ros.dao.restproductionorder;

import com.redescooter.ses.web.ros.vo.restproductionorder.inwhouse.InWhouseDetailPartsResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface InWhouseOrderPartsBServiceMapper {

    /**
     * @Author Aleks
     * @Description  通过入库单id  找入库单的部件明细
     * @Date  2020/11/12 15:12
     * @Param
     * @return
     **/
    List<InWhouseDetailPartsResult> partsBs(@Param("inWhId")Long inWhId);

}
