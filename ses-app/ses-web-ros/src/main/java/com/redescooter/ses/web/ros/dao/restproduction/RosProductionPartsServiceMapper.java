package com.redescooter.ses.web.ros.dao.restproduction;

import com.redescooter.ses.web.ros.vo.restproduct.RosPartsListEnter;
import com.redescooter.ses.web.ros.vo.restproduct.RosPartsListResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassNameRosProductionPartsServiceMapper
 * @Description
 * @Author kyle
 * @Date2020/9/24 15:42
 * @Version V1.0
 **/
public interface RosProductionPartsServiceMapper {

   int partsTotal(@Param("enter") RosPartsListEnter enter);

   List<RosPartsListResult> partsList(@Param("enter") RosPartsListEnter enter);


   List<RosPartsListResult> partsExport(@Param("ids") List<Long> ids);

}
