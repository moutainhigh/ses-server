package com.redescooter.ses.web.ros.service.sys;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.web.ros.vo.sys.dept.DeptAuthorityDetailsResult;
import com.redescooter.ses.web.ros.vo.sys.role.DeptRoleListResult;
import com.redescooter.ses.web.ros.vo.sys.role.RoleListEnter;
import com.redescooter.ses.web.ros.vo.sys.role.RoleEnter;

import java.util.List;

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
     * 岗位创建
     *
     * @param enter
     * @return
     */
    GeneralResult delete(IdEnter enter);

    /**
     * 角色列表
     *
     * @param enter
     * @return
     */
    List<DeptRoleListResult> list(RoleListEnter enter);


    /**
     * 部门权限详情
     *
     * @param enter
     * @return
     */
    DeptAuthorityDetailsResult authorityDetails(IdEnter enter);
}
