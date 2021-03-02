package com.redescooter.ses.web.ros.vo.wms.cn.china;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description:
 * @author: Aleks
 * @create: 2020/12/28 16:00
 */
@Data
@ApiModel("成品库车辆库存出参")
public class WmsFinishScooterListResult extends GeneralResult {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("车型名称")
    private String groupName;

    @ApiModelProperty("颜色名称")
    private String colorName;

    @ApiModelProperty("色值")
    private String colorValue;

    @ApiModelProperty("可用库存数量")
    private Integer ableStockQty;

    @ApiModelProperty("已用库存数量")
    private Integer usedStockQty;

    @ApiModelProperty("待入库库存数量")
    private Integer waitInStockQty;

    @ApiModelProperty("待出库库存数量")
    private Integer waitOutStockQty;


}
