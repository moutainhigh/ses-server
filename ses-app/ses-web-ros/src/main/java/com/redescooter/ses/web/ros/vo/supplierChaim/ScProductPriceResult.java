package com.redescooter.ses.web.ros.vo.supplierChaim;

import java.util.Date;

import com.redescooter.ses.api.common.vo.base.GeneralResult;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @ClassName:ProductPriceResult
 * @description: ProductPriceResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/02/25 15:22
 */
@ApiModel(value = "产品价格", description = "产品价格")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class ScProductPriceResult extends GeneralResult {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "产品价格")
    private String productPrice;

    @ApiModelProperty(value = "更新时间")
    private Date refuseTime;
}
