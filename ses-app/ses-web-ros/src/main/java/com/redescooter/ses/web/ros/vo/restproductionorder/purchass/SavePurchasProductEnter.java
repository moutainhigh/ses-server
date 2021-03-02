package com.redescooter.ses.web.ros.vo.restproductionorder.purchass;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import lombok.*;

import io.swagger.annotations.*;

import java.math.BigDecimal;

/**
 * @ClassName:SavePurchasProductEnter
 * @description: SavePurchasProductEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/11/11 17:36
 */
@ApiModel(value = "采购单产品入参", description = "采购单产品入参")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
public class SavePurchasProductEnter extends GeneralResult {

    @ApiModelProperty(value = "部件Id")
    private Long id;

    @ApiModelProperty(value = "数量")
    private Integer qty;

    @ApiModelProperty(value = "单价价格")
    private BigDecimal unitPrice;

}
