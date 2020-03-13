package com.redescooter.ses.web.ros.service.sys;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.router.VueRouter;
import com.redescooter.ses.web.ros.dm.OpeSysRole;
import com.redescooter.ses.web.ros.dm.OpeSysRoleMenu;
import com.redescooter.ses.web.ros.vo.sys.menu.ModulePermissionsResult;
import com.redescooter.ses.web.ros.vo.sys.menu.SaveMenuEnter;
import com.redescooter.ses.web.ros.vo.tree.MenuTreeResult;

import java.util.List;

/**
 * @ClassName SysMenuService
 * @Author Jerry
 * @date 2020/03/11 17:51
 * @Description:
 */
public interface SysMenuService {

    /**
     * 菜单保存
     *
     * @param enter
     * @return
     */
    GeneralResult save(SaveMenuEnter enter);

    /**
     * 菜单树列表
     *
     * @param enter
     * @return
     */
    List<MenuTreeResult> trees(IdEnter enter);

    /**
     * 创建vue动态路由
     *
     * @param enter
     * @return
     */
    List<VueRouter<MenuTreeResult>> userRouters(GeneralEnter enter);

    /**
     * 用户菜单树
     *
     * @param enter
     * @return
     */
    List<MenuTreeResult> userMenuTrees(GeneralEnter enter);

    /**
     * 模块权限列表
     *
     * @param enter
     * @return
     */
    List<ModulePermissionsResult> modulePermissions(IdEnter enter);
}
