package com.redescooter.ses.web.ros.service.assign.impl;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.tool.utils.SesStringUtils;
import com.redescooter.ses.web.ros.dao.assign.OpeCarDistributeExMapper;
import com.redescooter.ses.web.ros.dao.assign.OpeCarDistributeMapper;
import com.redescooter.ses.web.ros.dao.assign.OpeCarDistributeNodeMapper;
import com.redescooter.ses.web.ros.service.assign.AssignedService;
import com.redescooter.ses.web.ros.vo.assign.done.enter.AssignedListEnter;
import com.redescooter.ses.web.ros.vo.assign.done.result.AssignedDetailResult;
import com.redescooter.ses.web.ros.vo.assign.done.result.AssignedListResult;
import org.apache.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Description 车辆已分配ServiceImpl
 * @Author Chris
 * @Date 2020/12/27 16:37
 */
@Service
public class AssignedServiceImpl implements AssignedService {

    private static final Logger logger = LoggerFactory.getLogger(AssignedServiceImpl.class);

    @Autowired
    private OpeCarDistributeMapper opeCarDistributeMapper;

    @Autowired
    private OpeCarDistributeNodeMapper opeCarDistributeNodeMapper;

    @Autowired
    private OpeCarDistributeExMapper opeCarDistributeExMapper;

    /**
     * 已分配列表
     */
    @Override
    public PageResult<AssignedListResult> getAssignedList(AssignedListEnter enter) {
        logger.info("已分配列表的入参是:[{}]", enter);
        SesStringUtils.objStringTrim(enter);

        int count = opeCarDistributeExMapper.getAssignedListCount(enter);
        if (count == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        List<AssignedListResult> list = opeCarDistributeExMapper.getAssignedList(enter);
        return PageResult.create(enter, count, list);
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
