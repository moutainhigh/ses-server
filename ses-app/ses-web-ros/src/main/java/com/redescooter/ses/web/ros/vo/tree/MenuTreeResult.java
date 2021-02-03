package com.redescooter.ses.web.ros.vo.tree;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.redescooter.ses.api.common.vo.tree.TreeNode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;


/**
 * description: MenuTreeResult
 * author: jerry.li
 * create: 2019-05-30 14:34
 */
@ApiModel(value = "菜单树")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class MenuTreeResult extends TreeNode {

    @ApiModelProperty(value = "菜单名称")
    private String name;

    @ApiModelProperty(value = "菜单编码")
    private String code;

    @ApiModelProperty(value = "权限码")
    private String permission;

    @ApiModelProperty(value = "路由")
    private String path;

    @ApiModelProperty(value = "对应路由组件")
    private String component;

    @ApiModelProperty(value = "菜单类型 0:菜单 1:按钮，2：目录")
    private String type;

    @ApiModelProperty(value = "图表")
    private String icon = "";

    @ApiModelProperty(value = "等级")
    private Integer level;

    @ApiModelProperty(value = "菜单权重")
    private int sort;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "扩展字段,父级name")
    private String def1;

    @ApiModelProperty(value = "扩展字段，父级code")
    private String def2;

    @ApiModelProperty(value = "扩展字段，父级type")
    private String def3;

    @ApiModelProperty(value = "是否选中")
    private boolean checked = Boolean.FALSE;

    @ApiModelProperty(value = "是否展开")
    private boolean spread = Boolean.FALSE;

    @ApiModelProperty(value = "是否隐藏")
    private boolean hidden = Boolean.FALSE;

    @TableField(value = "menu_status")
    @ApiModelProperty(value = "状态 1：正常，2：禁用")
    private Integer menuStatus = 1;

    @TableField(value = "created_time")
    @ApiModelProperty(value = "创建时间")
    private Date createdTime;

    @ApiModelProperty(value = "是否外链。0：否，1：是")
    private String ifToLink;

    /**
     * true无下级  false有下级
     */
    @JsonInclude(value = Include.NON_NULL)
    private Boolean _loading = Boolean.FALSE;

}
