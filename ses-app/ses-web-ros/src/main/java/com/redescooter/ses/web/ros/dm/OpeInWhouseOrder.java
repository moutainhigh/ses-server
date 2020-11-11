package com.redescooter.ses.web.ros.dm;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
    * 入库单表
    */
@ApiModel(value="com-redescooter-ses-web-ros-dm-OpeInWhouseOrder")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "ope_in_whouse_order")
public class OpeInWhouseOrder {
    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value="主键id")
    private Long id;

    /**
     * 逻辑删除
     */
    @TableField(value = "dr")
    @ApiModelProperty(value="逻辑删除")
    private Integer dr;

    /**
     * 租户ID
     */
    @TableField(value = "tenant_id")
    @ApiModelProperty(value="租户ID")
    private Long tenantId;

    /**
     * 部门id（做数据权限用）
     */
    @TableField(value = "dept_id")
    @ApiModelProperty(value="部门id（做数据权限用）")
    private Long deptId;

    /**
     * 入库单号
     */
    @TableField(value = "in_wh_no")
    @ApiModelProperty(value="入库单号")
    private String inWhNo;

    /**
     * 入库单状态， 1： 草稿，:10：待质检，20：质检中，30：已入库
     */
    @TableField(value = "combin_status")
    @ApiModelProperty(value="入库单状态， 1： 草稿，:10：待质检，20：质检中，30：已入库")
    private Integer combinStatus;

    /**
     * 关联的单据id
     */
    @TableField(value = "order_id")
    @ApiModelProperty(value="关联的单据id")
    private Long orderId;

    /**
     * 关联的单据号
     */
    @TableField(value = "order_no")
    @ApiModelProperty(value="关联的单据号")
    private String orderNo;

    /**
     * 关联的单据类型，1：生产采购单，2：组装单
     */
    @TableField(value = "order_type")
    @ApiModelProperty(value="关联的单据类型，1：生产采购单，2：组装单")
    private Integer orderType;

    /**
     * 入库类型，1：生产入库，2：返修入库，3：其他
     */
    @TableField(value = "in_wh_type")
    @ApiModelProperty(value="入库类型，1：生产入库，2：返修入库，3：其他")
    private Integer inWhType;

    /**
     * 入库数量
     */
    @TableField(value = "in_wh_qty")
    @ApiModelProperty(value="入库数量")
    private Integer inWhQty;

    /**
     * 备注
     */
    @TableField(value = "remark")
    @ApiModelProperty(value="备注")
    private String remark;

    /**
     * 创建人
     */
    @TableField(value = "created_by")
    @ApiModelProperty(value="创建人")
    private Long createdBy;

    /**
     * 创建时间
     */
    @TableField(value = "created_time")
    @ApiModelProperty(value="创建时间")
    private Date createdTime;

    /**
     * 更新人
     */
    @TableField(value = "updated_by")
    @ApiModelProperty(value="更新人")
    private Long updatedBy;

    /**
     * 更新时间
     */
    @TableField(value = "updated_time")
    @ApiModelProperty(value="更新时间")
    private Date updatedTime;

    /**
     * 冗余字段
     */
    @TableField(value = "def1")
    @ApiModelProperty(value="冗余字段")
    private String def1;

    /**
     * 冗余字段
     */
    @TableField(value = "def2")
    @ApiModelProperty(value="冗余字段")
    private String def2;

    /**
     * 冗余字段
     */
    @TableField(value = "def3")
    @ApiModelProperty(value="冗余字段")
    private String def3;

    /**
     * 冗余字段
     */
    @TableField(value = "def4")
    @ApiModelProperty(value="冗余字段")
    private String def4;

    /**
     * 冗余字段
     */
    @TableField(value = "def5")
    @ApiModelProperty(value="冗余字段")
    private BigDecimal def5;

    public static final String COL_ID = "id";

    public static final String COL_DR = "dr";

    public static final String COL_TENANT_ID = "tenant_id";

    public static final String COL_DEPT_ID = "dept_id";

    public static final String COL_IN_WH_NO = "in_wh_no";

    public static final String COL_COMBIN_STATUS = "combin_status";

    public static final String COL_ORDER_ID = "order_id";

    public static final String COL_ORDER_NO = "order_no";

    public static final String COL_ORDER_TYPE = "order_type";

    public static final String COL_IN_WH_TYPE = "in_wh_type";

    public static final String COL_IN_WH_QTY = "in_wh_qty";

    public static final String COL_REMARK = "remark";

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