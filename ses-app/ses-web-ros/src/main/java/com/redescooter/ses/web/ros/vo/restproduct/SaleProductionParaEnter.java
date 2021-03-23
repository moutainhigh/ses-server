package com.redescooter.ses.web.ros.vo.restproduct;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description:
 * @author: Aleks
 * @create: 2021/03/23 13:57
 */
@Data
public class SaleProductionParaEnter {

    @ApiModelProperty("名字对应的编码,(1:speed，2：power，3：mileage，4：charge_cycle)")
    private String defName;

    @ApiModelProperty("值")
    private String defValue;

}
