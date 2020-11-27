package com.redescooter.ses.web.ros.dm;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 部门管理
 */
@ApiModel(value = "com-redescooter-ses-web-ros-dm-OpeSysDept")
@Data
@Builder
@TableName(value = "ope_sys_dept")
@AllArgsConstructor
@NoArgsConstructor
public class OpeSysDept {
    private static final long serialVersionUID = 1L;
    @TableId(value = "id")
    @ApiModelProperty(value = "")
    private Long id;

    /**
     * 逻辑删除
     */
    @TableField(value = "dr")
    @ApiModelProperty(value = "逻辑删除")
    @TableLogic
    private Integer dr;

    /**
     * 父级部门id
     */
    @TableField(value = "p_id")
    @ApiModelProperty(value = "父级部门id")
    private Long pId;

    /**
     * 租户id
     */
    @TableField(value = "tenant_id")
    @ApiModelProperty(value = "租户id")
    private Long tenantId;

    /**
     * 负责人
     */
    @TableField(value = "principal")
    @ApiModelProperty(value = "负责人")
    private Long principal;

    /**
     * 级别0公司，1部门
     */
    @TableField(value = "level")
    @ApiModelProperty(value = "级别0公司，1部门")
    private Integer level;

    /**
     * 部门名称
     */
    @TableField(value = "name")
    @ApiModelProperty(value = "部门名称")
    private String name;

    /**
     * 部门编码
     */
    @TableField(value = "code")
    @ApiModelProperty(value = "部门编码")
    private String code;

    /**
     * 部门状态 1：正常，2：禁用
     */
    @TableField(value = "dept_status")
    @ApiModelProperty(value = "部门状态 1：正常，2：禁用")
    private Integer deptStatus;

    /**
     * 描述说明
     */
    @TableField(value = "description")
    @ApiModelProperty(value = "描述说明")
    private String description;

    /**
     * 排序
     */
    @TableField(value = "sort")
    @ApiModelProperty(value = "排序")
    private Integer sort;

    /**
     * 系统内置标识
     */
    @TableField(value = "system_root")
    @ApiModelProperty(value = "系统内置标识")
    private String systemRoot;

    /**
     * 创建人
     */
    @TableField(value = "created_by")
    @ApiModelProperty(value = "创建人")
    private Long createdBy;

    /**
     * 创建时间
     */
    @TableField(value = "created_time")
    @ApiModelProperty(value = "创建时间")
    private Date createdTime;

    /**
     * 更新人
     */
    @TableField(value = "updated_by")
    @ApiModelProperty(value = "更新人")
    private Long updatedBy;

    /**
     * 修改时间
     */
    @TableField(value = "updated_time")
    @ApiModelProperty(value = "修改时间")
    private Date updatedTime;

    /**
     * 冗余字段
     */
    @TableField(value = "def1")
    @ApiModelProperty(value = "冗余字段")
    private String def1;

    /**
     * 冗余字段
     */
    @TableField(value = "def2")
    @ApiModelProperty(value = "冗余字段")
    private String def2;

    /**
     * 冗余字段
     */
    @TableField(value = "def3")
    @ApiModelProperty(value = "冗余字段")
    private String def3;

    /**
     * 冗余字段
     */
    @TableField(value = "def4")
    @ApiModelProperty(value = "冗余字段")
    private BigDecimal def4;

    /**
     * 冗余字段
     */
    @TableField(value = "def5")
    @ApiModelProperty(value = "冗余字段")
    private String def5;

    public static final String COL_ID = "id";

    public static final String COL_DR = "dr";

    public static final String COL_P_ID = "p_id";

    public static final String COL_TENANT_ID = "tenant_id";

    public static final String COL_PRINCIPAL = "principal";

    public static final String COL_LEVEL = "level";

    public static final String COL_NAME = "name";

    public static final String COL_CODE = "code";

    public static final String COL_DEPT_STATUS = "dept_status";

    public static final String COL_DESCRIPTION = "description";

    public static final String COL_SORT = "sort";

    public static final String COL_SYSTEM_ROOT = "system_root";

    public static final String COL_CREATED_BY = "created_by";

    public static final String COL_CREATED_TIME = "created_time";

    public static final String COL_UPDATED_BY = "updated_by";

    public static final String COL_UPDATED_TIME = "updated_time";

    public static final String COL_DEF1 = "def1";

    public static final String COL_DEF2 = "def2";

    public static final String COL_DEF3 = "def3";

    public static final String COL_DEF4 = "def4";

    public static final String COL_DEF5 = "def5";
}