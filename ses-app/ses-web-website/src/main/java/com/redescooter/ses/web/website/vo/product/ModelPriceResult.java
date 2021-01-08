package com.redescooter.ses.web.website.vo.product;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @Author jerry
 * @Date 2021/1/6 9:16 上午
 * @Description 车型及价格结果集出参
 **/
@ApiModel(value = "车型及价格结果集出参", description = "车型及价格结果集出参")
@Data
@EqualsAndHashCode(callSuper = true)
public class ModelPriceResult extends GeneralResult {

    private ProductModelDetailsResult productModel;

    private List<ProductPriceDetailsResult> priceList;

}
