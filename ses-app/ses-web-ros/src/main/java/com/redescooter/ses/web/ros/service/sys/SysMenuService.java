package com.redescooter.ses.web.ros.service.sys;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.router.VueRouter;
import com.redescooter.ses.web.ros.vo.sys.menu.EditMenuEnter;
import com.redescooter.ses.web.ros.vo.sys.menu.ModuleAuthResult;
import com.redescooter.ses.web.ros.vo.sys.menu.SaveMenuEnter;
import com.redescooter.ses.web.ros.vo.tree.MenuTreeResult;

import java.util.List;
import java.util.Map;

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
     * 菜单列表
     *
     * @param enter
     * @return
     */
    List<MenuTreeResult> list(IdEnter enter);

    /**
     * 菜单详情
     *
     * @param enter
     * @return
     */
    MenuTreeResult details(IdEnter enter);

    /**
     * 菜单删除
     *
     * @param enter
     * @return
     */
    GeneralResult delete(IdEnter enter);

    /**
     * 菜单编辑
     *
     * @param enter
     * @return
     */
    GeneralResult edit(EditMenuEnter enter);

    /**
     * 角色岗位菜单权限查看与授权
     *
     * @param enter
     * @return
     */
    List<MenuTreeResult> roleMenuAuth(GeneralEnter enter);

    /**
     * 操作权限查看与授权
     *
     * @param enter
     * @return
     */
    List<MenuTreeResult> roleOperationAuth(GeneralEnter enter);


    /**
     * 创建vue动态路由
     *
     * @param enter
     * @return
     */
    List<VueRouter<MenuTreeResult>> vueRouters(GeneralEnter enter);

}
