package com.redescooter.ses.web.ros.service.sys.impl;

import cn.hutool.core.collection.CollUtil;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.enums.dept.DeptLevelEnums;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.dm.OpeSysDept;
import com.redescooter.ses.web.ros.service.base.OpeSysDeptService;
import com.redescooter.ses.web.ros.service.sys.SysDeptRelationService;
import com.redescooter.ses.web.ros.service.sys.SysDeptService;
import com.redescooter.ses.web.ros.utils.TreeUtil;
import com.redescooter.ses.web.ros.vo.sys.dept.SaveDeptEnter;
import com.redescooter.ses.web.ros.vo.tree.DeptTreeReslt;
import lombok.extern.slf4j.Slf4j;
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
    public GeneralResult edit(SaveDeptEnter enter) {
        return null;
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
