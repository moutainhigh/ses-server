package com.redescooter.ses.web.ros.service.sys.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.enums.dept.DeptLevelEnums;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.dao.sys.DeptRelationServiceMapper;
import com.redescooter.ses.web.ros.dao.sys.DeptServiceMapper;
import com.redescooter.ses.web.ros.dm.OpeSysDept;
import com.redescooter.ses.web.ros.dm.OpeSysDeptRelation;
import com.redescooter.ses.web.ros.dm.OpeSysRoleDept;
import com.redescooter.ses.web.ros.dm.OpeSysUserProfile;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.OpeSysDeptService;
import com.redescooter.ses.web.ros.service.base.OpeSysRoleDeptService;
import com.redescooter.ses.web.ros.service.base.OpeSysUserService;
import com.redescooter.ses.web.ros.service.sys.SysDeptRelationService;
import com.redescooter.ses.web.ros.service.sys.SysDeptService;
import com.redescooter.ses.web.ros.utils.TreeUtil;
import com.redescooter.ses.web.ros.vo.sys.dept.EditDeptEnter;
import com.redescooter.ses.web.ros.vo.sys.dept.SaveDeptEnter;
import com.redescooter.ses.web.ros.vo.tree.DeptTreeReslt;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName SysDeptServiceImpl
 * @Author Jerry
 * @date 2020/03/11 20:21
 * @Description:
 */
@Slf4j
@Service
public class SysDeptServiceImpl implements SysDeptService {

    @Autowired
    private DeptRelationServiceMapper deptRelationServiceMapper;
    @Autowired
    private OpeSysDeptService sysDeptService;
    @Autowired
    private SysDeptRelationService sysDeptRelationService;
    @Autowired
    private OpeSysRoleDeptService opeSysRoleDeptService;
    @Autowired
    private OpeSysDeptService opeSysDeptService;
    @Autowired
    private DeptServiceMapper deptServiceMapper;

    @Autowired
    private IdAppService idAppService;

    @Transactional
    @Override
    public GeneralResult save(SaveDeptEnter enter) {
        List<OpeSysDept> sysDeptList = sysDeptService.list();

        if (CollectionUtils.isNotEmpty(sysDeptList)) {
            sysDeptList.forEach(item -> {
                //不可重复创建 根级部门
                if (item.getPId().equals(enter.getPId()) && enter.getPId().equals(Constant.DEPT_TREE_ROOT_ID)) {
                    throw new SesWebRosException(ExceptionCodeEnums.NON_REPEATABLE_CREATION_ROOT_LEVEL_DEPT.getCode(), ExceptionCodeEnums.NON_REPEATABLE_CREATION_ROOT_LEVEL_DEPT.getMessage());
                }
                // 不能创建二级部门
                if (item.getPId().equals(enter.getPId()) && item.getLevel().equals(DeptLevelEnums.DEPARTMENT)) {
                    throw new SesWebRosException(ExceptionCodeEnums.NON_CREATION_TWO_LEVEL_DEPT.getCode(), ExceptionCodeEnums.NON_CREATION_TWO_LEVEL_DEPT.getMessage());
                }
            });
        }
        OpeSysDept dept = this.buildDept(enter);
        sysDeptService.save(dept);
        sysDeptRelationService.insertDeptRelation(dept);
        return new GeneralResult(enter.getRequestId());
    }

    @Override
    public List<DeptTreeReslt> trees(GeneralEnter enter) {
        //查询部门信息
        List<DeptTreeReslt> list = deptServiceMapper.deptList();

        //查询员工信息
       List<OpeSysUserProfile> opeSysUserProfileList=deptServiceMapper.employeeListByDeptId(list.stream().map(DeptTreeReslt::getId).collect(Collectors.toList()));
        List<DeptTreeReslt> trees = new ArrayList<>();
        if (CollUtil.isNotEmpty(list)) {
            trees.addAll(trees);
        }
        return TreeUtil.build(trees, Constant.DEPT_TREE_ROOT_ID);
    }

    @Override
    public GeneralResult edit(EditDeptEnter enter) {
        //更新部门
        OpeSysDept dept = new OpeSysDept();
        BeanUtils.copyProperties(enter, dept);
        sysDeptService.updateById(dept);
        //更新部门关系
        OpeSysDeptRelation relation = new OpeSysDeptRelation();
        relation.setAncestor(dept.getPId());
        relation.setDescendant(dept.getId());
        deptRelationServiceMapper.updateDeptRelations(relation);

        return new GeneralResult(enter.getRequestId());
    }

