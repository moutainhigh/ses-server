package com.redescooter.ses.web.ros.vo.restproduct;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author Aleks
 * @Description
 * @Date  2020/10/20 11:36
 * @return
 **/
@Data
public class SalePartsSaveOrUpdateEnter extends GeneralEnter {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty(value="产品名称")
    private String productName;

    @ApiModelProperty(value="产品编码")
    private String productCode;

    @ApiModelProperty(value="部件名称")
    private String partsName;

    @ApiModelProperty(value = "部件id")
    private Long partsId;

    @ApiModelProperty("部件图片")
    private String picture;

}
