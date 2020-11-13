package com.redescooter.ses.web.ros.vo.restproductionorder.inwhouse;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassNameSaveOrUpdateScooterBEnter
 * @Description
 * @Author Aleks
 * @Date2020/11/11 13:02
 * @Version V1.0
 **/
@Data
public class SaveOrUpdatePartsBEnter {

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
    private Integer inWhQty = 1;

}
