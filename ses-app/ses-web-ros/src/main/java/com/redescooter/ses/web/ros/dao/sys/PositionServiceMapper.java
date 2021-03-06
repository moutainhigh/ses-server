package com.redescooter.ses.web.ros.dao.sys;

import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.web.ros.vo.sys.dept.DeptIdEnter;
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

    List<PositionTypeResult> positionTypeList(@Param("enter") DeptIdEnter enter);

   int listcount(@Param("enter") PositionEnter tenantId, @Param("deptIds") Set<Long> deptIds, @Param("systemRoot")String systemRoot);

   List<PositionResult> list(@Param("enter")PositionEnter tenantId,@Param("deptIds")Set<Long> deptIds,@Param("systemRoot")String systemRoot);

   PositionDetailsResult positionDetails(@Param("id") Long id);

}
