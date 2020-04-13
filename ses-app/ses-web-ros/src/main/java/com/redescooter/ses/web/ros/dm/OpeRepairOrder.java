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
import java.math.BigDecimal;
import java.util.Date;

@ApiModel(value = "com-redescooter-ses-web-ros-dm-OpeRepairOrder")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "ope_repair_order")
public class OpeRepairOrder implements Serializable {
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
     * 店铺ID
     */
    @TableField(value = "repair_shop_id")
    @ApiModelProperty(value = "店铺ID")
    private Long repairShopId;

    /**
     * 订单号
     */
    @TableField(value = "order_no")
    @ApiModelProperty(value = "订单号")
    private String orderNo;

    /**
     * 客户ID
     */
    @TableField(value = "customer_id")
    @ApiModelProperty(value = "客户ID")
    private Long customerId;

    /**
     * 顾客姓名
     */
    @TableField(value = "customer_name")
    @ApiModelProperty(value = "顾客姓名")
    private String customerName;

    /**
     * 负责人id
     */
    @TableField(value = "principal_id")
    @ApiModelProperty(value = "负责人id")
    private Integer principalId;

    /**
     * systemId
     */
    @TableField(value = "system_id")
    @ApiModelProperty(value = "systemId")
    private String systemId;

    /**
     * appId
     */
    @TableField(value = "app_id")
    @ApiModelProperty(value = "appId")
    private String appId;

    /**
     * 客户类型ENTERPRISE 、PERSONAL 公司、个人
     */
    @TableField(value = "customer_type")
    @ApiModelProperty(value = "客户类型ENTERPRISE 、PERSONAL 公司、个人")
    private String customerType;

    /**
     * 预约时间
     */
    @TableField(value = "repair_appointment_time")
    @ApiModelProperty(value = "预约时间")
    private Date repairAppointmentTime;

    /**
     * 维修接受时间
     */
    @TableField(value = "repair_accept_time")
    @ApiModelProperty(value = "维修接受时间")
    private String repairAcceptTime;

    /**
     * 维修完成时间
     */
    @TableField(value = "repair_complete_time")
    @ApiModelProperty(value = "维修完成时间")
    private String repairCompleteTime;

    /**
     * 维修总计时长
     */
    @TableField(value = "repair_duration")
    @ApiModelProperty(value = "维修总计时长")
    private String repairDuration;

    /**
     * 维修类型 MAINTENANCE、REPAIR、ACCESSORIES 保养、维修、配件
     */
    @TableField(value = "repair_type")
    @ApiModelProperty(value = "维修类型 MAINTENANCE、REPAIR、ACCESSORIES 保养、维修、配件")
    private String repairType;

    /**
     * 服务类型：Visit上门、Reserve预约
     */
    @TableField(value = "service_type")
    @ApiModelProperty(value = "服务类型：Visit上门、Reserve预约")
    private String serviceType;

    /**
     * 支付类型：Self-pay自费，Maintenance维保
     */
    @TableField(value = "pay_type")
    @ApiModelProperty(value = "支付类型：Self-pay自费，Maintenance维保")
    private String payType;

    /**
     * 费用
     */
    @TableField(value = "amount")
    @ApiModelProperty(value = "费用")
    private String amount;

    /**
     * 车辆数量
     */
    @TableField(value = "scooter_quantity")
    @ApiModelProperty(value = "车辆数量")
    private Integer scooterQuantity;

    /**
     * 用户联系电话
     */
    @TableField(value = "contact_number")
    @ApiModelProperty(value = "用户联系电话")
    private String contactNumber;

    /**
     * 用户联系地址
     */
    @TableField(value = "contact_address")
    @ApiModelProperty(value = "用户联系地址")
    private String contactAddress;

    /**
     * 国家ID
     */
    @TableField(value = "country_id")
    @ApiModelProperty(value = "国家ID")
    private Long countryId;

    /**
     * 城市ID
     */
    @TableField(value = "city_id")
    @ApiModelProperty(value = "城市ID")
    private Long cityId;

    /**
     * 区域ID
     */
    @TableField(value = "area_id")
    @ApiModelProperty(value = "区域ID")
    private Long areaId;

