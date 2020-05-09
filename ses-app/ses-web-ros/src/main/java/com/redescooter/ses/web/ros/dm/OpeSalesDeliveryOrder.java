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

@ApiModel(value = "com-redescooter-ses-web-ros-dm-OpeSalesDeliveryOrder")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "ope_sales_delivery_order")
public class OpeSalesDeliveryOrder implements Serializable {
    /**
     * ID
     */
    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value = "ID")
    private Long id;

    /**
     * 逻辑删除标识 0正常 1删除
     */
    @TableField(value = "dr")
    @TableLogic
    @ApiModelProperty(value = "逻辑删除标识 0正常 1删除")
    private Integer dr;

    /**
     * 订单号
     */
    @TableField(value = "order_no")
    @ApiModelProperty(value = "订单号")
    private String orderNo;

    /**
     * 销售订单ID
     */
    @TableField(value = "sales_order_id")
    @ApiModelProperty(value = "销售订单ID")
    private Long salesOrderId;

    /**
     * 发货人Id
     */
    @TableField(value = "send_id")
    @ApiModelProperty(value = "发货人Id")
    private Long sendId;

    /**
     * 发货人电话
     */
    @TableField(value = "send_phone")
    @ApiModelProperty(value = "发货人电话")
    private String sendPhone;

    /**
     * 状态
     */
    @TableField(value = "status")
    @ApiModelProperty(value = "状态")
    private String status;

    /**
     * 物流公司
     */
    @TableField(value = "logistics_company")
    @ApiModelProperty(value = "物流公司")
    private String logisticsCompany;

    /**
     * 快递单号
     */
    @TableField(value = "express_no")
    @ApiModelProperty(value = "快递单号")
    private String expressNo;

    /**
     * 数量
     */
    @TableField(value = "quantity")
    @ApiModelProperty(value = "数量")
    private Integer quantity;

    /**
     * 配送时间
     */
    @TableField(value = "delivery_time")
    @ApiModelProperty(value = "配送时间")
    private Date deliveryTime;

    /**
     * 附件地址
     */
    @TableField(value = "attachment")
    @ApiModelProperty(value = "附件地址")
    private String attachment;

    /**
     * 创建人
     */
    @TableField(value = "created_by")
    @ApiModelProperty(value = "创建人")
    private Integer createdBy;

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
    private Integer updatedBy;

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

    public static final String COL_ORDER_NO = "order_no";

    public static final String COL_SALES_ORDER_ID = "sales_order_id";

    public static final String COL_SEND_ID = "send_id";

    public static final String COL_SEND_PHONE = "send_phone";

    public static final String COL_STATUS = "status";

    public static final String COL_LOGISTICS_COMPANY = "logistics_company";

    public static final String COL_EXPRESS_NO = "express_no";

    public static final String COL_QUANTITY = "quantity";

    public static final String COL_DELIVERY_TIME = "delivery_time";

    public static final String COL_ATTACHMENT = "attachment";

    public static final String COL_CREATED_BY = "created_by";

    public static final String COL_CREATED_TIME = "created_time";

    public static final String COL_UPDATED_BY = "updated_by";

    public static final String COL_UPDATED_TIME = "updated_time";

    public static final String COL_DEF1 = "def1";

    public static final String COL_DEF2 = "def2";

    public static final String COL_DEF3 = "def3";

    public static final String COL_DEF5 = "def5";

    public static final String COL_DEF6 = "def6";
}