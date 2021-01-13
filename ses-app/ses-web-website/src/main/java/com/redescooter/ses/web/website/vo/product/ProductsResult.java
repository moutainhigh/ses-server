package com.redescooter.ses.web.website.vo.product;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @Author jerry
 * @Date 2021/1/6 1:33 下午
 * @Description 产品详情结果集
 **/
@ApiModel(value = "Product details result set", description = "产品详情结果集")
@Data
@EqualsAndHashCode(callSuper = true)
public class ProductsResult extends ProductDetailsResult {

    /**
     * 颜色集合
     */
    private List<ColourDetailsResult> colourDetailslist;
}
