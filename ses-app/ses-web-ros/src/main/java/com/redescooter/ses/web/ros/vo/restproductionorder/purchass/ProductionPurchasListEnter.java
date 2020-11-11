package com.redescooter.ses.web.ros.vo.restproductionorder.purchass;

import com.redescooter.ses.api.common.vo.base.PageEnter;
import lombok.*;

import io.swagger.annotations.*;

/**
 * @ClassName:ProductionPurchasListEnter
 * @description: ProductionPurchasListEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/11/10 14:51 
 */
@ApiModel(value = "采购单列表", description = "采购单列表")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
public class ProductionPurchasListEnter extends PageEnter {

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "关键字")
    private String keyword;
}
