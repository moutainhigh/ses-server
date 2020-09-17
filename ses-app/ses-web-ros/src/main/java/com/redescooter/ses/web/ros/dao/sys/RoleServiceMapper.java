package com.redescooter.ses.web.ros.dao.sys;

import com.redescooter.ses.web.ros.dm.OpeSysRole;
import com.redescooter.ses.web.ros.vo.sys.role.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * @ClassName:SysRoleServiceMapper
 * @description: RoleServiceMapper
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/13 12:20
 */
public interface RoleServiceMapper {

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


    /**
     * @Author Aleks
     * @Description  查询员工的角色
     * @Date  2020/9/1 15:48
     * @Param
     * @return
     **/
    List<OpeSysRole> staffRoles(@Param("staffId") Long staffId,@Param("roleId")Long roleId);


    RoleDetailResult roleDetail(@Param("roleId")Long roleId);

    int totalRows(@Param("enter") RoleQueryListEnter enter,@Param("deptIds") Set<Long> deptIds);

    List<RoleListResult> roleList(@Param("enter") RoleQueryListEnter enter,@Param("deptIds") Set<Long> deptIds);
}
