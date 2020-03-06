package com.redescooter.ses.web.ros.service;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.web.ros.vo.position.PositionListEnter;
import com.redescooter.ses.web.ros.vo.position.PositionDeptListResult;
import com.redescooter.ses.web.ros.vo.position.SavePositionEnter;

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


}
