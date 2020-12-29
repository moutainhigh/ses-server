package com.redescooter.ses.web.ros.vo.wms.cn.china;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description:
 * @author: Aleks
 * @create: 2020/12/29 11:54
 */
@Data
@ApiModel("仓库库存的情况统计出参")
public class WmsStockCountResult extends GeneralResult {

    @ApiModelProperty("可用库存数量")
    private Integer ableStockQty = 0;

    @ApiModelProperty("已用库存数量")
    private Integer usedStockQty = 0;

    @ApiModelProperty("待入库库存数量")
    private Integer waitInStockQty = 0;

    @ApiModelProperty("待出库库存数量")
    private Integer waitOutStockQty = 0;

}
