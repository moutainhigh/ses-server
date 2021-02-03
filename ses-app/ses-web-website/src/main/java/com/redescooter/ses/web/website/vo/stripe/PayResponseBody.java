package com.redescooter.ses.web.website.vo.stripe;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author jerry
 * @Date 2021/1/24 1:52 上午
 * @Description stripeResponseBody
 **/
@ApiModel(value = "stripeResponseBody", description = "stripeResponseBody")
@Data
public class PayResponseBody {
    private String clientSecret;
    private Boolean requiresAction;
    private String error;
}
