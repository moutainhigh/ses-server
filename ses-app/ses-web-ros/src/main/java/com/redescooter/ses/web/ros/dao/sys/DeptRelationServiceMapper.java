package com.redescooter.ses.web.ros.dao.sys;

import com.redescooter.ses.web.ros.dm.OpeSysDeptRelation;

/**
 * @ClassName DeptRelationServiceMapper
 * @Author Jerry
 * @date 2020/03/13 12:07
 * @Description:
 */
public interface DeptRelationServiceMapper {

    /**
     * 更新部门关系
     *
     * @return
     */
    void updateDeptRelations(OpeSysDeptRelation deptRelation);


