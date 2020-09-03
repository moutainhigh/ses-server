package com.redescooter.ses.web.ros.dao.sys;

import com.redescooter.ses.web.ros.vo.sys.position.PositionEnter;
import com.redescooter.ses.web.ros.vo.sys.position.PositionResult;
import com.redescooter.ses.web.ros.vo.sys.position.PositionTypeResult;

import java.util.List;

/**
 * @ClassNameSysPositionMapper
 * @Description
 * @Author Joan
 * @Date2020/9/2 17:28
 * @Version V1.0
 **/
public interface PositionServiceMapper{

    List<PositionTypeResult> positionTypeList(long tenantId);

   int listcount(PositionEnter tenantId);

   List<PositionResult> list(PositionEnter tenantId);

}
