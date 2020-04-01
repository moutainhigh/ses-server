package com.redescooter.ses.web.ros.vo.production.assembly;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @ClassName:productItemResult
 * @description: productItemResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/30 13:57
 */
@ApiModel(value = "组装单中商品列表", description = "组装单中商品列表")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class productItemResult extends GeneralResult {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "商品编号")
    private String productN;

    @ApiModelProperty(value = "商品名称")
    private String enName;

    @ApiModelProperty(value = "商品名称")
    private String cnName;

    @ApiModelProperty(value = "数量")
    private Integer qty;

    @ApiModelProperty(value = "产品单价")
    private BigDecimal price;

    @ApiModelProperty(value = "总计")
    private BigDecimal subtotal;

    @ApiModelProperty(value = "")
    private Integer stock;

}
