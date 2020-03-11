package com.redescooter.ses.web.ros.vo.tree;

import com.redescooter.ses.web.ros.dm.OpeSysMenu;
import io.swagger.annotations.ApiModel;
import lombok.*;


/**
 * description: SysMenuTreeResult
 * author: jerry.li
 * create: 2019-05-30 14:34
 */

@ApiModel(value = "菜单列表", description = "菜单列表")
@Data
@EqualsAndHashCode(callSuper = true)
public class SysMenuTreeResult extends TreeNode {

    private String icon;
    private String name;
    private boolean spread = false;
    private String path;
    private String component;
    private String authority;
    private String redirect;
    private String code;
    private String type;
    private String label;
    private Integer sort;

    public SysMenuTreeResult() {
    }

    public SysMenuTreeResult(long id, String name, long pId) {
        this.id = id;
        this.pId = pId;
        this.name = name;
        this.label = name;
    }

    public SysMenuTreeResult(long id, String name, SysMenuTreeResult parent) {
        this.id = id;
        this.pId = parent.getId();
        this.name = name;
        this.label = name;
    }

    public SysMenuTreeResult(OpeSysMenu menuVo) {
        this.id = menuVo.getId();
        this.pId = menuVo.getParentId();
        this.icon = menuVo.getIcon();
        this.name = menuVo.getName();
        this.path = menuVo.getPath();
        this.type = menuVo.getType();
        this.label = menuVo.getName();
        this.sort = menuVo.getSort();
    }

}
