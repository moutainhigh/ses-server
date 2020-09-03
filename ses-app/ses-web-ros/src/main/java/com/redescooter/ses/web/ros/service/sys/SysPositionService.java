package com.redescooter.ses.web.ros.service.sys;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.web.ros.vo.sys.position.PositionTypeResult;

import java.util.List;

/**
 * @ClassNameSysPositionService
 * @Description
 * @Author Joan
 * @Date2020/9/2 17:25
 * @Version V1.0
 **/
public interface SysPositionService {
    /**
     * 岗位类型查询
     *
     * @param enter
     * @return
     */
    List<PositionTypeResult> selectPositionType(GeneralEnter enter);


}
