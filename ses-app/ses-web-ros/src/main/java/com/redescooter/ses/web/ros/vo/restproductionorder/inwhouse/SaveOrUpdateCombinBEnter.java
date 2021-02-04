package com.redescooter.ses.web.ros.vo.restproductionorder.inwhouse;

import io.swagger.annotations.ApiModel;
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
@ApiModel("关联的组装单的组装件产品信息")
public class SaveOrUpdateCombinBEnter {

    @ApiModelProperty(value="组装件名称(中文名称)")
    private String combinName;

    @ApiModelProperty(value="组装件编号")
    private String combinNo;

    @ApiModelProperty(value="组装件id")
    private Long productionCombinBomId;

    @ApiModelProperty(value="组装数量")
    private Integer combinQty;

    @ApiModelProperty(value="可入库数量")
    private Integer ableInWhQty;

    @ApiModelProperty(value="入库数量")
    private Integer inWhQty = 1;

}
