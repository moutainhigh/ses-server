package com.redescooter.ses.mobile.rps.vo.restproductionorder.outbound;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import lombok.*;

import java.util.Date;

import io.swagger.annotations.*;
import org.springframework.format.annotation.DateTimeFormat;

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

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "字单据Id")
    private Long orderBId;

    @ApiModelProperty(value = "序列号")
    private String serialN;

    @ApiModelProperty(value = "到货时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Date arrivalDate;

    @ApiModelProperty(value = "批次号")
    private String lot;

    @ApiModelProperty(value = "供应商Id")
    private Long supplierId;

    @ApiModelProperty(value = "供应商名称")
    private String supplierName;

    @ApiModelProperty(value = "数量")
    private Integer qty;

    @ApiModelProperty(value = "附件 多个附件逗号分割")
    private String annex;
}
