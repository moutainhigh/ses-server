package com.redescooter.ses.mobile.rps.vo.restproductionorder.consign;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import lombok.*;

import io.swagger.annotations.*;

/**
 * @ClassName:ConsignShipProductEnter
 * @description: ConsignShipProductEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/11/03 19:40 
 */
@ApiModel(value = "出库单出库", description = "出库单出库")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
public class ConsignShipProductEnter extends GeneralEnter {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "是否存在序列号")
    private Boolean idclass;

    @ApiModelProperty(value = "序列号")
    private String serialN;

    @ApiModelProperty(value = "数量")
    private Integer qty;
}
