package com.redescooter.ses.web.ros.vo.restproduct;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author Aleks
 * @Description
 * @Date  2020/10/20 11:43
 * @Param
 * @return
 **/
@Data
public class SalePartsListResult extends GeneralResult {

    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty(value="产品名称")
    private String productName;

    /**
     * 产品编码
     */
    @ApiModelProperty(value="产品编码")
    private String productCode;

    /**
     * 组装件名称(英文名称)
     */
    @ApiModelProperty(value="组装件名称(英文名称)")
    private String partsName;

    /**
     * 组装件编号
     */
    @ApiModelProperty(value="组装件编号")
    private String partsNo;


    @ApiModelProperty(value="组装件id")
    private Long partsId;

    /**
     * 销售状态，0：不可销售，1：可销售
     */
    @ApiModelProperty(value="销售状态，0：不可销售，1：可销售")
    private Integer saleStutas;
}
