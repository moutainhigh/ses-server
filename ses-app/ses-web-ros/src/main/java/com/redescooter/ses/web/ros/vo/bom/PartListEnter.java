package com.redescooter.ses.web.ros.vo.bom;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @ClassName:SaveScooterPartResult
 * @description: SaveScooterPartResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/02/25 13:12
 */
@ApiModel(value = "配件详情入参", description = "配件详情入参")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class PartListEnter extends GeneralEnter {
    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "产品编号")
    private String platsN;

    @ApiModelProperty(value = "部件区域")
    private String sec;

    @ApiModelProperty(value = "英文名称")
    private String productEnName;

    @ApiModelProperty(value = "中文名称")
    private String productCnName;

    @ApiModelProperty(value = "生产周期")
    private String productionCycle;

    @ApiModelProperty(value = "数量")
    private int qty;
}
