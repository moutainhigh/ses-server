package com.redescooter.ses.web.ros.vo.bom.scooter;

import com.redescooter.ses.api.common.vo.base.GeneralResult;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @ClassName:ScooterListResult
 * @description: ScooterListResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/02/25 10:26
 */
@ApiModel(value = "bom 车辆列表出参", description = "bom 车辆列表出参")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class ScooterListResult extends GeneralResult {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "产品编号")
    private String productN;

    @ApiModelProperty(value = "产品名称")
    private String productCnName;

    @ApiModelProperty(value = "产品名称")
    private String productEnName;

    @ApiModelProperty(value = "产品名称")
    private String productFrName;

    @ApiModelProperty(value = "数量")
    private int qty;

    @ApiModelProperty(value = "生产周期")
    private String productionCycle;
}
