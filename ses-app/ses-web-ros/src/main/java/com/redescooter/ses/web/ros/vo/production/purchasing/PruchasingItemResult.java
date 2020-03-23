package com.redescooter.ses.web.ros.vo.production.purchasing;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import io.swagger.annotations.*;

/**
 * @ClassName:PruchasingProductResult
 * @description: PruchasingProductResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/19 10:20
 */
@ApiModel(value = "采购产品条目", description = "采购产品条目")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class PruchasingItemResult extends GeneralResult {
    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "产品编号")
    private String partsN;

    @ApiModelProperty(value = "名字")
    private String enName;

    @ApiModelProperty(value = "名字")
    private String cnName;

    @ApiModelProperty(value = "类型")
    private String type;

    @ApiModelProperty(value = "供应商Id")
    private Long supplierId;

    @ApiModelProperty(value = "供应商")
    private String supplierName;

    @ApiModelProperty(value = "交货时间")
    private Integer leadTime;

    @ApiModelProperty(value = "价格")
    private String price;

    @ApiModelProperty(value = "单位")
    private String unit;

    @ApiModelProperty(value = "数量")
    private Integer qty;

    @ApiModelProperty(value = "小计")
    private String subtotal;
}
