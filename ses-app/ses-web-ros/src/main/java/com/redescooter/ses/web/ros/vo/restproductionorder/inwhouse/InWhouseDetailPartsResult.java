package com.redescooter.ses.web.ros.vo.restproductionorder.inwhouse;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassNameInWhouseDetailPartsResult
 * @Description
 * @Author Aleks
 * @Date2020/11/11 14:23
 * @Version V1.0
 **/
@Data
@ApiModel(value = "入库单详情部件明细",description = "入库单详情部件明细")
public class InWhouseDetailPartsResult {

    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty(value="部件id")
    private Long partsId;

    @ApiModelProperty(value="部件名称(英文名称)")
    private String partsName;

    @ApiModelProperty(value="部件编号")
    private String partsNo;

    @ApiModelProperty(value="部件类型，1：Parts，2：Accessory，3：Battery，4：Scooter，5：Combination")
    private Integer partsType;

    @ApiModelProperty(value="采购数量")
    private Integer purchaseQty;

    @ApiModelProperty(value="可入库数量")
    private Integer ableInWhQty;

    @ApiModelProperty(value="入库数量")
    private Integer inWhQty;

    @ApiModelProperty(value="实际入库数量")
    private Integer actInWhQty;

    @ApiModelProperty("不合格数量")
    private Integer unqualifiedQty = 0;

}
