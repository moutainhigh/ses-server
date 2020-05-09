package com.redescooter.ses.web.delivery.dm;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

@ApiModel(value="com-redescooter-ses-web-delivery-dm-CorScooterRideStat")
@Data
@TableName(value = "cor_scooter_ride_stat")
public class CorScooterRideStat implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value="主键")
    private Long id;

    /**
     * 逻辑删除标识 0正常 1删除
     */
    @TableField(value = "dr")
    @TableLogic
    @ApiModelProperty(value="逻辑删除标识 0正常 1删除")
    private Integer dr;

    /**
     * 租户ID
     */
    @TableField(value = "tenant_id")
    @ApiModelProperty(value="租户ID")
    private Long tenantId;

    /**
     * 车辆主键
     */
    @TableField(value = "scooter_id")
    @ApiModelProperty(value="车辆主键")
    private Long scooterId;

    /**
     * 总时长
     */
    @TableField(value = "total_duration")
    @ApiModelProperty(value="总时长")
    private Long totalDuration;

    /**
     * 二氧化碳总量
     */
    @TableField(value = "co2_total")
    @ApiModelProperty(value="二氧化碳总量")
    private BigDecimal co2Total;

    /**
     * 二氧化碳增量值
     */
    @TableField(value = "co2_increment")
    @ApiModelProperty(value="二氧化碳增量值")
    private BigDecimal co2Increment;

    /**
     * 节省金额
     */
    @TableField(value = "saved_money")
    @ApiModelProperty(value="节省金额")
    private BigDecimal savedMoney;

    /**
     * 平均时速
     */
    @TableField(value = "svg_speed")
    @ApiModelProperty(value="平均时速")
    private BigDecimal svgSpeed;

    /**
     * 读取时间
     */
    @TableField(value = "read_time")
    @ApiModelProperty(value="读取时间")
    private Date readTime;

    /**
     * 总公里数
     */
    @TableField(value = "total_mileage")
    @ApiModelProperty(value="总公里数")
    private BigDecimal totalMileage;

    /**
     * 首次骑行时间
     */
    @TableField(value = "first_ride_time")
    @ApiModelProperty(value="首次骑行时间")
    private Date firstRideTime;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    @ApiModelProperty(value="创建时间")
    private Date createTime;

    /**
     * 创建人
     */
    @TableField(value = "create_by")
    @ApiModelProperty(value="创建人")
    private Long createBy;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    @ApiModelProperty(value="更新时间")
    private Date updateTime;

    /**
     * 更新人
     */
    @TableField(value = "update_by")
    @ApiModelProperty(value="更新人")
    private Long updateBy;

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
    @TableField(value = "def5")
    @ApiModelProperty(value="冗余字段")
    private String def5;

    /**
     * 冗余字段
     */
    @TableField(value = "def6")
    @ApiModelProperty(value="冗余字段")
    private Double def6;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_DR = "dr";

    public static final String COL_TENANT_ID = "tenant_id";

    public static final String COL_SCOOTER_ID = "scooter_id";

    public static final String COL_TOTAL_DURATION = "total_duration";

    public static final String COL_CO2_TOTAL = "co2_total";

    public static final String COL_CO2_INCREMENT = "co2_increment";

    public static final String COL_SAVED_MONEY = "saved_money";

    public static final String COL_SVG_SPEED = "svg_speed";

    public static final String COL_READ_TIME = "read_time";

    public static final String COL_TOTAL_MILEAGE = "total_mileage";

    public static final String COL_FIRST_RIDE_TIME = "first_ride_time";

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