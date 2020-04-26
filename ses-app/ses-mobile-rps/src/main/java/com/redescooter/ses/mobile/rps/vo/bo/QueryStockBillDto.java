package com.redescooter.ses.mobile.rps.vo.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import io.swagger.annotations.*;

/**
 * @ClassName:QueryStockBillDto
 * @description: QueryStockBillDto
 * @author: Alex
 * @Version：1.3
 * @create: 2020/04/26 19:01
 */
@ApiModel(value = "", description = "")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class QueryStockBillDto {

    @ApiModelProperty(value = "部件Id")
    private Long partId;

    @ApiModelProperty(value = "入库单Id")
    private Long stockBillId;

    @ApiModelProperty(value = "单据来源类型")
    private String sourceType;

    @ApiModelProperty(value = "库存Id")
    private Long stockId;
}
