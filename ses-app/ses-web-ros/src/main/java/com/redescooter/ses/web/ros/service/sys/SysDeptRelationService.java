package com.redescooter.ses.web.ros.service.sys;

import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.web.ros.dm.OpeSysDept;
import com.redescooter.ses.web.ros.dm.OpeSysDeptRelation;

/**
 * @ClassName SysDeptRelationService
 * @Author Jerry
 * @date 2020/03/11 20:45
 * @Description:
 */
public interface SysDeptRelationService {

    /**
     * 新建部门关系
     *
     * @param sysDept 部门
     */
    void insertDeptRelation(OpeSysDept sysDept);

    /**
     * 通过ID删除部门关系
     *
     * @param enter
     */
    void deleteAllDeptRealtion(IdEnter enter);

    /**
     * 更新部门关系
     *
     * @param relation
     */
    void updateDeptRealtion(OpeSysDeptRelation relation);
}
