package com.redescooter.ses.mobile.rps.vo.printentry;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.redescooter.ses.api.common.constant.DateConstant;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

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

    @ApiModelProperty(value = "质检数量")
    private Integer qty;

    @ApiModelProperty(value = "到货时间")
    @DateTimeFormat(pattern = DateConstant.DEFAULT_DATETIME_FORMAT)
    @JsonFormat(pattern = DateConstant.DEFAULT_DATETIME_FORMAT, timezone = DateConstant.UTC)
    private Date arrivalTime;
}
