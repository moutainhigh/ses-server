package com.redescooter.ses.web.ros.dm;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 委托单表
 */
@ApiModel(value = "com-redescooter-ses-web-ros-dm-OpeEntrustOrder")
@Data
@TableName(value = "ope_entrust_order")
public class OpeEntrustOrder implements Serializable {
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
     * 关联的发货单id
     */
    @TableField(value = "invoice_id")
    @ApiModelProperty(value = "关联的发货单id")
    private Long invoiceId;

    /**
     * 发货单号
     */
    @TableField(value = "invoice_no")
    @ApiModelProperty(value = "发货单号")
    private String invoiceNo;

    /**
     * 出库单号
     */
    @TableField(value = "entrust_no")
    @ApiModelProperty(value = "出库单号")
    private String entrustNo;

    /**
     * 委托单状态，0：待发货，10：待签收，20：已签收
     */
    @TableField(value = "entrust_status")
    @ApiModelProperty(value = "委托单状态，0：待发货，10：待签收，20：已签收")
    private Integer entrustStatus;

    /**
     * 委托单类型，1：整车，2：组装件，3：部件
     */
    @TableField(value = "entrust_type")
    @ApiModelProperty(value = "委托单类型，1：整车，2：组装件，3：部件")
    private Integer entrustType;

    /**
     * 发货数量
     */
    @TableField(value = "consignor_qty")
    @ApiModelProperty(value = "发货数量")
    private Integer consignorQty;

    /**
     * 交货日期
     */
    @TableField(value = "delivery_date")
    @ApiModelProperty(value = "交货日期")
    private Date deliveryDate;

    /**
     * 运输方式，1：海运，2：陆运，3：空运
     */
    @TableField(value = "trans_type")
    @ApiModelProperty(value = "运输方式，1：海运，2：陆运，3：空运")
    private Integer transType;

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
     * 发货人id
     */
    @TableField(value = "consignor_user")
    @ApiModelProperty(value = "发货人id")
    private Long consignorUser;

    /**
     * 发货人电话
     */
    @TableField(value = "consignor_telephone")
    @ApiModelProperty(value = "发货人电话")
    private String consignorTelephone;

    /**
     * 发货人邮箱
     */
    @TableField(value = "consignor_mail")
    @ApiModelProperty(value = "发货人邮箱")
    private String consignorMail;

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

    public static final String COL_INVOICE_ID = "invoice_id";

    public static final String COL_INVOICE_NO = "invoice_no";

    public static final String COL_ENTRUST_NO = "entrust_no";

    public static final String COL_ENTRUST_STATUS = "entrust_status";

    public static final String COL_ENTRUST_TYPE = "entrust_type";

    public static final String COL_CONSIGNOR_QTY = "consignor_qty";

    public static final String COL_DELIVERY_DATE = "delivery_date";

    public static final String COL_TRANS_TYPE = "trans_type";

    public static final String COL_CONSIGNEE_USER = "consignee_user";

    public static final String COL_CONSIGNEE_USER_TELEPHONE = "consignee_user_telephone";

    public static final String COL_CONSIGNEE_USER_MAIL = "consignee_user_mail";

    public static final String COL_CONSIGNEE_ADDRESS = "consignee_address";

    public static final String COL_CONSIGNEE_POST_CODE = "consignee_post_code";

    public static final String COL_CONSIGNOR_USER = "consignor_user";

    public static final String COL_CONSIGNOR_TELEPHONE = "consignor_telephone";

    public static final String COL_CONSIGNOR_MAIL = "consignor_mail";

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