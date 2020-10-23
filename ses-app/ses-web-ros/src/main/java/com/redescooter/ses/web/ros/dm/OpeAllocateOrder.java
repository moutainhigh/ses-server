package com.redescooter.ses.web.ros.dm;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 调拨单表
 */
@ApiModel(value = "com-redescooter-ses-web-ros-dm-OpeAllocateOrder")
@Data
@TableName(value = "ope_allocate_order")
public class OpeAllocateOrder implements Serializable {
    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value = "主键id")
    private Long id;

    /**
     * 逻辑删除
     */
    @TableField(value = "dr")
    @ApiModelProperty(value = "逻辑删除")
    @TableLogic
    private Integer dr;

    /**
     * 租户ID
     */
    @TableField(value = "tenant_id")
    @ApiModelProperty(value = "租户ID")
    private Long tenantId;

    /**
     * 部门id（做数据权限用）
     */
    @TableField(value = "dept_id")
    @ApiModelProperty(value = "部门id（做数据权限用）")
    private Long deptId;

    /**
     * 调拨单号
     */
    @TableField(value = "allocate_no")
    @ApiModelProperty(value = "调拨单号")
    private String allocateNo;

    /**
     * 调拨单状态，0：草稿，10：待处理，20：采购中，30：待发货，40：待签收，50：已签收，60：已完成，70：已取消
     */
    @TableField(value = "allocate_status")
    @ApiModelProperty(value = "调拨单状态，0：草稿，10：待处理，20：采购中，30：待发货，40：待签收，50：已签收，60：已完成，70：已取消")
    private Integer allocateStatus;

    /**
     * 调拨数量
     */
    @TableField(value = "allocate_qty")
    @ApiModelProperty(value = "调拨数量")
    private Integer allocateQty;

    /**
     * 调拨单类型，1：整车，2：组装件，3：部件
     */
    @TableField(value = "allocate_type")
    @ApiModelProperty(value = "调拨单类型，1：整车，2：组装件，3：部件")
    private Integer allocateType;

    /**
     * 发货仓库id
     */
    @TableField(value = "ship_wh")
    @ApiModelProperty(value = "发货仓库id")
    private Long shipWh;

    /**
     * 接受仓库id
     */
    @TableField(value = "receipt_wh")
    @ApiModelProperty(value = "接受仓库id")
    private Long receiptWh;

    /**
     * 收货人id
     */
    @TableField(value = "consignee_user")
    @ApiModelProperty(value = "收货人id")
    private Long consigneeUser;

    /**
     * 收货人电话
     */
    @TableField(value = "consignee_user_telephone")
    @ApiModelProperty(value = "收货人电话")
    private String consigneeUserTelephone;

    /**
     * 收货人邮箱
     */
    @TableField(value = "consignee_user_mail")
    @ApiModelProperty(value = "收货人邮箱")
    private String consigneeUserMail;

    /**
     * 收获地址
     */
    @TableField(value = "consignee_address")
    @ApiModelProperty(value = "收获地址")
    private String consigneeAddress;

    /**
     * 收货地邮编
     */
    @TableField(value = "consignee_post_code")
    @ApiModelProperty(value = "收货地邮编")
    private String consigneePostCode;

    /**
     * 通知人id
     */
    @TableField(value = "notify_user")
    @ApiModelProperty(value = "通知人id")
    private Long notifyUser;

    /**
     * 通知人电话
     */
    @TableField(value = "notify_user_telephone")
    @ApiModelProperty(value = "通知人电话")
    private String notifyUserTelephone;

    /**
     * 通知人邮箱
     */
    @TableField(value = "notify_user_mail")
    @ApiModelProperty(value = "通知人邮箱")
    private String notifyUserMail;

    /**
     * 备注
     */
    @TableField(value = "remark")
    @ApiModelProperty(value = "备注")
    private String remark;

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
    private String def4;

    /**
     * 冗余字段
     */
    @TableField(value = "def5")
    @ApiModelProperty(value = "冗余字段")
    private BigDecimal def5;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_DR = "dr";

    public static final String COL_TENANT_ID = "tenant_id";

    public static final String COL_DEPT_ID = "dept_id";

    public static final String COL_ALLOCATE_NO = "allocate_no";

    public static final String COL_ALLOCATE_STATUS = "allocate_status";

    public static final String COL_ALLOCATE_QTY = "allocate_qty";

    public static final String COL_ALLOCATE_TYPE = "allocate_type";

    public static final String COL_SHIP_WH = "ship_wh";

    public static final String COL_RECEIPT_WH = "receipt_wh";

    public static final String COL_CONSIGNEE_USER = "consignee_user";

    public static final String COL_CONSIGNEE_USER_TELEPHONE = "consignee_user_telephone";

    public static final String COL_CONSIGNEE_USER_MAIL = "consignee_user_mail";

    public static final String COL_CONSIGNEE_ADDRESS = "consignee_address";

    public static final String COL_CONSIGNEE_POST_CODE = "consignee_post_code";

    public static final String COL_NOTIFY_USER = "notify_user";

    public static final String COL_NOTIFY_USER_TELEPHONE = "notify_user_telephone";

    public static final String COL_NOTIFY_USER_MAIL = "notify_user_mail";

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