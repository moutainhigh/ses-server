package com.redescooter.ses.web.ros.service.restproductionorder.trace.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.web.ros.dm.OpeOpTrace;
import com.redescooter.ses.web.ros.dm.OpeSysStaff;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.OpeOpTraceService;
import com.redescooter.ses.web.ros.service.base.OpeSysStaffService;
import com.redescooter.ses.web.ros.service.restproductionorder.trace.ProductionOrderTraceService;
import com.redescooter.ses.web.ros.vo.restproductionorder.optrace.OpTraceResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.optrace.SaveOpTraceEnter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductionOrderTraceServiceImpl implements ProductionOrderTraceService {

    @Autowired
    private OpeOpTraceService opeOpTraceService;
    @Autowired
    private OpeSysStaffService opeSysStaffService;

    @Autowired
    private IdAppService idAppService;

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/10/27 14:25
     * @Param: enter
     * @Return:
     * @desc:
     * @param enter
     */
    @Override
    public List<OpTraceResult> list(GeneralEnter enter) {
        List<OpTraceResult> resultList = new ArrayList<>();
        List<OpeOpTrace> opTraceList = opeOpTraceService.list();
        if (CollectionUtils.isNotEmpty(opTraceList)) {
            List<OpeSysStaff> opeSysStaffList = opeSysStaffService.list(new LambdaQueryWrapper<OpeSysStaff>().in(OpeSysStaff::getId,
                    opTraceList.stream().map(OpeOpTrace::getCreatedBy).collect(Collectors.toSet())));
            if (CollectionUtils.isNotEmpty(opeSysStaffList)) {

                opTraceList.forEach(item -> {
                    OpTraceResult opTraceResult = new OpTraceResult();
                    BeanUtils.copyProperties(item, opTraceResult);
                    opeSysStaffList.forEach(staff -> {
                        opTraceResult.setCreatedById(staff.getId());
                        opTraceResult.setCreatedByName(staff.getFirstName() + " " + staff.getLastName());
                    });
                });
                return resultList;
            }
        }
        return resultList;
    }

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/10/27 14:48
     * @Param: enter
     * @Return: OpeOpTrace
     * @desc: 根据业务查询操作记录
     * @param enter
     */
    @Override
    public List<OpTraceResult> listByBussId(IdEnter enter) {
        List<OpTraceResult> resultList = new ArrayList<>();
        List<OpeOpTrace> opTraceList = opeOpTraceService.list(new LambdaQueryWrapper<OpeOpTrace>().eq(OpeOpTrace::getRelationId, enter.getId()));
        if (CollectionUtils.isNotEmpty(opTraceList)) {
            List<OpeSysStaff> opeSysStaffList = opeSysStaffService.list(new LambdaQueryWrapper<OpeSysStaff>().in(OpeSysStaff::getId,
                    opTraceList.stream().map(OpeOpTrace::getCreatedBy).collect(Collectors.toSet())));
            if (CollectionUtils.isNotEmpty(opeSysStaffList)) {

                opTraceList.forEach(item -> {
                    OpTraceResult opTraceResult = new OpTraceResult();
                    BeanUtils.copyProperties(item, opTraceResult);
                    opeSysStaffList.forEach(staff -> {
                        opTraceResult.setCreatedById(staff.getId());
                        opTraceResult.setCreatedByName(staff.getFirstName() + " " + staff.getLastName());
                    });
                });
                return resultList;
            }
        }
        return resultList;
    }

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/10/27 14:49
     * @Param: enter
     * @Return: OpeOpTrace
     * @desc: 详情
     * @param enter
     */
    @Override
    public OpTraceResult detail(IdEnter enter) {
        OpeOpTrace opTrace = opeOpTraceService.getById(enter.getId());
        if (opTrace == null) {
            throw new SesWebRosException(ExceptionCodeEnums.ORDER_TRACE_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_TRACE_IS_NOT_EXIST.getMessage());
        }
        OpeSysStaff opeSysStaff = opeSysStaffService.getOne(new LambdaQueryWrapper<OpeSysStaff>().eq(OpeSysStaff::getId, opTrace.getCreatedBy()));
        if (opeSysStaff == null) {
            throw new SesWebRosException(ExceptionCodeEnums.EMPLOYEE_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.EMPLOYEE_IS_NOT_EXIST.getMessage());
        }
        OpTraceResult opTraceResult = new OpTraceResult();
        BeanUtils.copyProperties(opTrace, opTraceResult);
        opTraceResult.setCreatedByName(opeSysStaff.getFirstName() + " " + opeSysStaff.getLastName());
        return opTraceResult;
    }

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/10/27 14:49
     * @Param: enter
     * @Return: OpeOpTrace
     * @desc: 保存操作记录
     * @param enter
     */
    @Transactional
    @Override
    public GeneralResult save(SaveOpTraceEnter enter) {
        OpeOpTrace saveOpeOpTrace = new OpeOpTrace();
        BeanUtils.copyProperties(enter, saveOpeOpTrace);
        if (null != enter.getId() || enter.getId() == 0) {
            saveOpeOpTrace.setId(idAppService.getId(""));
            saveOpeOpTrace.setCreatedBy(enter.getUserId());
            saveOpeOpTrace.setCreatedTime(new Date());
        }
        saveOpeOpTrace.setUpdatedBy(enter.getUserId());
        saveOpeOpTrace.setUpdatedTime(new Date());
        opeOpTraceService.saveOrUpdate(saveOpeOpTrace);
        return new GeneralResult(enter.getRequestId());
    }
}
