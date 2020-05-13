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
@ApiModel(value = "车辆型号", description = "车辆型号")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class ProductResult extends GeneralResult {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "型号")
    private String productModel;

    @ApiModelProperty(value = "颜色")
    private String color;

    @ApiModelProperty(value = "价格")
    private BigDecimal price;
}
