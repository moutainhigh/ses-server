package com.redescooter.ses.web.ros.dao.sys;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.web.ros.vo.sys.menu.ModulePermissionsResult;
import com.redescooter.ses.web.ros.vo.tree.MenuTreeResult;

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
    List<ModulePermissionsResult> fatherModulePermissions(GeneralEnter enter);

    /**
     * 根据儿子找父级权限
     *
     * @param enter
     * @return
     */
    List<ModulePermissionsResult> fatherModulePermissionsBySon(IdEnter enter);


}
