package com.redescooter.ses.web.ros.dm;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
    * 销售车辆颜色表
    */
@ApiModel(value="com-redescooter-ses-web-ros-dm-OpeSaleScooterColor")
@Data
@TableName(value = "ope_sale_scooter_color")
public class OpeSaleScooterColor implements Serializable {
    /**
     * 销售车辆id
     */
    @TableField(value = "sale_scooter_id")
    @ApiModelProperty(value="销售车辆id")
    private Long saleScooterId;

    /**
     * 颜色id
     */
    @TableField(value = "color_id")
    @ApiModelProperty(value="颜色id")
    private Long colorId;

    public static final String COL_SALE_SCOOTER_ID = "sale_scooter_id";

    public static final String COL_COLOR_ID = "color_id";
}