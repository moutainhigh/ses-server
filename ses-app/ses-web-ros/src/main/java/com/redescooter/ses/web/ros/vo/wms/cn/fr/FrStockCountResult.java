package com.redescooter.ses.web.ros.vo.wms.cn.fr;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @description:
 * @author: Aleks
 * @create: 2020/12/31 15:32
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("法国库存统计出参")
public class FrStockCountResult extends GeneralResult {

    @ApiModelProperty("车辆数量")
    private Integer scooterNum = 0;

    @ApiModelProperty("组装件数量")
    private Integer combinNum = 0;

    @ApiModelProperty("部件数量")
    private Integer partsNum = 0;

}
