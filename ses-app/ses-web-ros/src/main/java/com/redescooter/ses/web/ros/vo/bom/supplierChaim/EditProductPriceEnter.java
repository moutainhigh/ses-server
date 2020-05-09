package com.redescooter.ses.web.ros.vo.bom.supplierChaim;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.web.ros.exception.ValidationExceptionCode;

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

    @ApiModelProperty(value = "id 产品主键Id", required = true)
    @NotNull(code = com.redescooter.ses.api.common.exception.ValidationExceptionCode.ID_IS_EMPTY, message = "id 为空")
    private Long id;

    @ApiModelProperty(value = "价格类型，指（整车、部品、零部件），传值见TAPD", required = true)
    @NotNull(code = ValidationExceptionCode.SALES_PRICE_TYPE_IS_EMPTY, message = "销售价格类型")
    private String priceType;

    @ApiModelProperty(value = "法国报价，若业务只有一个报价，默认只传法国报价", required = true)
    @NotNull(code = ValidationExceptionCode.PRODUCT_FR_PRICE_IS_EMPTY, message = "产品法国报价为空 为空")
    private String productFrPrice;

    @ApiModelProperty(value = "报价单位 只是第一次 设置报价的时候传")
    private String productFrUnit;
}
