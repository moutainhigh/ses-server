package com.redescooter.ses.web.ros.service.assign.impl;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.web.ros.service.assign.AssignedService;
import com.redescooter.ses.web.ros.vo.assign.done.enter.AssignedListEnter;
import com.redescooter.ses.web.ros.vo.assign.done.result.AssignedDetailResult;
import com.redescooter.ses.web.ros.vo.assign.done.result.AssignedListResult;
import org.apache.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description 车辆已分配ServiceImpl
 * @Author Chris
 * @Date 2020/12/27 16:37
 */
@Service
public class AssignedServiceImpl implements AssignedService {

    private static final Logger logger = LoggerFactory.getLogger(AssignedServiceImpl.class);

    /**
     * 已分配列表
     */
    @Override
    public PageResult<AssignedListResult> getAssignedList(AssignedListEnter enter) {
        return null;
    }

    /**
     * 已分配列表查看详情
     */
    @Override
    public AssignedDetailResult getAssignedDetail(IdEnter enter) {
        return null;
    }

    /**
     * 已分配列表生成PDF
     */
    @Override
    public GeneralResult generatePDF(IdEnter enter) {
        return null;
    }

}
