package com.redescooter.ses.web.ros.service.sys.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.base.Strings;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.constant.JedisConstant;
import com.redescooter.ses.api.common.enums.dept.DeptStatusEnums;
import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.web.ros.constant.SequenceName;
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
import com.redescooter.ses.web.ros.vo.sys.dept.DeptIdEnter;
import com.redescooter.ses.web.ros.vo.sys.position.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisCluster;

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
    private RoleService roleService;
    @Autowired
    private StaffService staffService;
    @Autowired
    private OpeSysStaffService opeSysStaffService;
    @Autowired
    private IdAppService idAppService;
    @Autowired
    private SysDeptService sysDeptService;

    @Autowired
    private JedisCluster jedisCluster;

    /**
     * 岗位类型查询
     *
     * @param enter
     * @return
     */
    @Override
    public List<PositionTypeResult> selectPositionType(DeptIdEnter enter) {
        return positionServiceMapper.positionTypeList(enter.getTenantId(),enter.getDeptId());
    }

    /**
     * 岗位列表
     *
     * @param page
     * @return
     */
    @Override
    public PageResult<PositionResult> list(PositionEnter page) {
        Set<Long> deptIds =  new HashSet<>();
        String key = JedisConstant.LOGIN_ROLE_DATA + page.getUserId();
        // 通过这个来判断是不是管理员账号，默认为是管理员
        boolean flag = true;
        if (jedisCluster.exists(key)){
            flag = false;
            Map<String, String> map = jedisCluster.hgetAll(key);
            String ids = map.get("deptIds");
            if(!Strings.isNullOrEmpty(ids)){
                for (String s : ids.split(",")) {
                    deptIds.add(Long.parseLong(s));
                }
            }
        }
        if (page.getKeyWord() != null && page.getKeyWord().length() > 50) {
            return PageResult.createZeroRowResult(page);
        }
        int totalRows = positionServiceMapper.listcount(page,flag?null:deptIds);
        if (totalRows == 0) {
            return PageResult.createZeroRowResult(page);
        }
        List<PositionResult> list = positionServiceMapper.list(page,flag?null:deptIds);

        //获取岗位下的人员
        List<Long> positionIds = list.stream().map(PositionResult::getId).collect(Collectors.toList());
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
        return PageResult.create(page, totalRows, list);
    }

    /**
     * 岗位编辑
     *
     * @param enter
     * @return
     */
    @Override
    @Transactional
    public GeneralResult positionEdit(EditPositionEnter enter) {
        if (enter.getDeptId() != null) {
            sysDeptService.checkDeptStatus(enter.getDeptId(),false);
        }
        OpeSysPosition byId = opeSysPositionService.getById(enter.getId());
        if (byId == null) {
            throw new SesWebRosException(ExceptionCodeEnums.POSITION_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.POSITION_IS_NOT_EXIST.getMessage());
        }
        // 修改时校验名称是否重复
        checkPositionName(enter.getId(),enter.getPositionName(),enter.getDeptId());
        if (byId.getPositionStatus().equals(DeptStatusEnums.COMPANY.getValue()) && enter.getPositionStatus().equals(DeptStatusEnums.DEPARTMENT.getValue())) {
            //岗位角色员工禁用
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
        if (position == null) {
            throw new SesWebRosException(ExceptionCodeEnums.POSITION_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.POSITION_IS_NOT_EXIST.getMessage());
        }
        PositionDetailsResult positionDetailsResult = positionServiceMapper.positionDetails(position.getId());
        positionDetailsResult.setPositionPersonnel(staffService.deptStaffCount(position.getId(), 2));
        return positionDetailsResult;
    }

    /**
     * 岗位新增
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult save(SavePositionEnter enter) {
        // 校验同部门下的岗位名称是否已存在
        checkPositionName(null,enter.getPositionName(),enter.getDeptId());
        //校验上级部门是否被禁用
        sysDeptService.checkDeptStatus(enter.getDeptId(),false);
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
        return new GeneralResult(enter.getRequestId());
    }


    // 校验同部门下面的岗位名称不能重复
    void checkPositionName(Long id,String positionName,Long deptId){
        QueryWrapper<OpeSysPosition> qw = new QueryWrapper<>();
        qw.eq(OpeSysPosition.COL_POSITION_NAME,positionName);
        qw.eq(OpeSysPosition.COL_DEPT_ID,deptId);
        if(id != null){
            qw.ne(OpeSysPosition.COL_ID,id);
        }
        int count = opeSysPositionService.count(qw);
        if(count > 0){
            throw new SesWebRosException(ExceptionCodeEnums.SAVE_DEPT_POSITION_NAME_NOT_REPEAT.getCode(), ExceptionCodeEnums.SAVE_DEPT_POSITION_NAME_NOT_REPEAT.getMessage());
        }
    }



    @Override
    public BooleanResult deletePositionSelect(IdEnter enter) {
        Integer count = staffService.deptStaffCount(enter.getId(), 2);
        BooleanResult booleanResult = new BooleanResult();
        if (count > 0) {
            booleanResult.setSuccess(false);
        } else {
            booleanResult.setSuccess(true);
        }
        return booleanResult;
    }

    @Override
    public GeneralResult deletePosition(IdEnter enter) {
        Integer count = staffService.deptStaffCount(enter.getId(), 2);
        if (count > 0) {
            throw new SesWebRosException(ExceptionCodeEnums.UNBUNDLING_OF_EMPLOYEES.getCode(), ExceptionCodeEnums.UNBUNDLING_OF_EMPLOYEES.getMessage());
        }
        opeSysPositionService.removeById(enter.getId());
        return new GeneralResult(enter.getRequestId());
    }

    // 新增角色的时候  生成角色编码
    public String createCode() {
        String positionCode = "P0" + new Random().nextInt(99999);
        QueryWrapper<OpeSysPosition> qw = new QueryWrapper<>();
        qw.eq(OpeSysPosition.COL_POSITION_CODE, positionCode);
        int count = opeSysPositionService.count(qw);
        if (count > 0) {
            createCode();
        }
        return positionCode;
    }


    @Override
    @Transactional
    public void delePositionByDeptId(Long deptId) {
        QueryWrapper<OpeSysPosition> qw = new QueryWrapper<>();
        qw.eq(OpeSysPosition.COL_DEPT_ID,deptId);
        // 找到部门下面有哪些岗位
        List<OpeSysPosition> positionList = opeSysPositionService.list(qw);
        if(CollectionUtils.isNotEmpty(positionList)){
            // 存放要删除的岗位id
            List<Long> deleIds = new ArrayList<>();
            List<Long> positionIds = positionList.stream().map(OpeSysPosition::getId).collect(Collectors.toList());
             // 找到部门下面的人
            QueryWrapper<OpeSysStaff> staffWrapper = new QueryWrapper<>();
            staffWrapper.in(OpeSysStaff.COL_POSITION_ID,positionIds);
            List<OpeSysStaff> staffList = opeSysStaffService.list(staffWrapper);
            if(CollectionUtils.isEmpty(staffList)){
                // 岗位下面没有人 直接删除 不多比比
                deleIds.addAll(positionIds);
            }else {
                // 岗位下面的人 按照岗位来进行分组
                Map<Long, List<OpeSysStaff>> map = staffList.stream().collect(Collectors.groupingBy(OpeSysStaff::getPositionId));
                for (Long id : positionIds) {
                    if(!map.keySet().contains(id)){
                        // 如果map里面不包括当前岗位，就删除
                        deleIds.add(id);
                    }
                }
            }
            if(CollectionUtils.isNotEmpty(deleIds)){
                opeSysPositionService.removeByIds(deleIds);
                // 岗位删除之后  再把岗位下面的角色删除了（因为岗位和角色是级联关系，所以岗位下面没有人，角色下面必然没有人）
                roleService.deleRoleByPosIds(deleIds);
            }
        }

    }
}
