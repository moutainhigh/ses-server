package com.redescooter.ses.web.ros.vo.sys.menu;

import com.redescooter.ses.web.ros.vo.tree.MenuTreeResult;

import java.util.List;

/**
 * @ClassName ModulePermissionsResult
 * @Author Jerry
 * @date 2020/03/13 14:49
 * @Description:
 */

public class ModulePermissionsResult {
    private Long id;
    private String name;
    private String code;
    private Integer sort;

    private List<MenuTreeResult> permissions;
}
