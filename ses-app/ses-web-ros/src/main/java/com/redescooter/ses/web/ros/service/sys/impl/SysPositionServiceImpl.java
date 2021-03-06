package com.redescooter.ses.web.ros.service.sys.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.base.Strings;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.constant.JedisConstant;
import com.redescooter.ses.api.common.enums.dept.DeptStatusEnums;
import com.redescooter.ses.api.common.vo.base.BooleanResult;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.constant.StringManaConstant;
import com.redescooter.ses.web.ros.dao.sys.PositionServiceMapper;
import com.redescooter.ses.web.ros.dm.OpeSysPosition;
import com.redescooter.ses.web.ros.dm.OpeSysStaff;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.OpeSysPositionService;
import com.redescooter.ses.web.ros.service.base.OpeSysStaffService;
import com.redescooter.ses.web.ros.service.sys.RoleService;
import com.redescooter.ses.web.ros.service.sys.StaffService;
import com.redescooter.ses.web.ros.service.sys.SysDeptService;
import com.redescooter.ses.web.ros.service.sys.SysPositionService;
import com.redescooter.ses.web.ros.utils.NumberUtil;
import com.redescooter.ses.web.ros.vo.sys.dept.DeptIdEnter;
import com.redescooter.ses.web.ros.vo.sys.position.EditPositionEnter;
import com.redescooter.ses.web.ros.vo.sys.position.PositionDetailsResult;
import com.redescooter.ses.web.ros.vo.sys.position.PositionEnter;
import com.redescooter.ses.web.ros.vo.sys.position.PositionResult;
import com.redescooter.ses.web.ros.vo.sys.position.PositionTypeResult;
import com.redescooter.ses.web.ros.vo.sys.position.SavePositionEnter;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisCluster;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
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
    private RoleService roleService;

    @Autowired
    private StaffService staffService;

    @Autowired
    private OpeSysStaffService opeSysStaffService;

    @DubboReference
    private IdAppService idAppService;

    @Autowired
    private SysDeptService sysDeptService;

    @Autowired
    private JedisCluster jedisCluster;

    /**
     * ??????????????????
     *
     * @param enter
     * @return
     */
    @Override
    public List<PositionTypeResult> selectPositionType(DeptIdEnter enter) {
        return positionServiceMapper.positionTypeList(enter);
    }

    /**
     * ????????????
     *
     * @param page
     * @return
     */
    @Override
    public PageResult<PositionResult> list(PositionEnter page) {
        Set<Long> deptIds = new HashSet<>();
        String key = JedisConstant.LOGIN_ROLE_DATA + page.getUserId();
        // ?????????????????????????????????????????????????????????????????????
        boolean flag = true;
        if (jedisCluster.exists(key)) {
            flag = false;
            Map<String, String> map = jedisCluster.hgetAll(key);
            String ids = map.get("deptIds");
            if (!Strings.isNullOrEmpty(ids)) {
                for (String s : ids.split(",")) {
                    deptIds.add(Long.parseLong(s));
                }
            }
        }
        if (NumberUtil.notNullAndGtFifty(page.getKeyWord())) {
            return PageResult.createZeroRowResult(page);
        }
        int totalRows = positionServiceMapper.listcount(page, flag ? null : deptIds, Constant.SYSTEM_ROOT);
        if (NumberUtil.eqZero(totalRows)) {
            return PageResult.createZeroRowResult(page);
        }
        List<PositionResult> list = positionServiceMapper.list(page, flag ? null : deptIds, Constant.SYSTEM_ROOT);

        //????????????????????????
        List<Long> positionIds = list.stream().map(PositionResult::getId).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(positionIds)) {
            QueryWrapper<OpeSysStaff> qw = new QueryWrapper<>();
            qw.in(OpeSysStaff.COL_POSITION_ID, positionIds);
            List<OpeSysStaff> staffs = opeSysStaffService.list(qw);
            if (CollectionUtils.isNotEmpty(staffs)) {
                Map<Long, List<OpeSysStaff>> map = staffs.stream().collect(Collectors.groupingBy(OpeSysStaff::getPositionId));
                for (Long positionId : map.keySet()) {
                    for (PositionResult result : list) {
                        if (Objects.deepEquals(result.getId(), positionId)) {
                            result.setPositionPersonnel(map.get(positionId).size());
                        }
                    }


                }
            }
        }
        return PageResult.create(page, totalRows, list);
    }

    /**
     * ????????????
     *
     * @param enter
     * @return
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult positionEdit(EditPositionEnter enter) {
        if (StringManaConstant.entityIsNotNull(enter.getDeptId())) {
            sysDeptService.checkDeptStatus(enter.getDeptId(), false);
        }
        OpeSysPosition byId = opeSysPositionService.getById(enter.getId());
        if (StringManaConstant.entityIsNull(byId)) {
            throw new SesWebRosException(ExceptionCodeEnums.POSITION_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.POSITION_IS_NOT_EXIST.getMessage());
        }
        // ?????????????????????????????????
        checkPositionName(enter.getId(), enter.getPositionName(), enter.getDeptId());
        if (byId.getPositionStatus().equals(DeptStatusEnums.COMPANY.getValue()) && enter.getPositionStatus().equals(DeptStatusEnums.DEPARTMENT.getValue())) {
            //????????????????????????
            List<Long> list = new ArrayList<>();
            list.add(byId.getId());
            roleService.disableRole(list);
        }
        OpeSysPosition position = new OpeSysPosition();
        position.setId(enter.getId());
        position.setDeptId(enter.getDeptId());
        position.setSort(enter.getSort());
        position.setPositionName(enter.getPositionName());
        position.setPositionStatus(enter.getPositionStatus());
        position.setDr(Constant.DR_FALSE);
        position.setUpdatedBy(enter.getUserId());
        position.setUpdatedTime(new Date());
        opeSysPositionService.updateById(position);
        return new GeneralResult(enter.getRequestId());
    }

    @Override
    public PositionDetailsResult positionDetails(IdEnter enter) {
        OpeSysPosition position = opeSysPositionService.getById(enter.getId());
        if (StringManaConstant.entityIsNull(position)) {
            throw new SesWebRosException(ExceptionCodeEnums.POSITION_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.POSITION_IS_NOT_EXIST.getMessage());
        }
        PositionDetailsResult positionDetailsResult = positionServiceMapper.positionDetails(position.getId());
        positionDetailsResult.setPositionPersonnel(staffService.deptStaffCount(position.getId(), 2));
        return positionDetailsResult;
    }

    /**
     * ????????????
     *
     * @param enter
     * @return
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult save(SavePositionEnter enter) {
        // ????????????????????????????????????????????????
        checkPositionName(null, enter.getPositionName(), enter.getDeptId());
        //?????????????????????????????????
        sysDeptService.checkDeptStatus(enter.getDeptId(), false);
        OpeSysPosition position = new OpeSysPosition();
        BeanUtils.copyProperties(enter, position);
        position.setId(idAppService.getId(SequenceName.OPE_SYS_POSITION));
        position.setDr(Constant.DR_FALSE);
        position.setPositionCode(createCode());
        position.setCreatedBy(enter.getUserId());
        position.setCreatedTime(new Date());
        position.setUpdatedBy(enter.getUserId());
        position.setUpdatedTime(new Date());
        opeSysPositionService.save(position);
        try {
            // todo ??????????????????????????????????????????????????????  ???????????????????????????
            staffService.inintUserMsg(enter.getUserId());
        } catch (Exception e) {
        }
        return new GeneralResult(enter.getRequestId());
    }


    // ????????????????????????????????????????????????
    void checkPositionName(Long id, String positionName, Long deptId) {
        QueryWrapper<OpeSysPosition> qw = new QueryWrapper<>();
        qw.eq(OpeSysPosition.COL_POSITION_NAME, positionName);
//        qw.eq(OpeSysPosition.COL_DEPT_ID,deptId);
        if (StringManaConstant.entityIsNotNull(id)) {
            qw.ne(OpeSysPosition.COL_ID, id);
        }
        int count = opeSysPositionService.count(qw);
        if (0 < count) {
            throw new SesWebRosException(ExceptionCodeEnums.SAVE_DEPT_POSITION_NAME_NOT_REPEAT.getCode(), ExceptionCodeEnums.SAVE_DEPT_POSITION_NAME_NOT_REPEAT.getMessage());
        }
    }


    @Override
    public BooleanResult deletePositionSelect(IdEnter enter) {
        Integer count = staffService.deptStaffCount(enter.getId(), 2);
        BooleanResult booleanResult = new BooleanResult();
        if (0 < count) {
            booleanResult.setSuccess(false);
        } else {
            booleanResult.setSuccess(true);
        }
        return booleanResult;
    }

    @Override
    public GeneralResult deletePosition(IdEnter enter) {
        Integer count = staffService.deptStaffCount(enter.getId(), 2);
        if (0 < count) {
            throw new SesWebRosException(ExceptionCodeEnums.UNBUNDLING_OF_EMPLOYEES.getCode(), ExceptionCodeEnums.UNBUNDLING_OF_EMPLOYEES.getMessage());
        }
        opeSysPositionService.removeById(enter.getId());
        return new GeneralResult(enter.getRequestId());
    }

    // ?????????????????????  ??????????????????
    public String createCode() {
        String positionCode = "P0" + new Random().nextInt(99999);
        QueryWrapper<OpeSysPosition> qw = new QueryWrapper<>();
        qw.eq(OpeSysPosition.COL_POSITION_CODE, positionCode);
        int count = opeSysPositionService.count(qw);
        if (0 < count) {
            createCode();
        }
        return positionCode;
    }


    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public void delePositionByDeptId(Long deptId) {
        QueryWrapper<OpeSysPosition> qw = new QueryWrapper<>();
        qw.eq(OpeSysPosition.COL_DEPT_ID, deptId);
        // ?????????????????????????????????
        List<OpeSysPosition> positionList = opeSysPositionService.list(qw);
        if (CollectionUtils.isNotEmpty(positionList)) {
            // ????????????????????????id
            List<Long> deleIds = new ArrayList<>();
            List<Long> positionIds = positionList.stream().map(OpeSysPosition::getId).collect(Collectors.toList());
            // ????????????????????????
            QueryWrapper<OpeSysStaff> staffWrapper = new QueryWrapper<>();
            staffWrapper.in(OpeSysStaff.COL_POSITION_ID, positionIds);
            List<OpeSysStaff> staffList = opeSysStaffService.list(staffWrapper);
            if (CollectionUtils.isEmpty(staffList)) {
                // ????????????????????? ???????????? ????????????
                deleIds.addAll(positionIds);
            } else {
                // ?????????????????? ???????????????????????????
                Map<Long, List<OpeSysStaff>> map = staffList.stream().collect(Collectors.groupingBy(OpeSysStaff::getPositionId));
                for (Long id : positionIds) {
                    if (!map.keySet().contains(id)) {
                        // ??????map???????????????????????????????????????
                        deleIds.add(id);
                    }
                }
            }
            if (CollectionUtils.isNotEmpty(deleIds)) {
                opeSysPositionService.removeByIds(deleIds);
                // ??????????????????  ??????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
                roleService.deleRoleByPosIds(deleIds);
            }
        }

    }
}
