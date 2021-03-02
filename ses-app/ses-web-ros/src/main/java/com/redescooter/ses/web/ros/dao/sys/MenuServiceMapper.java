package com.redescooter.ses.web.ros.dao.sys;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.web.ros.dm.OpeSysMenu;
import com.redescooter.ses.web.ros.vo.sys.menu.ModuleAuthResult;
import com.redescooter.ses.web.ros.vo.tree.MenuDatasListResult;
import com.redescooter.ses.web.ros.vo.tree.MenuTreeResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassName MenuServiceMapper
 * @Author Jerry
 * @date 2020/03/13 15:24
 * @Description:
 */

public interface MenuServiceMapper {

    /**
     * 获取模块权限列表
     *
     * @param id
     * @return
     */
    List<MenuTreeResult> queryMenuSon(long id);

    /**
     * 父级module权限获取
     *
     * @param enter
     * @return
     */
    List<ModuleAuthResult> fatherModulePermissions(GeneralEnter enter);

    List<MenuDatasListResult> menuDatas(@Param("type") Integer type);


    List<Long> findChilds(@Param("id")Long id);

    List<Long> getRoleIds(Long userId);

    /**
     * @Author Aleks
     * @Description  查询当前登陆用户的菜单按钮权限
     * @Date  2020/11/27 19:29
     * @Param
     * @return
     **/
    List<OpeSysMenu> menusByUserId(@Param("userId") Long userId);

    /**
     * 返回二级菜单和三级菜单
     */
    List<MenuDatasListResult> getSecondAndThirdMenu();

    /**
     * 得到除了自身外的其他所有目录和二级菜单和三级菜单
     */
    List<MenuDatasListResult> getAllCatalogAndMenu(@Param("id") Long id);

}
