package com.redescooter.ses.web.ros.service.sys;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.web.ros.vo.sys.position.RoleDeptListResult;
import com.redescooter.ses.web.ros.vo.sys.position.RoleListEnter;
import com.redescooter.ses.web.ros.vo.sys.role.RoleEnter;

/**
 * @ClassName SysRoleService
 * @Author Jerry
 * @date 2020/03/12 14:10
 * @Description:
 */
public interface SysRoleService {

    /**
     * 岗位创建
     *
     * @param enter
     * @return
     */
    GeneralResult save(RoleEnter enter);

    /**
     * 岗位编辑
     *
     * @param enter
     * @return
     */
    GeneralResult edit(RoleEnter enter);

    /**
     * 角色列表
     *
     * @param enter
     * @return
     */
    RoleDeptListResult list(RoleListEnter enter);

}
