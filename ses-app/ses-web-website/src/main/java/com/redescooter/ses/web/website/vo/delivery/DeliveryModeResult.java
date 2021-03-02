package com.redescooter.ses.web.website.vo.delivery;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

/**
 * @Author jerry
 * @Date 2021/1/6 7:59 下午
 * @Description 配送方式结果集出参
 **/
@ApiModel(value = "Parameters of distribution method result set", description = "配送方式结果集出参")
@Data
@EqualsAndHashCode(callSuper = true)
public class DeliveryModeResult extends GeneralResult {

    @ApiModelProperty(value = "Delivery mode code")
    private String code;
    @ApiModelProperty(value = "Delivery method unique code")
    private Integer value;
    @ApiModelProperty(value = "Cost of delivery mode")
    private String cost;
}
