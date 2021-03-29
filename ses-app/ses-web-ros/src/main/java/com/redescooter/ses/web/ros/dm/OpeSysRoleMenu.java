package com.redescooter.ses.web.ros.dm;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "com-redescooter-ses-web-ros-dm-OpeSysRoleMenu")
@TableName(value = "ope_sys_role_menu")
public class OpeSysRoleMenu {

    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value = "ID")
    private Long id;

    /**
     * 角色ID
     */
    @TableField(value = "role_id")
    @ApiModelProperty(value = "角色ID")
    private Long roleId;

    /**
     * 菜单ID
     */
    @TableField(value = "menu_id")
    @ApiModelProperty(value = "菜单ID")
    private Long menuId;

    public static final String COL_ROLE_ID = "role_id";

    public static final String COL_MENU_ID = "menu_id";

    public static final String COL_ID = "id";

    public OpeSysRoleMenu() {

    }

    public OpeSysRoleMenu(Long roleId, Long menuId) {
        this.roleId = roleId;
        this.menuId = menuId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }
}