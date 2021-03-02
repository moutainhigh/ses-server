package com.redescooter.ses.mobile.rps.vo.restproductionorder.orderflow;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import lombok.*;

import io.swagger.annotations.*;

/**
 * @ClassName:ProductQcDetailEnter
 * @description: ProductQcDetailEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/11/02 18:21 
 */
@ApiModel(value = "产品出库详情", description = "产品出库详情")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
public class ProductOutWhDetailEnter extends GeneralEnter {

    @ApiModelProperty(value = "字单据Id")
    private Long id;

    @ApiModelProperty(value = "")
    private Integer productType;
}
