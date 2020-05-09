package com.redescooter.ses.web.delivery.vo;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @ClassName:ScooterMapResult
 * @description: ScooterMapResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/01/04 15:03
 */
@ApiModel(value = "Map车辆出参", description = "Map车辆出参")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
@Data
public class ScooterMapResult extends GeneralResult {

    @ApiModelProperty(value = "车辆Id")
    private Long id;

    @ApiModelProperty(value = "纬度")
    private String lat;

    @ApiModelProperty(value = "经度")
    private String lng;

    @ApiModelProperty(value = "电池")
    private Integer battery;

    @ApiModelProperty(value = "车辆状态")
    private String status;

    @ApiModelProperty(value = "车牌")
    private String licensePlate;

    @ApiModelProperty(value = "司机Id")
    private Long driverId;

    @ApiModelProperty(value = "司机名")
    private String driverFirstName;

    @ApiModelProperty(value = "司机姓")
    private String driverLastName;

    @ApiModelProperty(value = "头像")
    private String avatar;
}
