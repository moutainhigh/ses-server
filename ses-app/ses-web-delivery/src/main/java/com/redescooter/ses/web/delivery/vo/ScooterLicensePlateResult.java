package com.redescooter.ses.web.delivery.vo;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import io.swagger.annotations.*;

import java.math.BigDecimal;

/**
 * @ClassName:ScooterLicensePlateResult
 * @description: ScooterLicensePlateResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/01/07 11:43
 */
@ApiModel(value = "车牌号列表", description = "车牌号列表")
@Data //生成getter,setter等函数
public class ScooterLicensePlateResult extends GeneralResult {

    @ApiModelProperty(value = "车辆Id")
    private Long scooterId;

    @ApiModelProperty(value = "车牌号")
    private String scooterLicensePlate;

    @ApiModelProperty(value = "维度")
    private BigDecimal lat;

    @ApiModelProperty(value = "经度")
    private BigDecimal lng;

    @ApiModelProperty(value = "司机Id")
    private Long driverId;

    @ApiModelProperty(value = "司机姓名")
    private String driverFirstName;

    @ApiModelProperty(value = "司机姓名")
    private String driverLastName;

    @ApiModelProperty(value = "司机头像")
    private String driverPicture;
}
