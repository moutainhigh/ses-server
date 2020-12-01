package com.redescooter.ses.web.ros.vo.restproduct;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassNameSaleScooterListResult
 * @Description
 * @Author Aleks
 * @Date2020/10/13 15:31
 * @Version V1.0
 **/
@Data
public class SaleScooterListResult extends GeneralResult {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty(value="产品名称")
    private String productName;

    @ApiModelProperty(value="产品编码")
    private String productCode;

    @ApiModelProperty(value="车辆所属规格id")
    private Long specificatId;

    @ApiModelProperty(value="车辆所属规格名称")
    private String specificatName;

    @ApiModelProperty(value="车辆所属分组id")
    private Long groupId;

    @ApiModelProperty(value = "所属分组名称")
    private String groupName;

    @ApiModelProperty(value = "车辆所属颜色id")
    private Long colorId;

    @ApiModelProperty(value = "车辆所属颜色名称")
    private String colorName;

    @ApiModelProperty(value = "车辆所属颜色的色值")
    private String colorValue;

    @ApiModelProperty(value = "销售状态，0：不可销售，1：可销售")
    private Integer saleStutas;

}
