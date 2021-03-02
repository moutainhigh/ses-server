package com.redescooter.ses.web.ros.vo.wms.cn.china;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description:
 * @author: Aleks
 * @create: 2020/12/29 18:18
 */
@Data
@ApiModel("原料库部件列表出参")
public class MaterialStockPartsListResult extends GeneralResult {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("部件id")
    private Long partsId;

    @ApiModelProperty("部件编号")
    private String partsNo;

    @ApiModelProperty("部件类型，1：Parts，2：Accessory，3：Battery，4：Scooter，5：Combination")
    private Integer partsType;

    @ApiModelProperty("英文名字")
    private String enName;

    @ApiModelProperty("中文名字")
    private String cnName;

    @ApiModelProperty("可用库存数量")
    private Integer ableStockQty;

    @ApiModelProperty("是否有序列号 0否，1是")
    private Integer idClass;

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
