package com.redescooter.ses.web.ros.vo.bom.combination;

import com.redescooter.ses.api.common.vo.base.GeneralResult;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @ClassName:CombinationListResult
 * @description: CombinationListResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/02/25 13:56
 */
@ApiModel(value = "组合列表出参", description = "组合列表出参")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class CombinationListResult extends GeneralResult {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "产品编号")
    private String productN;

    @ApiModelProperty(value = "产品英文名")
    private String productEnName;

    @ApiModelProperty(value = "产品中文名")
    private String productCnName;

    @ApiModelProperty(value = "产品法文名")
    private String productFrName;

    @ApiModelProperty(value = "产品数量")
    private int qty;

    @ApiModelProperty(value = "是否存在部品列表")
    private Boolean partList=Boolean.FALSE;
}
