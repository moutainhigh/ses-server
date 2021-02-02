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
@ApiModel("关联的组装单的整车产品信息")
public class SaveOrUpdateScooterBEnter {


    @ApiModelProperty(value="车型（规格分组）的id")
    private Long groupId;

    @ApiModelProperty(value="颜色id")
    private Long colorId;

    @ApiModelProperty(value="车型（规格分组）的名字")
    private String groupName;

    @ApiModelProperty(value="颜色的名字")
    private String colorName;

    @ApiModelProperty(value="色值")
    private String colorValue;

    @ApiModelProperty(value="bom车辆id")
    private Long scooterBomId;

    @ApiModelProperty(value="组装数量")
    private Integer combinQty;

    @ApiModelProperty(value="可入库数量")
    private Integer ableInWhQty;

    @ApiModelProperty(value="入库数量")
    private Integer inWhQty = 1;

    @ApiModelProperty(value="备注")
    private String remark;


}
