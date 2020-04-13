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
 * @ClassName:ScooterRideDataResult
 * @description: ScooterRideDataResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/01/04 11:57
 */
@ApiModel(value = "车辆骑行数据出参", description = "车辆骑行数据出参")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@Data
public class ScooterRideDataResult extends GeneralResult {

    @ApiModelProperty(value = "公里数")
    private String totalMileage = "0";

    @ApiModelProperty(value = "节省的钱")
    private String totalMoney = "0";

    @ApiModelProperty(value = "平均公里数")
    private String avgMileage = "0";

    @ApiModelProperty(value = "节省co2")
    private String totalCo2 = "0";
}
