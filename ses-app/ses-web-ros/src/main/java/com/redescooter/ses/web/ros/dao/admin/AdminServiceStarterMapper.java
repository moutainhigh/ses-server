package com.redescooter.ses.web.ros.dao.admin;

import com.redescooter.ses.web.ros.dm.OpeSysDept;
import com.redescooter.ses.web.ros.dm.OpeSysUser;

/**
 * @ClassName:AdminServiceStarterMapper
 * @description: AdminServiceStarterMapper
 * @author: Alex
 * @Version：1.3
 * @create: 2020/06/01 11:28
 */
public interface AdminServiceStarterMapper {

    /**
     * 校验admin 是否存在
     *
     * @param adminUserName
     * @return
     */
    OpeSysUser checkAdmin(String adminUserName);

    /**
     * 查询部门根节点
     * @param deptTreeRootId
     * @return
     */
    OpeSysDept topDept(long deptTreeRootId);
}
