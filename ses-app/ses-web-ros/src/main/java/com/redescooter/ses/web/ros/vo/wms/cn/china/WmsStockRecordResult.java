package com.redescooter.ses.web.ros.vo.wms.cn.china;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @description:
 * @author: Aleks
 * @create: 2020/12/28 16:19
 */
@Data
@ApiModel("入库记录出参")
public class WmsStockRecordResult implements Serializable {

    @ApiModelProperty("入库类型，1：生产入库，2：返修入库，3：采购入库，5：退料入库，6：其他")
    private Integer inWhType;

    @ApiModelProperty("入库数量")
    private Integer inWhQty;

    @ApiModelProperty("入库时间")
    private Date createdTime;

    @ApiModelProperty("操作人")
    private String createdName;

    @ApiModelProperty("SN 供应商序列号")
    private String sn;

    @ApiModelProperty("批次号")
    private String lotNum;

    @ApiModelProperty("关联的库存表id")
    private Long relationId;

}
