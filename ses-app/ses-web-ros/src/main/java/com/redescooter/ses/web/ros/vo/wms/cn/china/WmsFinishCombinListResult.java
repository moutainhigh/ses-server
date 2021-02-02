package com.redescooter.ses.web.ros.vo.wms.cn.china;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description:
 * @author: Aleks
 * @create: 2020/12/29 15:15
 */
@Data
@ApiModel("成品库组装件库存出参")
public class WmsFinishCombinListResult extends GeneralResult {

    @ApiModelProperty("id")
    private Long id;

    /**
     * 组装件编号
     */
    @ApiModelProperty("组装件编号")
    private String combinNo;

    /**
     * 中文名称
     */
    @ApiModelProperty("中文名称")
    private String cnName;

    /**
     * 英文名称
     */
    @ApiModelProperty("英文名称")
    private String enName;

    /**
     * 法文名称
     */
    @ApiModelProperty("法文名称")
    private String frName;

    /**
     * 可用库存数量
     */
    @ApiModelProperty("可用库存数量")
    private Integer ableStockQty;

    /**
     * 已用库存数量
     */
    @ApiModelProperty("已用库存数量")
    private Integer usedStockQty;

    /**
     * 待入库数量
     */
    @ApiModelProperty("待入库数量")
    private Integer waitInStockQty;

    /**
     * 待出库数量
     */
    @ApiModelProperty("待出库数量")
    private Integer waitOutStockQty;



}
