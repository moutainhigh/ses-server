package com.redescooter.ses.web.ros.service.sys.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.base.Strings;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.constant.JedisConstant;
import com.redescooter.ses.api.common.enums.dept.DeptLevelEnums;
import com.redescooter.ses.api.common.enums.dept.DeptStatusEnums;
import com.redescooter.ses.api.common.vo.base.BooleanResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.utils.SesStringUtils;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.constant.StringManaConstant;
import com.redescooter.ses.web.ros.dao.sys.DeptRelationServiceMapper;
import com.redescooter.ses.web.ros.dao.sys.DeptServiceMapper;
import com.redescooter.ses.web.ros.dm.OpeSysDept;
import com.redescooter.ses.web.ros.dm.OpeSysDeptRelation;
import com.redescooter.ses.web.ros.dm.OpeSysPosition;
import com.redescooter.ses.web.ros.dm.OpeSysRoleDept;
import com.redescooter.ses.web.ros.dm.OpeSysStaff;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.OpeSysDeptService;
import com.redescooter.ses.web.ros.service.base.OpeSysPositionService;
import com.redescooter.ses.web.ros.service.base.OpeSysRoleDeptService;
import com.redescooter.ses.web.ros.service.base.OpeSysStaffService;
import com.redescooter.ses.web.ros.service.base.OpeSysUserProfileService;
import com.redescooter.ses.web.ros.service.sys.RoleService;
import com.redescooter.ses.web.ros.service.sys.StaffService;
import com.redescooter.ses.web.ros.service.sys.SysDeptRelationService;
import com.redescooter.ses.web.ros.service.sys.SysDeptService;
import com.redescooter.ses.web.ros.service.sys.SysPositionService;
import com.redescooter.ses.web.ros.utils.TreeUtil;
import com.redescooter.ses.web.ros.vo.sys.dept.AddDeptEnter;
import com.redescooter.ses.web.ros.vo.sys.dept.DeptDetailsResult;
import com.redescooter.ses.web.ros.vo.sys.dept.DeptListEnter;
import com.redescooter.ses.web.ros.vo.sys.dept.DeptTypeResult;
import com.redescooter.ses.web.ros.vo.sys.dept.EditDeptEnter;
import com.redescooter.ses.web.ros.vo.sys.dept.EmployeeListByDeptIdEnter;
import com.redescooter.ses.web.ros.vo.sys.dept.EmployeeProfileResult;
import com.redescooter.ses.web.ros.vo.sys.dept.PrincipalResult;
import com.redescooter.ses.web.ros.vo.sys.dept.PrincipalsEnter;
import com.redescooter.ses.web.ros.vo.sys.dept.SaveDeptEnter;
import com.redescooter.ses.web.ros.vo.sys.dept.SelectDeptResult;
import com.redescooter.ses.web.ros.vo.sys.dept.TypeListEnter;
import com.redescooter.ses.web.ros.vo.sys.dept.UpdateDeptEnter;
import com.redescooter.ses.web.ros.vo.tree.DeptTreeListResult;
import com.redescooter.ses.web.ros.vo.tree.DeptTreeReslt;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisCluster;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
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
    private RoleService roleService;
    @Autowired
    private OpeSysPositionService opeSysPositionService;
    @Autowired
    private OpeSysUserProfileService opeSysUserProfileService;
    @Autowired
    private SysDeptRelationService sysDeptRelationService;
    @Autowired
    private OpeSysRoleDeptService opeSysRoleDeptService;
    @Autowired
    private OpeSysDeptService opeSysDeptService;
    @Autowired
    private DeptServiceMapper deptServiceMapper;
    @DubboReference
    private IdAppService idAppService;
    @Autowired
    private OpeSysStaffService opeSysStaffService;
    @Autowired
    private StaffService taffService;

    @Autowired
    private SysPositionService sysPositionService;

    @Autowired
    private JedisCluster jedisCluster;

    @Autowired
    private StaffService staffService;

    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult save(SaveDeptEnter enter) {
        //????????????
        List<OpeSysDept> saveDeptList = new ArrayList<>();

        //SaveDeptEnter??????????????????
        enter = SesStringUtils.objStringTrim(enter);
        List<OpeSysDept> deptList = sysDeptService.list();

        if (CollectionUtils.isNotEmpty(deptList)) {
            OpeSysDept sortDept = null;
            int maxSort = 0;
            for (OpeSysDept item : deptList) {
                //?????????????????? ????????????
                if (item.getPId().equals(enter.getPId()) && enter.getPId().equals(Constant.DEPT_TREE_ROOT_ID)) {
                    throw new SesWebRosException(ExceptionCodeEnums.NON_REPEATABLE_CREATION_ROOT_LEVEL_DEPT.getCode(), ExceptionCodeEnums.NON_REPEATABLE_CREATION_ROOT_LEVEL_DEPT.getMessage());
                }
                // ????????????????????????
                if (item.getPId().equals(enter.getPId()) && item.getLevel().equals(DeptLevelEnums.DEPARTMENT)) {
                    throw new SesWebRosException(ExceptionCodeEnums.NON_CREATION_TWO_LEVEL_DEPT.getCode(), ExceptionCodeEnums.NON_CREATION_TWO_LEVEL_DEPT.getMessage());
                }

                //????????????????????????
                if (item.getSort().equals(enter.getSort()) && enter.getPId().equals(item.getPId())) {
                    sortDept = item;
                }
                if (maxSort < item.getSort()) {
                    maxSort = item.getSort();
                }
            }
            if (StringManaConstant.entityIsNotNull(sortDept) && 0 != maxSort) {
                //????????????
                sortDept.setSort(maxSort + 1);
                saveDeptList.add(sortDept);
            }
        }
        OpeSysDept dept = this.buildDept(enter);
        saveDeptList.add(dept);
        sysDeptService.saveOrUpdateBatch(saveDeptList);
        sysDeptRelationService.insertDeptRelation(dept);
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * ????????????
     *
     * @param enter
     * @return
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult addSave(AddDeptEnter enter) {
        //?????????????????????????????????
        if (StringManaConstant.entityIsNotNull(enter.getPid()) && -1 != enter.getPid()) {
            checkDeptStatus(enter.getPid(), true);
        }

        List<OpeSysDept> saveDeptList = new ArrayList<>();
        //SaveDeptEnter??????????????????
        enter = SesStringUtils.objStringTrim(enter);
        if (StringUtils.isEmpty(enter.getName())) {
            throw new SesWebRosException(ExceptionCodeEnums.DEPT_NAME_IS_EMPTY.getCode(), ExceptionCodeEnums.DEPT_NAME_IS_EMPTY.getMessage());
        }
        if (StringManaConstant.entityIsNull(enter.getPid())) {
            throw new SesWebRosException(ExceptionCodeEnums.SUPERIOR_DEPT_IS_EMPTY.getCode(), ExceptionCodeEnums.SUPERIOR_DEPT_IS_EMPTY.getMessage());

        }
        // ??????????????????????????????(?????????????????????)
        checkDeptName(enter.getName(), enter.getPid(), null);
        OpeSysDept dept = this.addDept(enter);
        sysDeptService.save(dept);
        try {
            // todo ??????????????????????????????????????????????????????  ???????????????????????????
            staffService.inintUserMsg(enter.getUserId());
        } catch (Exception e) {
        }
        return new GeneralResult(enter.getRequestId());
    }


    // ??????????????????????????????(?????????????????????????????????)???
    // ??????  ??????????????????????????????????????????????????????
    public void checkDeptName(String deptName, Long parentId, Long deptId) {
        QueryWrapper<OpeSysDept> qw = new QueryWrapper<>();
        qw.eq(OpeSysDept.COL_NAME, deptName);
        qw.eq(OpeSysDept.COL_P_ID, parentId);
        if (StringManaConstant.entityIsNotNull(deptId)) {
            // ???????????????
            qw.ne(OpeSysDept.COL_ID, deptId);
        }
        int count = opeSysDeptService.count(qw);
        if (0 < count) {
            throw new SesWebRosException(ExceptionCodeEnums.DEPT_NAME_EXIST.getCode(), ExceptionCodeEnums.DEPT_NAME_EXIST.getMessage());
        }
    }


    /**
     * ?????????
     *
     * @param enter
     * @return
     */
    @Override
    public List<DeptTreeListResult> deptTrees(DeptListEnter enter) {
        Set<Long> deptIds = new HashSet<>();
        String key = JedisConstant.LOGIN_ROLE_DATA + enter.getUserId();
        // ?????????????????????????????????????????????????????????????????????
        boolean flag = true;
        if (jedisCluster.exists(key)) {
            flag = false;
            Map<String, String> map = jedisCluster.hgetAll(key);
            String ids = map.get("deptIds");
            if (!Strings.isNullOrEmpty(ids)) {
                for (String s : ids.split(",")) {
                    deptIds.addAll(deptServiceMapper.getParentIds(Long.valueOf(s)));
                }
            } else {
                // ????????????????????????????????? ?????????????????????????????????
                QueryWrapper<OpeSysDept> qw = new QueryWrapper<>();
                qw.eq(OpeSysDept.COL_CREATED_BY, enter.getUserId());
                List<OpeSysDept> depts = sysDeptService.list(qw);
                if (CollectionUtils.isNotEmpty(depts)) {
                    List<Long> idList = depts.stream().map(OpeSysDept::getId).collect(Collectors.toList());
                    for (Long id : idList) {
                        deptIds.addAll(deptServiceMapper.getParentIds(id));
                    }
                }
            }
        }
        List<DeptTreeListResult> deptTreeReslts = deptServiceMapper.getDeptList(enter, flag ? null : deptIds);
        return TreeUtil.build(deptTreeReslts, Constant.DEPT_TREE_ROOT_ID);
    }


    @Override
    public List<DeptTreeListResult> saveDeptSelectParent(DeptListEnter enter) {
        List<DeptTreeListResult> deptTreeReslts = deptServiceMapper.saveDeptSelectParent(enter);
        return TreeUtil.build(deptTreeReslts, Constant.DEPT_TREE_ROOT_ID);
    }

    /**
     * ????????????
     *
     * @param enter
     * @return
     */
    @Override
    public List<DeptTreeReslt> trees(GeneralEnter enter) {
        List<DeptTreeReslt> deptTreeReslts = deptList(enter);
        //??????sort??????
        Collections.sort(deptTreeReslts, new Comparator<DeptTreeReslt>() {
            @Override
            public int compare(DeptTreeReslt o1, DeptTreeReslt o2) {
                return o1.getSort().compareTo(o2.getSort());
            }
        });
        Collections.sort(deptTreeReslts, Comparator.comparing(DeptTreeReslt::getSort));
        return TreeUtil.build(deptTreeReslts, Constant.DEPT_TREE_ROOT_ID);
    }

    /**
     * ????????????
     *
     * @param enter
     * @return
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult deleteDept(IdEnter enter) {
        List<OpeSysDept> list = sysDeptService.list(new QueryWrapper<OpeSysDept>().eq(OpeSysDept.COL_P_ID, enter.getId()));
        if (CollectionUtils.isNotEmpty(list)) {
            throw new SesWebRosException(ExceptionCodeEnums.PLEASE_UNTIE_THE_SUBDEPT.getCode(), ExceptionCodeEnums.PLEASE_UNTIE_THE_SUBDEPT.getMessage());
        }
        // ??????????????????????????????
        Integer count = taffService.deptStaffCount(enter.getId(), 1);
        if (0 < count) {
            // ??????????????????  ??????????????????
            throw new SesWebRosException(ExceptionCodeEnums.UNBUNDLING_OF_EMPLOYEES.getCode(), ExceptionCodeEnums.UNBUNDLING_OF_EMPLOYEES.getMessage());
        }
        opeSysDeptService.removeById(enter.getId());
        // 2020 9 10??????  ?????????????????????  ??????????????????????????????????????????  ????????????????????????????????????  ????????????????????????????????????????????????
        sysPositionService.delePositionByDeptId(enter.getId());
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * ???????????? ????????????
     *
     * @param enter
     * @return
     */
    @Override
    public List<DeptTreeReslt> deptList(GeneralEnter enter) {
        //??????????????????
        List<DeptTreeReslt> list = deptServiceMapper.deptList();

        //??????????????????
        List<EmployeeProfileResult> employeeList = deptServiceMapper.employeeList(null, null);
        if (CollectionUtils.isNotEmpty(list)) {
            if (CollectionUtils.isNotEmpty(employeeList)) {
                list.forEach(item -> {
                    for (EmployeeProfileResult user : employeeList) {
                        if (item.getId() == user.getDeptId()) {
                            //????????????????????????4??? ???????????? ????????????
                            if (StringUtils.isNotEmpty(item.getEmployeePictures())) {
                                if (item.getEmployeePictures().split(",").length > 5) {
                                    break;
                                }
                                item.setEmployeePictures(!Strings.isNullOrEmpty(item.getEmployeePictures()) ? item.getEmployeePictures() + "," : item.getEmployeePictures());
                                item.setEmployeePictures(new StringBuilder(item.getEmployeePictures()).append(user.getEmployeePicture()).toString());
                            }
                        }
                    }
                });
            }
            // ????????????????????????????????????????????????????????????????????????????????????????????????
            deptEmployeeCount(list);
        }
        return list;
    }

    public List<DeptTreeReslt> deptEmployeeCount(List<DeptTreeReslt> list) {
        for (DeptTreeReslt treeReslt : list) {
            treeReslt.setEmployeeCount(recurCount(treeReslt.getEmployeeCount(), treeReslt, list));
        }
        return list;
    }


    public int recurCount(int count, DeptTreeReslt deptTreeReslt, List<DeptTreeReslt> list) {
        List<DeptTreeReslt> parent = new ArrayList<>();
        List<DeptTreeReslt> child = new ArrayList<>();
        for (DeptTreeReslt treeReslt : list) {
            if (treeReslt.getPId() == deptTreeReslt.getId()) {
                parent.add(treeReslt);
            } else {
                child.add(treeReslt);
            }
        }
        // ????????????????????????????????????
        if (CollectionUtils.isNotEmpty(parent)) {
            // ??????????????????????????????????????????
            count = count + parent.stream().mapToInt(DeptTreeReslt::getEmployeeCount).sum();
        }
        for (DeptTreeReslt reslt : parent) {
            count = recurCount(count, reslt, child);
        }
        return count;
    }


    /**
     * ????????????
     *
     * @param enter
     * @return
     */
    @Override
    public List<EmployeeProfileResult> employeeListByDeptId(EmployeeListByDeptIdEnter enter) {
        OpeSysDept dept = sysDeptService.getById(enter.getId());
        if (StringManaConstant.entityIsNull(dept)) {
            throw new SesWebRosException(ExceptionCodeEnums.DEPT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.DEPT_IS_NOT_EXIST.getMessage());
        }

        //?????? ???????????????
        List<Long> deptIds = new ArrayList<>();
        deptIds.add(enter.getId());
        // ?????????????????????????????????????????????id
        deptIds.addAll(deptServiceMapper.getChildDeptIds(enter.getId()));
        return deptServiceMapper.employeeList(deptIds, enter.getKeyword());
    }

    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult edit(EditDeptEnter enter) {
        List<OpeSysDept> updateDeptList = new ArrayList<>();
        OpeSysDept checkDept = null;

        //??????????????????
        List<OpeSysDept> sysDeptList = sysDeptService.list();

        if (CollectionUtils.isNotEmpty(sysDeptList)) {

            for (OpeSysDept dept : sysDeptList) {
                if (dept.getId().equals(enter.getId())) {
                    checkDept = dept;
                    break;
                }
            }

            if (StringManaConstant.entityIsNull(checkDept)) {
                throw new SesWebRosException(ExceptionCodeEnums.DEPT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.DEPT_IS_NOT_EXIST.getMessage());
            }

            OpeSysDept sortDept = null;
            int maxSort = 0;
            for (OpeSysDept item : sysDeptList) {

                //????????????????????????????????????
                if (item.getSort().equals(enter.getSort()) && checkDept.getPId().equals(item.getPId())) {
                    sortDept = item;
                }
                if (maxSort < item.getSort()) {
                    maxSort = item.getSort();
                }
            }
            //????????????
            if (0 != maxSort && StringManaConstant.entityIsNotNull(sortDept)) {
                sortDept.setSort(checkDept.getSort());
                updateDeptList.add(sortDept);
            }
        }

        //????????????
        checkDept.setName(enter.getName());
        checkDept.setSort(enter.getSort());
        checkDept.setUpdatedBy(enter.getUserId());
        checkDept.setUpdatedTime(new Date());
        if (StringManaConstant.entityIsNotNull(enter.getPrincipal()) && 0 != enter.getPrincipal()) {
            checkDept.setPrincipal(enter.getPrincipal());
        }
        updateDeptList.add(checkDept);
        sysDeptService.updateBatch(updateDeptList);
        //??????????????????
        OpeSysDeptRelation relation = new OpeSysDeptRelation();
        relation.setAncestor(checkDept.getPId());
        relation.setDescendant(checkDept.getId());
        deptRelationServiceMapper.updateDeptRelations(relation);

        return new GeneralResult(enter.getRequestId());
    }

    /**
     * ??????????????????
     *
     * @param enter
     * @return
     */
    @Override
    public List<DeptTypeResult> selectDeptType(TypeListEnter enter) {
        return deptServiceMapper.deptType(enter);
    }

    /**
     * ??????????????????
     *
     * @param enter
     * @return
     */
    @Override
    public SelectDeptResult selectEditDept(IdEnter enter) {
        return deptServiceMapper.selectEditDept(enter.getId());
    }

    /**
     * ????????????
     *
     * @param enter
     * @return
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult editDept(UpdateDeptEnter enter) {
        //?????????????????????????????????
        if (StringManaConstant.entityIsNotNull(enter.getPid()) && -1 != enter.getPid()) {
            checkDeptStatus(enter.getPid(), true);
        }
        // ?????????????????????  ?????????????????????????????????????????????????????????
        if (enter.getId().equals(enter.getPid())) {
            // ???????????????????????????????????????
            throw new SesWebRosException(ExceptionCodeEnums.PARENT_DEPT_ERROR.getCode(), ExceptionCodeEnums.PARENT_DEPT_ERROR.getMessage());
        }
        // ??????????????????????????????(?????????????????????)
        checkDeptName(enter.getName(), enter.getPid(), enter.getId());
        SelectDeptResult deptResult = deptServiceMapper.selectEditDept(enter.getId());
        OpeSysDept opeSysDept = new OpeSysDept();
        List<Long> ids = new ArrayList<>();
        if (StringManaConstant.entityIsNull(deptResult)) {
            throw new SesWebRosException(ExceptionCodeEnums.DEPT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.DEPT_IS_NOT_EXIST.getMessage());
        }
        if (deptResult.getPId().equals(Constant.DEPT_TREE_ROOT_ID) && 2 == enter.getDeptStatus()) {
            // ????????????????????? ????????????
            throw new SesWebRosException(ExceptionCodeEnums.TOP_DEPT_IS_NOT_DISABLE.getCode(), ExceptionCodeEnums.TOP_DEPT_IS_NOT_DISABLE.getMessage());
        }
        if (deptResult.getDeptStatus().equals(DeptStatusEnums.COMPANY.getValue()) && enter.getDeptStatus().equals(DeptStatusEnums.DEPARTMENT.getValue())) {
            // ????????????  ????????????????????????????????????
            List<OpeSysDept> childDepts = deptServiceMapper.getChildDept(deptResult.getId());
            if (CollectionUtils.isNotEmpty(childDepts)) {
                List<OpeSysDept> depts = new ArrayList<>();
                for (OpeSysDept dept : childDepts) {
                    dept.setDeptStatus(DeptStatusEnums.DEPARTMENT.getValue());
                    depts.add(dept);
                    opeSysDeptService.updateBatch(depts);
                    List<OpeSysPosition> list = opeSysPositionService.list(new QueryWrapper<OpeSysPosition>().in(OpeSysPosition.COL_DEPT_ID, depts.stream().map(OpeSysDept::getId).collect(Collectors.toList())));
                    if (CollectionUtils.isNotEmpty(list)) {
                        //????????????
                        list.stream().forEach(opeSysPosition -> opeSysPosition.setPositionStatus(Integer.valueOf(DeptStatusEnums.DEPARTMENT.getValue())));
                        opeSysPositionService.updateBatch(list);
                        //????????????????????????
                        ids = list.stream().map(OpeSysPosition::getId).collect(Collectors.toList());
                        roleService.disableRole(ids);
                    }
                }
                // ????????????????????????
                List<Long> deptChilds = childDepts.stream().map(OpeSysDept::getId).collect(Collectors.toList());
                deptChilds.add(deptResult.getId());
                taffService.disAbleStaff(deptChilds);
            }
            List<Long> disIds = new ArrayList<>();
            disIds.add(deptResult.getId());
            taffService.disAbleStaff(disIds);
            List<OpeSysPosition> list = opeSysPositionService.list(new QueryWrapper<OpeSysPosition>().eq(OpeSysPosition.COL_DEPT_ID, enter.getId()));
            if (CollectionUtils.isNotEmpty(list)) {
                //????????????
                list.stream().forEach(opeSysPosition -> opeSysPosition.setPositionStatus(Integer.valueOf(DeptStatusEnums.DEPARTMENT.getValue())));
                opeSysPositionService.updateBatch(list);
                //????????????????????????
                ids = list.stream().map(OpeSysPosition::getId).collect(Collectors.toList());
                roleService.disableRole(ids);
            }
        }
        if (deptResult.getDeptStatus().equals(DeptStatusEnums.DEPARTMENT.getValue()) && enter.getDeptStatus().equals(DeptStatusEnums.COMPANY.getValue())) {
            if (StringManaConstant.entityIsNotNull(enter.getPid())) {
                OpeSysDept one = sysDeptService.getOne(new QueryWrapper<OpeSysDept>().eq(OpeSysDept.COL_ID, enter.getPid()));
                if (StringManaConstant.entityIsNotNull(one) && one.getDeptStatus().equals(DeptStatusEnums.DEPARTMENT.getValue())) {
                    throw new SesWebRosException(ExceptionCodeEnums.DEPT_DISABLE.getCode(), ExceptionCodeEnums.DEPT_DISABLE.getMessage());
                }
            }
        }
        BeanUtils.copyProperties(enter, opeSysDept);
        if (StringManaConstant.entityIsNotNull(enter.getPid())) {
            opeSysDept.setLevel(addDeptLevel(enter.getPid()));
        }
        opeSysDept.setPId(enter.getPid());
        opeSysDept.setUpdatedBy(enter.getUserId());
        opeSysDept.setUpdatedTime(new Date());
        opeSysDeptService.updateById(opeSysDept);
        return new GeneralResult(enter.getRequestId());
    }

    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult delete(IdEnter enter) {
        //????????????????????????????????????
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
        // ??????????????????????????????
        ids.add(enter.getId());
        // ????????????????????????
        QueryWrapper<OpeSysRoleDept> opeSysRoleDeptQueryWrapper = new QueryWrapper<>();
        opeSysRoleDeptQueryWrapper.in(OpeSysRoleDept.COL_DEPT_ID, ids);
        List<OpeSysRoleDept> sysRoleDeptList = opeSysRoleDeptService.list(opeSysRoleDeptQueryWrapper);
        if (CollectionUtils.isNotEmpty(sysRoleDeptList)) {
            throw new SesWebRosException(ExceptionCodeEnums.REMOVE_DEPT_UNDER_POSITION.getCode(), ExceptionCodeEnums.REMOVE_DEPT_UNDER_POSITION.getMessage());
        }
        // ????????????
        opeSysDeptService.removeById(enter.getId());
        return new GeneralResult(enter.getRequestId());
    }

    @Override
    public DeptTreeReslt details(IdEnter enter) {

        OpeSysDept dept = sysDeptService.getById(enter.getId());

        DeptTreeReslt reslt = new DeptTreeReslt();

        if (StringManaConstant.entityIsNotNull(reslt)) {
            BeanUtils.copyProperties(dept, reslt);
        }

        return reslt;
    }

    /**
     * ????????????
     *
     * @param enter
     * @return
     */
    @Override
    public DeptDetailsResult deptDetails(IdEnter enter) {
        OpeSysDept byId = sysDeptService.getById(enter.getId());
        DeptDetailsResult result = new DeptDetailsResult();
        if (StringManaConstant.entityIsNull(byId)) {
            throw new SesWebRosException(ExceptionCodeEnums.DEPT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.DEPT_IS_NOT_EXIST.getMessage());
        }
        List<OpeSysDept> list = sysDeptService.list(new QueryWrapper<OpeSysDept>().eq(OpeSysDept.COL_P_ID, enter.getId()));
        OpeSysDept one = sysDeptService.getOne(new QueryWrapper<OpeSysDept>().eq(OpeSysDept.COL_ID, byId.getPId()));
        if (StringManaConstant.entityIsNotNull(one)) {
//            throw new SesWebRosException(ExceptionCodeEnums.PARENT_DEPT_NOT_EXIST.getCode(), ExceptionCodeEnums.PARENT_DEPT_NOT_EXIST.getMessage());
            if (StringManaConstant.entityIsNotNull(one.getDeptStatus()) && 2 == one.getDeptStatus()) {
                throw new SesWebRosException(ExceptionCodeEnums.PARENT_DEPT_IS_DISABLE.getCode(), ExceptionCodeEnums.PARENT_DEPT_IS_DISABLE.getMessage());
            }
        }
        BeanUtils.copyProperties(byId, result);
        if (StringManaConstant.entityIsNotNull(one)) {
            result.setPName(one.getName());
            result.setPId(one.getId());
        }
        List<OpeSysStaff> sysStaff = opeSysStaffService.list(new QueryWrapper<OpeSysStaff>().in(OpeSysStaff.COL_ID, byId.getUpdatedBy(), byId.getCreatedBy()));
        sysStaff.forEach(item -> {
            if (item.getId().equals(byId.getCreatedBy())) {
                result.setCreatedName(item.getFullName());
            }
            if (item.getId().equals(byId.getUpdatedBy())) {
                result.setUpdatedName(item.getFullName());
            }
        });
        if (StringManaConstant.entityIsNotNull(byId.getPrincipal())) {
            OpeSysStaff PrincipalName = opeSysStaffService.getOne(new QueryWrapper<OpeSysStaff>().eq(OpeSysStaff.COL_ID, byId.getPrincipal()));
            if (StringManaConstant.entityIsNotNull(PrincipalName)) {
                result.setPrincipalName(PrincipalName.getFullName());
            }
        }

        result.setDeptCount(list.size());
        result.setEmployeeCount(taffService.deptStaffCount(byId.getId(), 1));
        return result;
    }

    /**
     * ??????????????????
     *
     * @param enter
     * @return
     */
    @Override
    public BooleanResult deleteDeptSelect(IdEnter enter) {
        List<OpeSysDept> list = sysDeptService.list(new QueryWrapper<OpeSysDept>().eq(OpeSysDept.COL_P_ID, enter.getId()));
        BooleanResult booleanResult = new BooleanResult();
        if (CollectionUtils.isNotEmpty(list)) {
            booleanResult.setSuccess(false);
        } else {
            booleanResult.setSuccess(true);
        }
        return booleanResult;
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

    /**
     * ???????????????
     *
     * @param enter
     * @return
     */
    @Override
    public List<PrincipalResult> principals(PrincipalsEnter enter) {
        //?????? ???????????????
        List<Long> deptIds = new ArrayList<>();
        if (StringManaConstant.entityIsNotNull(enter.getId()) && 0 != enter.getId()) {
            deptIds.add(enter.getId());
        }
        return deptServiceMapper.principals(deptIds);
    }

    @Override
    public void checkDeptStatus(Long deptId, boolean flag) {
        OpeSysDept one = sysDeptService.getOne(new QueryWrapper<OpeSysDept>().eq(OpeSysDept.COL_ID, deptId));
        if (StringManaConstant.entityIsNotNull(one) && one.getDeptStatus().equals(DeptStatusEnums.DEPARTMENT.getValue())) {
            throw new SesWebRosException(ExceptionCodeEnums.DEPT_DISABLE.getCode(), ExceptionCodeEnums.DEPT_DISABLE.getMessage());
        }
        // ????????????  ??????????????????4???
        if (flag) {
            if (StringManaConstant.entityIsNotNull(one.getLevel()) && 4 <= one.getLevel()) {
                throw new SesWebRosException(ExceptionCodeEnums.DEPT_LEVEL_ERROR.getCode(), ExceptionCodeEnums.DEPT_LEVEL_ERROR.getMessage());
            }
        }
    }

    /**
     * ??????????????????
     *
     * @param pid
     * @return
     */
    @Override
    public Integer addDeptLevel(Long pid) {
        OpeSysDept one = sysDeptService.getOne(new QueryWrapper<OpeSysDept>().eq(OpeSysDept.COL_ID, pid));
        Integer level = null;
        if (StringManaConstant.entityIsNotNull(one)) {
            level = one.getLevel() + 1;
        }
        return level;
    }

    private OpeSysDept addDept(AddDeptEnter enter) {

        OpeSysDept dept = new OpeSysDept();
        BeanUtils.copyProperties(enter, dept);
        if (StringManaConstant.entityIsNull(enter.getPid()) || 0 == enter.getPid()) {
            dept.setPId(Constant.DEPT_TREE_ROOT_ID);
        } else {
            dept.setPId(enter.getPid());
        }
        dept.setCode(createCode());
        dept.setId(idAppService.getId(SequenceName.OPE_SYS_DEPT));
        dept.setDr(Constant.DR_FALSE);
        dept.setLevel(addDeptLevel(enter.getPid()));
        dept.setCreatedBy(enter.getUserId());
        dept.setCreatedTime(new Date());
        dept.setUpdatedBy(enter.getUserId());
        dept.setUpdatedTime(new Date());

        return dept;
    }

    private OpeSysDept buildDept(SaveDeptEnter enter) {

        OpeSysDept dept = new OpeSysDept();
        BeanUtils.copyProperties(enter, dept);
        if (StringManaConstant.entityIsNull(dept.getPId()) || 0 == dept.getPId()) {
            dept.setPId(Constant.DEPT_TREE_ROOT_ID);
        }
        dept.setId(idAppService.getId(SequenceName.OPE_SYS_DEPT));
        dept.setDr(Constant.DR_FALSE);
        dept.setLevel((DeptLevelEnums.getEnumByValue(enter.getLevel().toString()) == null ? Integer.valueOf(DeptLevelEnums.COMPANY.getValue()) :
                Integer.valueOf(DeptLevelEnums.getEnumByValue(enter.getLevel().toString()).getValue())));
        if (StringManaConstant.entityIsNotNull(enter.getPrincipal()) && 0 != enter.getPrincipal()) {
            dept.setPrincipal(enter.getPrincipal());
        }
        dept.setCreatedBy(enter.getUserId());
        dept.setCreatedTime(new Date());
        dept.setUpdatedBy(enter.getUserId());
        dept.setUpdatedTime(new Date());

        return dept;
    }

    // ?????????????????????  ??????????????????
    public String createCode() {
        String deptCode = "D0" + new Random().nextInt(99999);
        QueryWrapper<OpeSysDept> qw = new QueryWrapper<>();
        qw.eq(OpeSysDept.COL_CODE, deptCode);
        int count = sysDeptService.count(qw);
        if (0 < count) {
            createCode();
        }
        return deptCode;
    }
}
