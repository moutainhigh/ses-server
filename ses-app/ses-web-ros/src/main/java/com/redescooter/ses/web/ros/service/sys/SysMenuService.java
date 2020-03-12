package com.redescooter.ses.web.ros.service.sys;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.router.VueRouter;
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
    List<MenuTreeResult> trees(GeneralEnter enter);


    List<VueRouter<MenuTreeResult>> userRouters(GeneralEnter enter);
}
