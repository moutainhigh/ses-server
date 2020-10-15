package com.redescooter.ses.web.ros.vo.production;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import io.swagger.annotations.*;

/**
 * @ClassName:ProductionPartsEnter
 * @description: ProductionPartsEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/24 10:41
 */
@ApiModel(value = "采购部品列表入参", description = "采购部品列表入参")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class ProductionPartsEnter extends GeneralEnter {

    @ApiModelProperty(value = "产品Id")
    private Long id;

    @ApiModelProperty(value = "产品类型")
    private String productionProductType;

    @ApiModelProperty(value = "数量")
    private Integer qty;
}
