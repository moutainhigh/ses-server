package com.redescooter.ses.web.ros.vo.production.purchasing;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.redescooter.ses.api.common.constant.DateConstant;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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
    @ApiModelProperty(value = "id 产品Id ")
    private Long id;

    @ApiModelProperty(value = "部品Id ")
    private Long partId;

    @ApiModelProperty(value = "产品编号")
    private String partsN;

    @ApiModelProperty(value = "名字")
    private String enName;

    @ApiModelProperty(value = "名字")
    private String cnName;

    @ApiModelProperty(value = "类型")
    private String productType;

    @ApiModelProperty(value = "供应商Id")
    private Long supplierId;

    @ApiModelProperty(value = "供应商")
    private String supplierName;

    @ApiModelProperty(value = "生产周期")
    private Integer leadTime;

    @ApiModelProperty(value = "应交货时间")
    @DateTimeFormat(pattern = DateConstant.DEFAULT_DATETIME_FORMAT)
    @JsonFormat(pattern = DateConstant.DEFAULT_DATETIME_FORMAT, timezone = DateConstant.UTC)
    private Date dueTime;

    @ApiModelProperty(value = "价格")
    private BigDecimal price;

    @ApiModelProperty(value = "单位")
    private String unit;

    @ApiModelProperty(value = "数量")
    private Integer qty;

    @ApiModelProperty(value = "小计")
    private BigDecimal subtotal;

    @ApiModelProperty(value = "部品列表")
    private List<PruchasingItemResult> pruchasingItemResultList;

    @ApiModelProperty("可用数量")
    private Integer ableQty = 0;
}
