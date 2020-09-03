package com.redescooter.ses.web.ros.service.sys.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.dao.sys.PositionServiceMapper;
import com.redescooter.ses.web.ros.dm.OpeSysDept;
import com.redescooter.ses.web.ros.dm.OpeSysPosition;
import com.redescooter.ses.web.ros.dm.OpeSysStaff;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.OpeSysDeptService;
import com.redescooter.ses.web.ros.service.base.OpeSysPositionService;
import com.redescooter.ses.web.ros.service.base.OpeSysStaffService;
import com.redescooter.ses.web.ros.service.sys.StaffService;
import com.redescooter.ses.web.ros.service.sys.SysPositionService;
import com.redescooter.ses.web.ros.vo.sys.dept.UpdateDeptEnter;
import com.redescooter.ses.web.ros.vo.sys.position.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassNameSysPositionServiceImpl
 * @Description
 * @Author Joan
 * @Date2020/9/2 17:25
 * @Version V1.0
 **/
@Slf4j
@Service
public class SysPositionServiceImpl implements SysPositionService {
    @Autowired
    private PositionServiceMapper positionServiceMapper;
    @Autowired
    private OpeSysPositionService opeSysPositionService;
    @Autowired
    private StaffService staffService;
    @Autowired
    private OpeSysStaffService opeSysStaffService;
    @Autowired
    private IdAppService idAppService;

    /**
     * 岗位类型查询
     *
     * @param enter
     * @return
     */
    @Override
    public List<PositionTypeResult> selectPositionType(GeneralEnter enter) {
        return positionServiceMapper.positionTypeList(enter.getTenantId());
    }

    /**
     * 岗位列表
     *
     * @param page
     * @return
     */
    @Override
    public PageResult<PositionResult> list(PositionEnter page) {
        if (page.getKeyWord()!=null && page.getKeyWord().length()>50){
            return PageResult.createZeroRowResult(page);
        }
        int totalRows = positionServiceMapper.listcount(page);
        if (totalRows == 0) {
            return PageResult.createZeroRowResult(page);
        }
        List<PositionResult> list = positionServiceMapper.list(page);

        //获取岗位下的人员
        List<Long> positionIds = list.stream().map(PositionResult::getId).collect(Collectors.toList());
        QueryWrapper<OpeSysStaff> qw = new QueryWrapper<>();
        qw.in(OpeSysStaff.COL_POSITION_ID,positionIds);
        List<OpeSysStaff> staffs = opeSysStaffService.list(qw);
        if(CollectionUtils.isNotEmpty(staffs)){
            Map<Long,List<OpeSysStaff>> map = staffs.stream().collect(Collectors.groupingBy(OpeSysStaff::getPositionId));
            for (Long positionId : map.keySet()) {
                for (PositionResult result : list) {
                    if(Objects.deepEquals(result.getId(),positionId)){
                        result.setPositionPersonnel(map.get(positionId).size());
                    }
                }


            }
        }
        return PageResult.create(page, totalRows, list);
    }

    /**
     * 岗位编辑
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult positionEdit(UpdateDeptEnter enter) {
        OpeSysPosition byId = opeSysPositionService.getById(enter.getId());
        if (byId==null){
            throw new SesWebRosException(ExceptionCodeEnums.POSITION_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.POSITION_IS_NOT_EXIST.getMessage());
        }
        OpeSysPosition position = new OpeSysPosition();
        BeanUtils.copyProperties(enter,position);
        position.setDr(Constant.DR_FALSE);
        position.setUpdatedBy(enter.getUserId());
        position.setUpdatedTime(new Date());
        opeSysPositionService.updateById(position);
        return new GeneralResult(enter.getRequestId());
    }

    @Override
    public PositionDetailsResult positionDetails(IdEnter enter) {
        return null;
    }

    /**
     * 岗位列表
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult save(SavePositionEnter enter) {
      OpeSysPosition position = new OpeSysPosition();
        BeanUtils.copyProperties(enter,position);
        position.setId(idAppService.getId(SequenceName.OPE_SYS_POSITION));
        position.setDr(Constant.DR_FALSE);
        position.setCreatedBy(enter.getUserId());
        position.setCreatedTime(new Date());
        position.setUpdatedBy(enter.getUserId());
        position.setUpdatedTime(new Date());
        opeSysPositionService.save(position);
        return new GeneralResult(enter.getRequestId());
    }

    @Override
    public BooleanResult deletePositionSelect(IdEnter enter) {
        Integer count = staffService.deptStaffCount(enter.getId(), 2);
        BooleanResult booleanResult = new BooleanResult();
        if (count>0){
            booleanResult.setSuccess(true);
        }else {
            booleanResult.setSuccess(false);
        }
        return booleanResult;
    }

    @Override
    public GeneralResult deletePosition(IdEnter enter) {


        return new GeneralResult(enter.getRequestId());
    }
}
