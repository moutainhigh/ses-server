package com.redescooter.ses.mobile.rps.vo.materialqc;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import io.swagger.annotations.*;

/**
 * @ClassName:RpsHeadResult
 * @description: RpsHeadResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/04/13 16:06
 */
@ApiModel(value = "Rps首页数据出参", description = "Rps首页数据出参")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class RpsHeadResult extends GeneralResult {

    @ApiModelProperty(value = "来料质检数量")
    private int materialQcTotal;

    @ApiModelProperty(value = "备料数量")
    private int prepareMaterialTotal;

    @ApiModelProperty(value = "采购入库数量")
    private int purchasInWhTotal;

    @ApiModelProperty(value = "组装数量")
    private int assemblyTotal;

    @ApiModelProperty(value = "车辆质检数量")
    private int scooterQcTotal;

    @ApiModelProperty(value = "生产入库数量")
    private int productionInWhTotal;

    @ApiModelProperty(value = "出库单数量")
    private int outboundTotal;

    @ApiModelProperty(value = "委托单数量")
    private int consignTotal;
}
