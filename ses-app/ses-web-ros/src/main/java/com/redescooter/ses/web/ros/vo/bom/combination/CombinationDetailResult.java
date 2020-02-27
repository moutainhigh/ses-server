package com.redescooter.ses.web.ros.vo.bom.combination;

import java.util.List;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.web.ros.vo.bom.QueryPartListResult;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @ClassName:CombinationLDetailResult
 * @description: CombinationLDetailResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/02/27 11:47
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class CombinationDetailResult extends GeneralResult {
    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "产品编号")
    private String productN;

    @ApiModelProperty(value = "产品法文名")
    private String productFrName;

    @ApiModelProperty(value = "产品英文名")
    private String productEnName;

    @ApiModelProperty(value = "产品中文名")
    private String productCnName;

    @ApiModelProperty(value = "产品数量")
    private int qty;

    @ApiModelProperty(value = "产品列表")
    private List<QueryPartListResult> partList;
}
