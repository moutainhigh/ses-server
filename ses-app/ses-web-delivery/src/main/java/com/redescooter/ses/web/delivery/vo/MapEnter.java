package com.redescooter.ses.web.delivery.vo;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

import io.swagger.annotations.*;

/**
 * @ClassName:MapEnter
 * @description: MapEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/01/07 09:55
 */
@ApiModel(value = "DeliveryMap 入参", description = "DeliveryMap 入参")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class MapEnter extends GeneralEnter {

    @ApiModelProperty(value = "订单状态列表")
    private List<String> statusList;
}
