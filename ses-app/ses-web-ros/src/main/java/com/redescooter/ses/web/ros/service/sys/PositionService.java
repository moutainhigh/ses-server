package com.redescooter.ses.web.ros.service.organization;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.web.ros.vo.organization.position.PositionDeptListResult;
import com.redescooter.ses.web.ros.vo.organization.position.PositionDetailResult;
import com.redescooter.ses.web.ros.vo.organization.position.PositionListEnter;
import com.redescooter.ses.web.ros.vo.organization.position.SavePositionEnter;

import java.util.List;

/**
 * @ClassName:PositionService
 * @description: PositionService
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/06 16:38
 */
public interface PositionService {
    /**
     * 职位列表
     *
     * @param enter
     * @return
     */
    List<PositionDeptListResult> list(PositionListEnter enter);

    /**
     * 保存职位
     *
     * @param enter
     * @return
     */
    GeneralResult savePosition(SavePositionEnter enter);

    /**
     * 职位详情
     *
     * @param enter
     * @return
     */
    PositionDetailResult positionDetail(IdEnter enter);
}
