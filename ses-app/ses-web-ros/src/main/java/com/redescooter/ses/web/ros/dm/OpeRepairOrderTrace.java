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

@ApiModel(value = "com-redescooter-ses-web-ros-dm-OpeRepairOrderTrace")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "ope_repair_order_trace")
public class OpeRepairOrderTrace implements Serializable {
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
     * 维修店ID
     */
    @TableField(value = "repair_shop_id")
    @ApiModelProperty(value = "维修店ID")
    private Long repairShopId;

    /**
     * 用户id
     */
    @TableField(value = "user_id")
    @ApiModelProperty(value = "用户id")
    private Long userId;

    /**
     * 用户类型
     */
    @TableField(value = "user_type")
    @ApiModelProperty(value = "用户类型")
    private String userType;

    /**
     * 维修单主键
     */
    @TableField(value = "repair_order_id")
    @ApiModelProperty(value = "维修单主键")
    private Long repairOrderId;

    /**
     * 状态
     */
    @TableField(value = "status")
    @ApiModelProperty(value = "状态")
    private String status;

    /**
     * 事件
     */
    @TableField(value = "event")
    @ApiModelProperty(value = "事件")
    private String event;

    /**
     * 拒绝或者取消理由
     */
    @TableField(value = "reason")
    @ApiModelProperty(value = "拒绝或者取消理由")
    private String reason;

    /**
     * 经度
     */
    @TableField(value = "longitude")
    @ApiModelProperty(value = "经度")
    private BigDecimal longitude;

    /**
     * 维度
     */
    @TableField(value = "latitude")
    @ApiModelProperty(value = "维度")
    private BigDecimal latitude;

    /**
     * GEOHASH
     */
    @TableField(value = "geohash")
    @ApiModelProperty(value = "GEOHASH")
    private String geohash;

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

    public static final String COL_REPAIR_SHOP_ID = "repair_shop_id";

    public static final String COL_USER_ID = "user_id";

    public static final String COL_USER_TYPE = "user_type";

    public static final String COL_REPAIR_ORDER_ID = "repair_order_id";

    public static final String COL_STATUS = "status";

    public static final String COL_EVENT = "event";

    public static final String COL_REASON = "reason";

    public static final String COL_LONGITUDE = "longitude";

    public static final String COL_LATITUDE = "latitude";

    public static final String COL_GEOHASH = "geohash";

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