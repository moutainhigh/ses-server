package com.redescooter.ses.web.ros.vo.wms.cn.china;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description:
 * @author: Aleks
 * @create: 2021/01/04 21:05
 */
@Data
public class WmsInStockRecordEnter {

    @ApiModelProperty(value = "关联的库存表ID")
    private Long relationId;

    /**
     * 关联的单据类型，1:成品库车辆库存单，2:成品库组装件库存单，3:原料库部件库存单，4:不合格品库车辆库存单，5:不合格品库组装件库存单，6:不合格品库部件库存单
     */
    @ApiModelProperty(value = "关联的单据类型，1:成品库车辆库存单，2:成品库组装件库存单，3:原料库部件库存单，4:不合格品库车辆库存单，5:不合格品库组装件库存单，6:不合格品库部件库存单")
    private Integer relationType;

    /**
     * 入库类型，1：生产入库，2：返修入库，3：采购入库，5：退料入库，6：其他
     */
    @ApiModelProperty(value = "入库类型，1：生产入库，2：返修入库，3：采购入库，5：退料入库，6：其他")
    private Integer inWhType;

    /**
     * 入库数量
     */
    @ApiModelProperty(value = "入库数量")
    private Integer inWhQty;

    /**
     * 记录类型，1:入库，2:出库
     */
    @ApiModelProperty(value = "记录类型，1:入库，2:出库")
    private Integer recordType;

    /**
     * 仓库类型，1:中国仓库，2:法国仓库
     */
    @ApiModelProperty(value = "仓库类型，1:中国仓库，2:法国仓库")
    private Integer stockType;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;


}
