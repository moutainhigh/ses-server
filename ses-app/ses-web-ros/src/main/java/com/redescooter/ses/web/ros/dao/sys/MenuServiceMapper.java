package com.redescooter.ses.web.ros.dao.sys;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
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


    List<ModulePermissionsResult> modulePermissions(GeneralEnter enter);
}
