package com.redescooter.ses.web.ros.vo.restproduct;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author Aleks
 * @Description
 * @Date  2020/10/20 11:36
 * @return
 **/
@Data
public class SaleCombinSaveOrUpdateEnter extends GeneralEnter {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty(value="产品名称")
    private String productName;

    @ApiModelProperty(value="产品编码")
    private String productCode;

    @ApiModelProperty(value="组装件名称")
    private String combinName;

    @ApiModelProperty(value = "组装件id")
    private Long productionCombinBomId;

    @ApiModelProperty("组装件图片")
    private String picture;

    @ApiModelProperty("销售价格")
    private BigDecimal price;

}
