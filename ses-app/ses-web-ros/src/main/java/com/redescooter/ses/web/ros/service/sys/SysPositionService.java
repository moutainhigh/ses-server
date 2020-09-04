package com.redescooter.ses.web.ros.service.sys;

import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.web.ros.vo.sys.position.*;

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
     * 岗位编辑
     *
     * @param enter
     * @return
     */

    GeneralResult positionEdit( EditPositionEnter enter);

    /**
     * 岗位详情
     *
     * @param enter
     * @return
     */
    PositionDetailsResult positionDetails(IdEnter enter);

    /**
     * 岗位新增
     *
     * @param enter
     * @return
     */
    GeneralResult save(SavePositionEnter enter);

    /**
     * 岗位删除校验
     *
     * @param enter
     * @return
     */
   BooleanResult deletePositionSelect(IdEnter enter);

    /**
     * 岗位删除
     *
     * @param enter
     * @return
     */
    GeneralResult deletePosition(IdEnter enter);
}
