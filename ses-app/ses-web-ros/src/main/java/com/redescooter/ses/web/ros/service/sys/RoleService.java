package com.redescooter.ses.web.ros.service.sys;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.web.ros.vo.sys.dept.DeptAuthorityDetailsResult;
import com.redescooter.ses.web.ros.vo.sys.role.*;
import com.redescooter.ses.web.ros.vo.tree.MenuTreeResult;
import com.redescooter.ses.web.ros.vo.tree.SalesAreaTressResult;

import java.util.List;

/**
 * @ClassName RoleService
 * @Author Jerry
 * @date 2020/03/12 14:10
 * @Description:
 */
public interface RoleService {

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
    DeptAuthorityDetailsResult roleAuthDetails(String type, IdEnter enter);

    /**
     * 获取岗位角色下的所有销售区域，并标注已选择的销售区域
     *
     * @param type
     * @param enter
     * @return
     */
    List<SalesAreaTressResult> roleSalesAreaById(String type, IdEnter enter);

    /**
     * 获取岗位角色下的所有菜单权限，并标注已选择的菜单权限
     *
     * @param type
     * @param enter
     * @return
     */
    List<MenuTreeResult> roleMenuById(String type, IdEnter enter);


    /**
     * 新增角色
     * @param enter
     * @return
     */
    GeneralResult roleSave(RoleSaveOrEditEnter enter);

    /**
     * @Author Aleks
     * @Description  修改角色
     * @Date  2020/9/1 13:20
     * @Param [enter]
     * @return
     **/
    GeneralResult roleEdit(RoleSaveOrEditEnter enter);

    /**
     * @Author Aleks
     * @Description  删除角色
     * @Date  2020/9/1 15:59
     * @Param [enter]
     * @return
     **/
    GeneralResult roleDelete(RoleOpEnter enter);


    /**
     * @Author Aleks
     * @Description  角色详情
     * @Date  2020/9/1 16:10
     * @Param [enter]
     * @return
     **/
    RoleDetailResult roleDetail(RoleOpEnter enter);



    PageResult<RoleListResult> roleList(RoleQueryListEnter enter);
}
