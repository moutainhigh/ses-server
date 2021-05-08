package com.redescooter.ses.web.website.vo.order;

import com.redescooter.ses.api.common.vo.base.IdResult;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Description
 * @Author Chris
 * @Date 2021/5/8 14:01
 */
@Data
public class AddOrderResult extends IdResult {

    @ApiModelProperty(value = "定金")
    private BigDecimal deposit;

}
