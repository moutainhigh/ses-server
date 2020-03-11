package com.redescooter.ses.web.ros.service.organization.impl;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.web.ros.service.organization.PositionService;
import com.redescooter.ses.web.ros.vo.organization.position.PositionDeptListResult;
import com.redescooter.ses.web.ros.vo.organization.position.PositionDetailResult;
import com.redescooter.ses.web.ros.vo.organization.position.PositionListEnter;
import com.redescooter.ses.web.ros.vo.organization.position.SavePositionEnter;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;

import java.util.List;

/**
 * @ClassName:PositionServiceImpl
 * @description: PositionServiceImpl
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/09 13:23
 */
@Slf4j
@Service
public class PositionServiceImpl implements PositionService {

    /**
     * 职位列表
     *
     * @param enter
     * @return
     */
    @Override
    public List<PositionDeptListResult> list(PositionListEnter enter) {
        return null;
    }

    /**
     * 保存职位
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult savePosition(SavePositionEnter enter) {
        return null;
    }

    /**
     * 职位详情
     *
     * @param enter
     * @return
     */
    @Override
    public PositionDetailResult positionDetail(IdEnter enter) {
        return null;
    }
}
