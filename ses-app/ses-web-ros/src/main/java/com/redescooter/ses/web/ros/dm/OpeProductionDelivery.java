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

@ApiModel(value = "com-redescooter-ses-web-ros-dm-OpeProductionDelivery")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "ope_production_delivery")
public class OpeProductionDelivery implements Serializable {
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
     * 仓库ID
     */
    @TableField(value = "warehouse_id")
    @ApiModelProperty(value = "仓库ID")
    private Long warehouseId;

    /**
     * 订单号
     */
    @TableField(value = "order_no")
    @ApiModelProperty(value = "订单号")
    private String orderNo;

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
     * 物流编号
     */
    @TableField(value = "logistics_no")
    @ApiModelProperty(value = "物流编号")
    private String logisticsNo;

    /**
     * 目的城市
     */
    @TableField(value = "destination_city")
    @ApiModelProperty(value = "目的城市")
    private Long destinationCity;

    /**
     * 发货人
     */
    @TableField(value = "delivery_officer")
    @ApiModelProperty(value = "发货人")
    private Long deliveryOfficer;

    /**
     * 发货人电话
     */
    @TableField(value = "delivery_officer_phone")
    @ApiModelProperty(value = "发货人电话")
    private String deliveryOfficerPhone;

    /**
     * 收件人
     */
    @TableField(value = "consignee_officer")
    @ApiModelProperty(value = "收件人")
    private Long consigneeOfficer;

    /**
     * 收件人电话
     */
    @TableField(value = "consignee_officer_phone")
    @ApiModelProperty(value = "收件人电话")
    private String consigneeOfficerPhone;

    /**
     * 数量
     */
    @TableField(value = "quantity")
    @ApiModelProperty(value = "数量")
    private Integer quantity;

    /**
     * 预计离港时间
     */
    @TableField(value = "etd")
    @ApiModelProperty(value = "预计离港时间")
    private Date etd;

    /**
     * 预计到港时间
     */
    @TableField(value = "eta")
    @ApiModelProperty(value = "预计到港时间")
    private Date eta;

    /**
     * 实际离港时间
     */
    @TableField(value = "atd")
    @ApiModelProperty(value = "实际离港时间")
    private Date atd;

    /**
     * 实际到港时间
     */
    @TableField(value = "ata")
    @ApiModelProperty(value = "实际到港时间")
    private Date ata;

    /**
     * 离开港口
     */
    @TableField(value = "departure_port")
    @ApiModelProperty(value = "离开港口")
    private String departurePort;

    /**
     * 到达港口
     */
    @TableField(value = "arrival_port")
    @ApiModelProperty(value = "到达港口")
    private String arrivalPort;

    /**
     * 附件
     */
    @TableField(value = "attachment")
    @ApiModelProperty(value = "附件")
    private String attachment;

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

    public static final String COL_WAREHOUSE_ID = "warehouse_id";

    public static final String COL_ORDER_NO = "order_no";

    public static final String COL_STATUS = "status";

    public static final String COL_LOGISTICS_COMPANY = "logistics_company";

    public static final String COL_LOGISTICS_NO = "logistics_no";

    public static final String COL_DESTINATION_CITY = "destination_city";

    public static final String COL_DELIVERY_OFFICER = "delivery_officer";

    public static final String COL_DELIVERY_OFFICER_PHONE = "delivery_officer_phone";

    public static final String COL_CONSIGNEE_OFFICER = "consignee_officer";

    public static final String COL_CONSIGNEE_OFFICER_PHONE = "consignee_officer_phone";

    public static final String COL_QUANTITY = "quantity";

    public static final String COL_ETD = "etd";

    public static final String COL_ETA = "eta";

    public static final String COL_ATD = "atd";

    public static final String COL_ATA = "ata";

    public static final String COL_DEPARTURE_PORT = "departure_port";

    public static final String COL_ARRIVAL_PORT = "arrival_port";

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