package com.redescooter.ses.mobile.rps.vo.printentry;

import com.redescooter.ses.api.common.vo.base.GeneralResult;

import lombok.*;

import io.swagger.annotations.*;

/**
 *  @author: alex
 *  @Date: 2020/10/23 15:30
 *  @version：V ROS 1.8.3
 *  @Description:
 */
@ApiModel(value = "订单质检记录", description = "订单质检记录")
@Data
public class PrintEntryOrderDetailResult extends GeneralResult {

    @ApiModelProperty(value = "质检记录Id")
    private Long id;

    @ApiModelProperty(value = "采购单Id")
    private Long purchasId;

    @ApiModelProperty(value = "采购单字订单Id")
    private Long purchasbId;

    @ApiModelProperty(value = "部品Id")
    private Long partId;

    @ApiModelProperty(value = "部品名称")
    private String partName;

    @ApiModelProperty(value = "部品号")
    private String partN;

    @ApiModelProperty(value = "批次号")
    private String lotNum;

    @ApiModelProperty(value = "序列号")
    private String serialNum;
}
