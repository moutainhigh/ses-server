package com.redescooter.ses.mobile.rps.vo.restproductionorder.outbound;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import lombok.*;

import java.util.Date;

import io.swagger.annotations.*;

/**
 * @ClassName:ProductSnResult
 * @description: ProductSnResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/11/02 13:42 
 */
@ApiModel(value = "", description = "")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
public class ProductSnResult extends GeneralResult {

    @ApiModelProperty(value = "序列号")
    private String sn;

    @ApiModelProperty(value = "到货时间")
    private Date arrivalDate;

    @ApiModelProperty(value = "批次号")
    private String lot;

    @ApiModelProperty(value = "供应商Id")
    private Long supplierId;

    @ApiModelProperty(value = "供应商名称")
    private String supplierName;

    @ApiModelProperty(value = "数量")
    private Integer qty;

    @ApiModelProperty(value = "附件")
    private String annex;
}
