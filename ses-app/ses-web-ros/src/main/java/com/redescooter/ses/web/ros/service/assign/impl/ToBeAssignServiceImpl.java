package com.redescooter.ses.web.ros.service.assign.impl;

import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.base.StringEnter;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.utils.SesStringUtils;
import com.redescooter.ses.web.ros.dao.assign.OpeCarDistributeExMapper;
import com.redescooter.ses.web.ros.dao.assign.OpeCarDistributeMapper;
import com.redescooter.ses.web.ros.dao.assign.OpeCarDistributeNodeMapper;
import com.redescooter.ses.web.ros.service.assign.ToBeAssignService;
import com.redescooter.ses.web.ros.vo.assign.tobe.enter.ToBeAssignLicensePlateNextEnter;
import com.redescooter.ses.web.ros.vo.assign.tobe.enter.ToBeAssignListEnter;
import com.redescooter.ses.web.ros.vo.assign.tobe.enter.ToBeAssignSeatNextEnter;
import com.redescooter.ses.web.ros.vo.assign.tobe.enter.ToBeAssignSubmitEnter;
import com.redescooter.ses.web.ros.vo.assign.tobe.result.ToBeAssignColorResult;
import com.redescooter.ses.web.ros.vo.assign.tobe.result.ToBeAssignDetailResult;
import com.redescooter.ses.web.ros.vo.assign.tobe.result.ToBeAssignListResult;
import com.redescooter.ses.web.ros.vo.assign.tobe.result.ToBeAssignNextStopResult;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Description 车辆待分配ServiceImpl
 * @Author Chris
 * @Date 2020/12/27 14:50
 */
@Service
public class ToBeAssignServiceImpl implements ToBeAssignService {

    private static final Logger logger = LoggerFactory.getLogger(ToBeAssignServiceImpl.class);

    @Autowired
    private OpeCarDistributeMapper opeCarDistributeMapper;

    @Autowired
    private OpeCarDistributeNodeMapper opeCarDistributeNodeMapper;

    @Autowired
    private OpeCarDistributeExMapper opeCarDistributeExMapper;

    @Reference
    private IdAppService idAppService;

    /**
     * 待分配列表
     */
    @Override
    public PageResult<ToBeAssignListResult> getToBeAssignList(ToBeAssignListEnter enter) {
        logger.info("待分配列表的入参是:[{}]", enter);
        SesStringUtils.objStringTrim(enter);

        int count = opeCarDistributeExMapper.getToBeAssignListCount(enter);
        if (count == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        List<ToBeAssignListResult> list = opeCarDistributeExMapper.getToBeAssignList(enter);
        return PageResult.create(enter, count, list);
    }

    /**
     * 待分配列表点击分配带出数据
     */
    @Override
    public ToBeAssignDetailResult getToBeAssignDetail(IdEnter enter) {
        return null;
    }

    /**
     * 填写完座位数点击下一步
     */
    @Override
    public ToBeAssignNextStopResult getSeatNext(ToBeAssignSeatNextEnter enter) {
        return null;
    }

    /**
     * 填写完车牌点击下一步
     */
    @Override
    public ToBeAssignNextStopResult getLicensePlateNext(ToBeAssignLicensePlateNextEnter enter) {
        return null;
    }

    /**
     * 根据R.SN获得颜色
     */
    @Override
    public ToBeAssignColorResult getColorByRSN(StringEnter enter) {
        return null;
    }

    /**
     * 填写完R.SN并点击提交
     */
    @Override
    public ToBeAssignNextStopResult submit(ToBeAssignSubmitEnter enter) {
        return null;
    }

}
