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
@ApiModel(value = "配送方式结果集出参", description = "配送方式结果集出参")
@Data
@EqualsAndHashCode(callSuper = true)
public class DeliveryModeResult extends GeneralResult {

    @ApiModelProperty(value = "配送方式编码")
    private String code;
    @ApiModelProperty(value = "配送方式唯一代码")
    private Integer value;
    @ApiModelProperty(value = "配送方式费用")
    private String cost;
}
