package com.redescooter.ses.api.common.vo.count;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

/**
 * @ClassName:ScooterCountResult
 * @description: ScooterCountResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/12/17 18:50
 */
@Data
@ApiModel(value = "车辆销售统计")
public class ScooterCountResult extends GeneralResult {

    @ApiModelProperty("E50")
    private List<OrderCountResult> scooterE50s;

    @ApiModelProperty("E100")
    private List<OrderCountResult> scooterE100s;

    @ApiModelProperty("E125")
    private List<OrderCountResult> scooterE125s;
}
