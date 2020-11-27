package com.redescooter.ses.web.ros.dm;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 岗位表
 */
@ApiModel(value = "com-redescooter-ses-web-ros-dm-OpeSysPosition")
@Data
@TableName(value = "ope_sys_position")
public class OpeSysPosition {
    private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
    @TableId(value = "id")
    @ApiModelProperty(value = "主键")
    private Long id;

    /**
     * 逻辑删除标识 0：正常，1：删除
     */
    @TableField(value = "dr")
    @ApiModelProperty(value = "逻辑删除标识 0：正常，1：删除")
    @TableLogic
    private Integer dr;

    /**
     * 租户id
     */
    @TableField(value = "tenant_id")
    @ApiModelProperty(value = "租户id")
    private Long tenantId;

    /**
     * 岗位名称
     */
    @TableField(value = "position_name")
    @ApiModelProperty(value = "岗位名称")
    private String positionName;

    /**
     * 岗位编码
     */
    @TableField(value = "position_code")
    @ApiModelProperty(value = "岗位编码")
    private String positionCode;

    /**
     * 所属部门id
     */
    @TableField(value = "dept_id")
    @ApiModelProperty(value = "所属部门id")
    private Long deptId;

    /**
     * 岗位排序
     */
    @TableField(value = "sort")
    @ApiModelProperty(value = "岗位排序")
    private Integer sort;

    /**
     * 岗位状态  1：正常，2：禁用
     */
    @TableField(value = "position_status")
    @ApiModelProperty(value = "岗位状态  1：正常，2：禁用")
    private Integer positionStatus;

    /**
     * 备注
     */
    @TableField(value = "remark")
    @ApiModelProperty(value = "备注")
    private String remark;

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
     * 更新时间
     */
    @TableField(value = "updated_time")
    @ApiModelProperty(value = "更新时间")
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

    public static final String COL_TENANT_ID = "tenant_id";

    public static final String COL_POSITION_NAME = "position_name";

    public static final String COL_POSITION_CODE = "position_code";

    public static final String COL_DEPT_ID = "dept_id";

    public static final String COL_SORT = "sort";

    public static final String COL_POSITION_STATUS = "position_status";

    public static final String COL_REMARK = "remark";

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