package com.redescooter.ses.web.ros.vo.website;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import io.swagger.annotations.*;

import java.math.BigDecimal;

/**
 * @ClassName:ScooterTypeResult
 * @description: ScooterTypeResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/05/12 15:57
 */
@ApiModel(value = "Vehicle model", description = "Vehicle model")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class ProductResult extends GeneralResult {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "name")
    private String name;

    @ApiModelProperty(value = "model")
    private String productModel;

    @ApiModelProperty(value = "color")
    private String color;

    @ApiModelProperty(value = "price")
    private BigDecimal price;
}
