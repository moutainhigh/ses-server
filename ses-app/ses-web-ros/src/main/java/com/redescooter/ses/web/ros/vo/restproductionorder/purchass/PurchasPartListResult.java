package com.redescooter.ses.web.ros.vo.restproductionorder.purchass;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import lombok.*;

import java.math.BigDecimal;

import io.swagger.annotations.*;

/**
 * @ClassName:PurchasPartListEnter
 * @description: PurchasPartListEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/11/12 10:27 
 */
@ApiModel(value = "可采购的部件列表出参", description = "可采购的部件列表出参")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
public class PurchasPartListResult extends GeneralResult {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "部品Id ")
    private Long partId;

    @ApiModelProperty(value = "部件类型")
    private Integer productType;

    @ApiModelProperty(value = "名字")
    private String enName;

    @ApiModelProperty(value = "编号")
    private String partsN;

    @ApiModelProperty(value = "价格")
    private BigDecimal price;

    @ApiModelProperty(value = "单位")
    private Integer unit;

}
