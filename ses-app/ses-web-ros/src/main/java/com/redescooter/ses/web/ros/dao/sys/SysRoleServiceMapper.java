package com.redescooter.ses.web.ros.dao.sys;

import com.redescooter.ses.web.ros.vo.sys.role.RoleListEnter;
import com.redescooter.ses.web.ros.vo.sys.role.DeptRoleListResult;
import com.redescooter.ses.web.ros.vo.sys.role.RoleResult;

import java.util.List;

/**
 * @ClassName:SysRoleServiceMapper
 * @description: SysRoleServiceMapper
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/13 12:20
 */
public interface SysRoleServiceMapper {

    /**
     * 角色列表
     *
     * @param enter
     * @return
     */
    List<RoleResult> list(RoleListEnter enter);

    /**
     * 角色部门列表
     *
     * @param enter
     * @return
     */
    List<DeptRoleListResult> roleDeptlist(RoleListEnter enter);
}
