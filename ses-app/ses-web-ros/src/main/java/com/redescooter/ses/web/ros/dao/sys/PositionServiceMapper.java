package com.redescooter.ses.web.ros.dao.sys;

import com.redescooter.ses.web.ros.vo.sys.position.PositionDetailsResult;
import com.redescooter.ses.web.ros.vo.sys.position.PositionEnter;
import com.redescooter.ses.web.ros.vo.sys.position.PositionResult;
import com.redescooter.ses.web.ros.vo.sys.position.PositionTypeResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * @ClassNameSysPositionMapper
 * @Description
 * @Author Joan
 * @Date2020/9/2 17:28
 * @Version V1.0
 **/
public interface PositionServiceMapper{

    List<PositionTypeResult> positionTypeList(@Param("tenantId") Long tenantId,@Param("deptId") Long deptId);

   int listcount(@Param("enter") PositionEnter tenantId,@Param("deptIds") Set<Long> deptIds);

   List<PositionResult> list(@Param("enter")PositionEnter tenantId,@Param("deptIds")Set<Long> deptIds);

   PositionDetailsResult positionDetails(@Param("id") Long id);

}