    /**
     * placeId 前端解析地址方便
     */
    @TableField(value = "place_id")
    @ApiModelProperty(value = "placeId 前端解析地址方便")
    private String placeId;

    /**
     * 经度，即客户的经度
     */
    @TableField(value = "longitude")
    @ApiModelProperty(value = "经度，即客户的经度")
    private BigDecimal longitude;

    /**
     * 维度，即客户的维度
     */
    @TableField(value = "latitude")
    @ApiModelProperty(value = "维度，即客户的维度")
    private BigDecimal latitude;

    /**
     * 备注说明
     */
    @TableField(value = "notes")
    @ApiModelProperty(value = "备注说明")
    private String notes;

    /**
     * 完成时备注
     */
    @TableField(value = "complete_notes")
    @ApiModelProperty(value = "完成时备注")
    private String completeNotes;

    /**
     * 状态：Pending待处理，ongoing进行中，completed已完成，rejected已拒绝，cancelled已取消
     */
    @TableField(value = "status")
    @ApiModelProperty(value = "状态：Pending待处理，ongoing进行中，completed已完成，rejected已拒绝，cancelled已取消")
    private String status;

    /**
     * 理由：拒绝或者取消理由
     */
    @TableField(value = "termination_reason")
    @ApiModelProperty(value = "理由：拒绝或者取消理由")
    private String terminationReason;

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
     * 再次预约的标记 表示订单只能被再次预约一次
     */
    @TableField(value = "reservation_mark")
    @ApiModelProperty(value = "再次预约的标记 表示订单只能被再次预约一次")
    private Boolean reservationMark;

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

    public static final String COL_REPAIR_SHOP_ID = "repair_shop_id";

    public static final String COL_ORDER_NO = "order_no";

    public static final String COL_CUSTOMER_ID = "customer_id";

    public static final String COL_CUSTOMER_NAME = "customer_name";

    public static final String COL_PRINCIPAL_ID = "principal_id";

    public static final String COL_SYSTEM_ID = "system_id";

    public static final String COL_APP_ID = "app_id";

    public static final String COL_CUSTOMER_TYPE = "customer_type";

    public static final String COL_REPAIR_APPOINTMENT_TIME = "repair_appointment_time";

    public static final String COL_REPAIR_ACCEPT_TIME = "repair_accept_time";

    public static final String COL_REPAIR_COMPLETE_TIME = "repair_complete_time";

    public static final String COL_REPAIR_DURATION = "repair_duration";

    public static final String COL_REPAIR_TYPE = "repair_type";

    public static final String COL_SERVICE_TYPE = "service_type";

    public static final String COL_PAY_TYPE = "pay_type";

    public static final String COL_AMOUNT = "amount";

    public static final String COL_SCOOTER_QUANTITY = "scooter_quantity";

    public static final String COL_CONTACT_NUMBER = "contact_number";

    public static final String COL_CONTACT_ADDRESS = "contact_address";

    public static final String COL_COUNTRY_ID = "country_id";

    public static final String COL_CITY_ID = "city_id";

    public static final String COL_AREA_ID = "area_id";

    public static final String COL_PLACE_ID = "place_id";

    public static final String COL_LONGITUDE = "longitude";

    public static final String COL_LATITUDE = "latitude";

    public static final String COL_NOTES = "notes";

    public static final String COL_COMPLETE_NOTES = "complete_notes";

    public static final String COL_STATUS = "status";

    public static final String COL_TERMINATION_REASON = "termination_reason";

    public static final String COL_CREATED_BY = "created_by";

    public static final String COL_CREATED_TIME = "created_time";

    public static final String COL_UPDATED_BY = "updated_by";

    public static final String COL_UPDATED_TIME = "updated_time";

    public static final String COL_RESERVATION_MARK = "reservation_mark";

    public static final String COL_DEF1 = "def1";

    public static final String COL_DEF2 = "def2";

    public static final String COL_DEF3 = "def3";

    public static final String COL_DEF5 = "def5";

    public static final String COL_DEF6 = "def6";
}