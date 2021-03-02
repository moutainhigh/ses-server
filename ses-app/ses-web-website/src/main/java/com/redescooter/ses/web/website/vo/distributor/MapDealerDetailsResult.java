package com.redescooter.ses.web.website.vo.distributor;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * @Author jerry
 * @Date 2021/1/6 3:44 上午
 * @Description 经销商结果集出参
 **/
@ApiModel(value = "Map Dealer Details ", description = "新增经销商入参")
@Data
@EqualsAndHashCode(callSuper = true)
public class MapDealerDetailsResult extends DealerDetailsResult {

    /**
     * 距离
     */
    @ApiModelProperty(value = "distance")
    private  String distance;
}
