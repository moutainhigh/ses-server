package com.redescooter.ses.web.ros.dao.restproduction;

import com.redescooter.ses.web.ros.vo.restproduct.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassNameRosProductionPartsServiceMapper
 * @Description
 * @Author Aleks
 * @Date2020/9/24 15:42
 * @Version V1.0
 **/
public interface RosProductionPartsServiceMapper {

   int partsTotal(@Param("enter") RosPartsListEnter enter);

   List<RosPartsListResult> partsList(@Param("enter") RosPartsListEnter enter);


   List<RosPartsListResult> partsExport(@Param("ids") List<Long> ids);


   List<PartsNameData> partsNameData();


   List<PartsNoData> partsNoData(@Param("enter") PartsNoEnter enter);

}
