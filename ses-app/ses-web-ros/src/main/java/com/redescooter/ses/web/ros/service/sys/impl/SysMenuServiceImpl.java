package com.redescooter.ses.web.ros.service.sys.impl;

import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.dm.OpeSysMenu;
import com.redescooter.ses.web.ros.service.base.OpeSysMenuService;
import com.redescooter.ses.web.ros.service.sys.SysMenuService;
import com.redescooter.ses.web.ros.utils.TreeUtil;
import com.redescooter.ses.web.ros.vo.sys.menu.SaveMenuEnter;
import com.redescooter.ses.web.ros.vo.tree.MenuTreeResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @ClassName SysMenuServiceImpl
 * @Author Jerry
 * @date 2020/03/11 18:01
 * @Description:
 */
@Slf4j
@Service
public class SysMenuServiceImpl implements SysMenuService {

    @Autowired
    private OpeSysMenuService sysMenuService;

    @Autowired
    private IdAppService idAppService;

    @Override
    public GeneralResult save(SaveMenuEnter enter) {

        OpeSysMenu menu = new OpeSysMenu();
        BeanUtils.copyProperties(enter, menu);
        if (menu.getPId() == null || menu.getPId() == 0) {
            menu.setPId(Constant.MENU_TREE_ROOT_ID);
        }
        menu.setId(idAppService.getId(SequenceName.OPE_SYS_MENU));
        menu.setDr(Constant.DR_FALSE);
        menu.setCreatedBy(enter.getUserId());
        menu.setCreatedTime(new Date());
        menu.setUpdatedBy(enter.getUserId());
        menu.setUpdatedTime(new Date());

        sysMenuService.save(menu);
        return new GeneralResult(enter.getRequestId());
    }

    @Override
    public List<MenuTreeResult> trees(GeneralEnter enter) {

        List<OpeSysMenu> list = sysMenuService.list();

        return TreeUtil.buildTree(list, enter, Constant.MENU_TREE_ROOT_ID);
    }
}
