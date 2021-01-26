package com.redescooter.ses.mobile.rps.vo.restproductionorder.invoice;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import lombok.*;

import io.swagger.annotations.*;

/**
 * @ClassName:InvoiceUpdateStatusEnter
 * @description: InvoiceUpdateStatusEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/11/05 16:53 
 */
@ApiModel(value = "", description = "")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
public class InvoiceUpdateStatusEnter extends GeneralEnter {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "操作动态")
    private Integer operatingDynamics;
}
