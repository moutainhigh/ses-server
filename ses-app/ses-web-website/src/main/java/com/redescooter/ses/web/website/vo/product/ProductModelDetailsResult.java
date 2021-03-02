package com.redescooter.ses.web.website.vo.product;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * @Author jerry
 * @Date 2021/1/5 6:00 下午
 * @Description 产品型号结果出参
 **/
@ApiModel(value = "Product model results out of reference", description = "产品型号结果出参")
@Data
@EqualsAndHashCode(callSuper = true)
public class ProductModelDetailsResult extends GeneralResult {

    /**
     * 主键
     */
    @ApiModelProperty(value = "ID")
    private Long modelid;

    /**
     * 产品类型单项名称
     */
    @ApiModelProperty(value = "Model Name")
    private String productModelName;


}
