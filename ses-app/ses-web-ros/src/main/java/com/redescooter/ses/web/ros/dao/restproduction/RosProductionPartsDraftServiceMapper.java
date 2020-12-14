package com.redescooter.ses.web.ros.dao.restproduction;

import com.redescooter.ses.web.ros.vo.restproduct.RosPartsListEnter;
import com.redescooter.ses.web.ros.vo.restproduct.RosPartsListResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassNameRosProductionPartsDraftServiceMapper
 * @Description
 * @Author Aleks
 * @Date2020/9/24 15:46
 * @Version V1.0
 **/
public interface RosProductionPartsDraftServiceMapper {

    int partsDraftTotal(@Param("enter") RosPartsListEnter enter);

    List<RosPartsListResult> partsDraftList(@Param("enter") RosPartsListEnter enter);

}
