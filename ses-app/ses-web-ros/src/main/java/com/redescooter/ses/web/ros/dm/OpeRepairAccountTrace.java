package com.redescooter.ses.web.ros.dm;

import com.baomidou.mybatisplus.annotation.IdType;
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

import java.io.Serializable;
import java.util.Date;

@ApiModel(value = "com-redescooter-ses-web-ros-dm-OpeRepairAccountTrace")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "ope_repair_account_trace")
public class OpeRepairAccountTrace implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value = "主键")
    private Long id;

    /**
     * 逻辑删除标识 0正常 1删除
     */
    @TableField(value = "dr")
    @TableLogic
    @ApiModelProperty(value = "逻辑删除标识 0正常 1删除")
    private Integer dr;

    /**
     * 部门id
     */
    @TableField(value = "org_department_id")
    @ApiModelProperty(value = "部门id")
    private Integer orgDepartmentId;

    /**
     * 员工Id
     */
    @TableField(value = "org_staff_id")
    @ApiModelProperty(value = "员工Id")
    private Long orgStaffId;

    /**
     * 维修店主键
     */
    @TableField(value = "repair_shop_id")
    @ApiModelProperty(value = "维修店主键")
    private Long repairShopId;

    /**
     * 事件
     */
    @TableField(value = "event")
    @ApiModelProperty(value = "事件")
    private String event;

    /**
     * 事件时间
     */
    @TableField(value = "event_time")
    @ApiModelProperty(value = "事件时间")
    private Date eventTime;

    /**
     * 备注 放话术参数
     */
    @TableField(value = "memo")
    @ApiModelProperty(value = "备注 放话术参数")
    private String memo;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    /**
     * 创建人
     */
    @TableField(value = "create_by")
    @ApiModelProperty(value = "创建人")
    private Long createBy;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    /**
     * 更新人
     */
    @TableField(value = "update_by")
    @ApiModelProperty(value = "更新人")
    private Long updateBy;

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
    @TableField(value = "def5")
    @ApiModelProperty(value = "冗余字段")
    private String def5;

    /**
     * 冗余字段
     */
    @TableField(value = "def6")
    @ApiModelProperty(value = "冗余字段")
    private Double def6;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_DR = "dr";

    public static final String COL_ORG_DEPARTMENT_ID = "org_department_id";

    public static final String COL_ORG_STAFF_ID = "org_staff_id";

    public static final String COL_REPAIR_SHOP_ID = "repair_shop_id";

    public static final String COL_EVENT = "event";

    public static final String COL_EVENT_TIME = "event_time";

    public static final String COL_MEMO = "memo";

    public static final String COL_CREATE_TIME = "create_time";

    public static final String COL_CREATE_BY = "create_by";

    public static final String COL_UPDATE_TIME = "update_time";

    public static final String COL_UPDATE_BY = "update_by";

    public static final String COL_DEF1 = "def1";

    public static final String COL_DEF2 = "def2";

    public static final String COL_DEF3 = "def3";

    public static final String COL_DEF5 = "def5";

    public static final String COL_DEF6 = "def6";
}