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
 * @ClassName:ListScooterResult
 * @description: ListScooterResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/01/02 18:21
 */
@ApiModel(value = "门店车辆列表", description = "门店车辆列表")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class ListScooterResult extends GeneralResult {

    @ApiModelProperty(value = "id")
    private Long id;
    @ApiModelProperty(value = "车辆型号")
    private String model;

    @ApiModelProperty(value = "车牌号")
    private String scooterLicense;

    @ApiModelProperty(value = "电量")
    private Integer battery;
}
