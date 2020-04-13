package com.redescooter.ses.api.mobile.b.vo;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * @ClassName:DeliveryListResult
 * @description: DeliveryListResult
 * @author: Alex
 * @Version：1.3
 * @create: 2019/12/29 14:31
 */
@ApiModel(value = "列表出参", description = "列表出参")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
public class DeliveryListResult extends GeneralResult {

    @ApiModelProperty(value = "状态统计，1 待配送，2配送中，3拒单，4配送超时，5超时完成，6已送达，7失败（取消订单），8超时预警")
    private Map<String, Integer> countByStatus;

    @ApiModelProperty(value = "delivery列表")
    private List<DeliveryDetailResult> deliveryList;

}
