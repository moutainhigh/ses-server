package com.redescooter.ses.web.ros.service.sys.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.web.ros.dm.OpeSysDept;
import com.redescooter.ses.web.ros.dm.OpeSysDeptRelation;
import com.redescooter.ses.web.ros.service.base.OpeSysDeptRelationService;
import com.redescooter.ses.web.ros.service.sys.SysDeptRelationService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName SysDeptRelationServiceImpl
 * @Author Jerry
 * @date 2020/03/11 20:46
 * @Description:
 */
@Slf4j
@Service
public class SysDeptRelationServiceImpl implements SysDeptRelationService {

    @Autowired
    private OpeSysDeptRelationService sysDeptRelationService;

    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public void insertDeptRelation(OpeSysDept sysDept) {
        //增加部门关系表
        OpeSysDeptRelation condition = new OpeSysDeptRelation();
        condition.setDescendant(sysDept.getPId());
        List<OpeSysDeptRelation> relationList = sysDeptRelationService
                .list(Wrappers.<OpeSysDeptRelation>query().lambda()
                        .eq(OpeSysDeptRelation::getDescendant, sysDept.getPId()))
                .stream().map(relation -> {
                    relation.setDescendant(sysDept.getId());
                    return relation;
                }).collect(Collectors.toList());
        if (CollUtil.isNotEmpty(relationList)) {
            sysDeptRelationService.saveBatch(relationList);
        }

        //自己也要维护到关系表中
        OpeSysDeptRelation own = new OpeSysDeptRelation();
        own.setDescendant(sysDept.getId());
        own.setAncestor(sysDept.getId());
        sysDeptRelationService.save(own);
    }

    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public void deleteAllDeptRealtion(IdEnter enter) {
        sysDeptRelationService.removeById(enter.getId());
    }

    @Override
    public void updateDeptRealtion(OpeSysDeptRelation relation) {


    }
}
