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
import com.redescooter.ses.web.ros.dm.OpeSysDept;
import com.redescooter.ses.web.ros.dm.OpeSysDeptRelation;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.OpeSysDeptRelationService;
import com.redescooter.ses.web.ros.service.base.OpeSysDeptService;
import com.redescooter.ses.web.ros.service.sys.SysDeptRelationService;
import com.redescooter.ses.web.ros.service.sys.SysDeptService;
import com.redescooter.ses.web.ros.utils.TreeUtil;
import com.redescooter.ses.web.ros.vo.sys.dept.DeptListReslut;
import com.redescooter.ses.web.ros.vo.sys.dept.EditDeptEnter;
import com.redescooter.ses.web.ros.vo.sys.dept.SaveDeptEnter;
import com.redescooter.ses.web.ros.vo.tree.DeptTreeReslt;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private OpeSysDeptService sysDeptService;
    @Autowired
    private SysDeptRelationService sysDeptRelationService;

    @Autowired
    private OpeSysDeptRelationService opeSysDeptRelationService;
    @Autowired
    private OpeSysDeptService opeSysDeptService;

    @Autowired
    private IdAppService idAppService;

    @Transactional
    @Override
    public GeneralResult save(SaveDeptEnter enter) {
        OpeSysDept dept = this.buildDept(enter);
        sysDeptService.save(dept);
        sysDeptRelationService.insertDeptRelation(dept);
        return new GeneralResult(enter.getRequestId());
    }

    @Override
    public List<DeptTreeReslt> trees(GeneralEnter enter) {

        List<OpeSysDept> list = sysDeptService.list();

        List<DeptTreeReslt> trees = new ArrayList<>();
        if (CollUtil.isNotEmpty(list)) {
            list.forEach(ls -> {
                DeptTreeReslt dept = new DeptTreeReslt();
                BeanUtils.copyProperties(ls, dept);
                trees.add(dept);
            });
        }
        return TreeUtil.build(trees, Constant.DEPT_TREE_ROOT_ID);
    }

    @Override
    public List<DeptListReslut> list(GeneralEnter enter) {
        return null;
    }

    @Override
    public GeneralResult edit(EditDeptEnter enter) {
        //更新部门
        OpeSysDept dept = new OpeSysDept();
        BeanUtils.copyProperties(enter, dept);
        sysDeptService.updateById(dept);
        //删除部门关系
        QueryWrapper<OpeSysDeptRelation> opeSysDeptRelationQueryWrapper = new QueryWrapper<>();
        opeSysDeptRelationQueryWrapper.eq(OpeSysDeptRelation.COL_DESCENDANT, enter.getId());
        opeSysDeptRelationService.remove(opeSysDeptRelationQueryWrapper);
        //重建部门关系
        List<OpeSysDeptRelation> opeSysDeptRelationList = new ArrayList<>();
        opeSysDeptRelationList.add(OpeSysDeptRelation.builder().ancestor(enter.getPId()).descendant(enter.getId()).build());
        opeSysDeptRelationList.add(OpeSysDeptRelation.builder().ancestor(enter.getId()).descendant(enter.getId()).build());
        opeSysDeptRelationService.batchInsert(opeSysDeptRelationList);
        return new GeneralResult(enter.getRequestId());
    }

    @Override
    public GeneralResult delete(IdEnter enter) {
        OpeSysDept opeSysDept = opeSysDeptService.getById(enter.getId());
        if (opeSysDept == null) {
            throw new SesWebRosException(ExceptionCodeEnums.DEPT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.DEPT_IS_NOT_EXIST.getMessage());
        }
        // 查询所有部门关系
        List<OpeSysDeptRelation> opeSysDeptRelationList = opeSysDeptRelationService.list();
        // 拿到需要删除的部门Id
        List<Long> childDept = findChildDept(opeSysDeptRelationList, enter.getId(), null);

        childDept.stream().forEach(System.out::println);
        return new GeneralResult(enter.getRequestId());
    }

    private List<Long> findChildDept(List<OpeSysDeptRelation> opeSysDeptRelationList, Long id, List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            ids = new ArrayList<>();
        }
        if (CollectionUtils.isEmpty(opeSysDeptRelationList)) {
            return new ArrayList<>();
        }

        for (OpeSysDeptRelation item : opeSysDeptRelationList) {
            if (item.getAncestor().equals(id)) {
                ids.add(item.getDescendant());
                id = item.getDescendant();
                opeSysDeptRelationList.remove(item);
                findChildDept(opeSysDeptRelationList, id, ids);
            }
        }

        return new ArrayList<>();
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



    private OpeSysDept buildDept(SaveDeptEnter enter) {

        OpeSysDept dept = new OpeSysDept();
        BeanUtils.copyProperties(enter, dept);
        if (dept.getPId() == null || dept.getPId() == 0) {
            dept.setPId(Constant.DEPT_TREE_ROOT_ID);
        }
        dept.setId(idAppService.getId(SequenceName.OPE_SYS_DEPT));
        dept.setDr(Constant.DR_FALSE);
        dept.setLevel((DeptLevelEnums.getEnumByValue(enter.getLevel().toString()) == null ? Integer.valueOf(DeptLevelEnums.COMPANY.getValue()) : Integer.valueOf(DeptLevelEnums.getEnumByValue(enter.getLevel().toString()).getValue())));
        dept.setCreatedBy(enter.getUserId());
        dept.setCreatedTime(new Date());
        dept.setUpdatedBy(enter.getUserId());
        dept.setUpdatedTime(new Date());

        return dept;
    }

}