    @Override
    public GeneralResult delete(IdEnter enter) {
        //不可删除根基部门（公司）
        List<OpeSysDept> sysDeptList = opeSysDeptService.list();
        List<Long> ids = new ArrayList<>();
        sysDeptList.forEach(item -> {
            if (enter.getId().equals(item.getId()) && item.getPId() == Constant.DEPT_TREE_ROOT_ID) {
                throw new SesWebRosException(ExceptionCodeEnums.NOT_DELETE_ROOT_LEVEL.getCode(), ExceptionCodeEnums.NOT_DELETE_ROOT_LEVEL.getMessage());
            }
            if (item.getPId().equals(enter.getId())) {
                ids.add(item.getId());
            }
        });
        if (CollectionUtils.isNotEmpty(ids)) {
            throw new SesWebRosException(ExceptionCodeEnums.REMOVE_ITSELF_CHILD_DEPT.getCode(), ExceptionCodeEnums.REMOVE_ITSELF_CHILD_DEPT.getMessage());
        }
        // 将自己放到删除集合中
        ids.add(enter.getId());
        // 查询相关联的角色
        QueryWrapper<OpeSysRoleDept> opeSysRoleDeptQueryWrapper = new QueryWrapper<>();
        opeSysRoleDeptQueryWrapper.in(OpeSysRoleDept.COL_DEPT_ID, ids);
        List<OpeSysRoleDept> sysRoleDeptList = opeSysRoleDeptService.list(opeSysRoleDeptQueryWrapper);
        if (CollectionUtils.isNotEmpty(sysRoleDeptList)) {
            throw new SesWebRosException(ExceptionCodeEnums.REMOVE_DEPT_UNDER_POSITION.getCode(), ExceptionCodeEnums.REMOVE_DEPT_UNDER_POSITION.getMessage());
        }
        // 删除部门
        opeSysDeptService.removeById(enter.getId());
        return new GeneralResult(enter.getRequestId());
    }

    @Override
    public DeptTreeReslt details(IdEnter enter) {

        OpeSysDept dept = sysDeptService.getById(enter.getId());

        DeptTreeReslt reslt = new DeptTreeReslt();

        if (reslt != null) {
            BeanUtils.copyProperties(dept, reslt);
        }

        return reslt;
    }

    @Override
    public DeptTreeReslt getDescendants(IdEnter enter) {

        List<OpeSysDept> list = sysDeptService.list();

        List<DeptTreeReslt> trees = new ArrayList<>();
        if (CollUtil.isNotEmpty(list)) {
            list.forEach(ls -> {
                DeptTreeReslt dept = new DeptTreeReslt();
                BeanUtils.copyProperties(ls, dept);
                trees.add(dept);
            });
        }

        DeptTreeReslt children = new DeptTreeReslt();
        children.setId(enter.getId());

        return TreeUtil.findChildren(children, trees);
    }

    @Override
    public DeptTreeReslt topDeptartment(IdEnter enter, String level) {

        if (StringUtils.isBlank(level)) {
            level = DeptLevelEnums.COMPANY.getValue();
        }
        DeptTreeReslt deptTreeReslt = deptServiceMapper.topDeptartment(enter, level);
        return deptTreeReslt;
    }


    private OpeSysDept buildDept(SaveDeptEnter enter) {

        OpeSysDept dept = new OpeSysDept();
        BeanUtils.copyProperties(enter, dept);
        if (dept.getPId() == null || dept.getPId() == 0) {
            dept.setPId(Constant.DEPT_TREE_ROOT_ID);
        }
        dept.setId(idAppService.getId(SequenceName.OPE_SYS_DEPT));
        dept.setDr(Constant.DR_FALSE);
        dept.setLevel((DeptLevelEnums.getEnumByValue(enter.getLevel().toString()) == null ? Integer.valueOf(DeptLevelEnums.COMPANY.getValue()) :
                Integer.valueOf(DeptLevelEnums.getEnumByValue(enter.getLevel().toString()).getValue())));
        dept.setCreatedBy(enter.getUserId());
        dept.setCreatedTime(new Date());
        dept.setUpdatedBy(enter.getUserId());
        dept.setUpdatedTime(new Date());

        return dept;
    }

}
