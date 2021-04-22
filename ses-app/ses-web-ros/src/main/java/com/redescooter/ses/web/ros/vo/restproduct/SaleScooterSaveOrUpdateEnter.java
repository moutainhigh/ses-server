package com.redescooter.ses.web.ros.vo.restproduct;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassNameSaleScooterSaveOrUpdateEnter
 * @Description
 * @Author Aleks
 * @Date2020/10/13 10:00
 * @Version V1.0
 **/
@Data
public class SaleScooterSaveOrUpdateEnter extends GeneralEnter {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty(value="产品名称")
    private String productName;

    @ApiModelProperty(value="产品编码")
    private String productCode;

    @ApiModelProperty(value="车辆所属规格id")
    private Long specificatId;

    @ApiModelProperty(value = "车辆所属颜色id")
    private Long colorId;

    @ApiModelProperty(value = "图片")
    private String picture;

    @ApiModelProperty("最低电池数")
    private Integer minBatteryNum;

    @ApiModelProperty(value = "产品参数 存储JSON")
    private String productionParam;

    @ApiModelProperty(value = "其他参数 存储JSON")
    private String otherParam;

}
