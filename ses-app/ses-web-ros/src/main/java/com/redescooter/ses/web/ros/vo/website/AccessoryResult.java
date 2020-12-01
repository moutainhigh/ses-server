package com.redescooter.ses.web.ros.vo.website;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import io.swagger.annotations.*;

import java.math.BigDecimal;

/**
 * @ClassName:ScooterBatteryResult
 * @description: ScooterBatteryResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/05/12 16:24
 */
@ApiModel(value = "Battery accessories", description = "Battery accessories")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class AccessoryResult extends GeneralResult {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "Accessory battery type")
    private String accessoryType;

    @ApiModelProperty(value = "Name of accessory battery")
    private String accessoryName;

    @ApiModelProperty(value = "Price of accessory battery")
    private BigDecimal price;
}
