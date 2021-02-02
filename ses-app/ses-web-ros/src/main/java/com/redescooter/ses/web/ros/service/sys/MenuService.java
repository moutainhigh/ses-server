package com.redescooter.ses.web.ros.service.sys;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.router.VueRouter;
import com.redescooter.ses.web.ros.vo.sys.menu.EditMenuEnter;
import com.redescooter.ses.web.ros.vo.sys.menu.QueryMenuEnter;
import com.redescooter.ses.web.ros.vo.sys.menu.SaveMenuEnter;
import com.redescooter.ses.web.ros.vo.tree.MenuDatasEnter;
import com.redescooter.ses.web.ros.vo.tree.MenuDatasListResult;
import com.redescooter.ses.web.ros.vo.tree.MenuTreeResult;

import java.util.List;

/**
 * @ClassName MenuService
 * @Author Jerry
 * @date 2020/03/11 17:51
 * @Description:
 */
public interface MenuService {

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
    List<MenuTreeResult> trees(GeneralEnter enter);

    /**
     * 菜单列表
     *
     * @param enter
     * @return
     */
    List<MenuTreeResult> parallel(QueryMenuEnter enter);

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
     * 获取岗位角色下的菜单权限
     *
     * @param enter
     * @return
     */
    List<MenuTreeResult> findMenuByRoleId(GeneralEnter enter);

    /**
     * 角色岗位菜单权限查看与授权
     * 树结构
     *
     * @param enter
     * @return
     */
    List<MenuTreeResult> roleMenuAuthTree(GeneralEnter enter);

    /**
     * 根据岗位角色ID获取菜单权限
     * 树结构
     *
     * @param enter
     * @return
     */
    List<MenuTreeResult> roleMenuAuthTreeByRoleId(IdEnter enter);

    /**
     * 角色岗位菜单权限查看与授权
     * 平行结构
     *
     * @param enter
     * @return
     */
    List<MenuTreeResult> roleMenuAuthParallel(GeneralEnter enter);

    /**
     * 根据岗位角色ID获取菜单权限
     * 平行结构
     *
     * @param enter
     * @return
     */
    List<MenuTreeResult> roleMenuAuthParallelByRoleId(IdEnter enter);

    /**
     * 创建vue动态路由
     *
     * @param enter
     * @return
     */
    List<VueRouter<MenuTreeResult>> vueRouters(GeneralEnter enter);

    /**
     * 菜单下拉数据
     * @param enter
     * @return
     */
    List<MenuDatasListResult> menuDatas(MenuDatasEnter enter);

    /**
     * 目录列表
     */
    List<MenuTreeResult> getCatalogList(GeneralEnter enter);

    /**
     * 根据父级id得到下级信息
     */
    List<MenuTreeResult> getSubListById(IdEnter enter);

}
