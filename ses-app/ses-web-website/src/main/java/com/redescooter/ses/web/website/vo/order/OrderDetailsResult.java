package com.redescooter.ses.web.website.vo.order;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author jerry
 * @Date 2021/1/6 3:42 上午
 * @Description 订单结果集出参
 **/

@ApiModel(value = "订单结果集出参", description = "订单结果集出参")
@Data
@EqualsAndHashCode(callSuper = true)
public class OrderDetailsResult extends GeneralResult {
}
