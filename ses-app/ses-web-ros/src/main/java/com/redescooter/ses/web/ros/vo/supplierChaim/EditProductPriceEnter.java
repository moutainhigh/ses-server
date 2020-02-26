package com.redescooter.ses.web.ros.vo.supplierChaim;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @ClassName:EditProductPriceEnter
 * @description: EditProductPriceEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/02/25 15:38
 */
@ApiModel(value = "产品价格修改", description = "产品价格修改")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class EditProductPriceEnter extends GeneralEnter {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "产品价格")
    private String productPrice;

    @ApiModelProperty(value = "货币单位，只是第一次 设置报价的时候传")
    private String unit;
}
