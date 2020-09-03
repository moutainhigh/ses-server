package com.redescooter.ses.web.ros.service.sys;

import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.web.ros.vo.sys.dept.UpdateDeptEnter;
import com.redescooter.ses.web.ros.vo.sys.position.*;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.ModelAttribute;

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

    /**
     * 岗位列表
     *
     * @param enter
     * @return
     */
    PageResult<PositionResult> list(PositionEnter enter);

    /**
     * 岗位列表
     *
     * @param enter
     * @return

     */
   EditPositionEnter positionEdit( UpdateDeptEnter enter);

    PositionDetailsResult positionDetails(IdEnter enter);

    GeneralResult save(SavePositionEnter enter);

   BooleanResult deletePositionSelect(IdEnter enter);

    GeneralResult deletePosition(IdEnter enter);
}
