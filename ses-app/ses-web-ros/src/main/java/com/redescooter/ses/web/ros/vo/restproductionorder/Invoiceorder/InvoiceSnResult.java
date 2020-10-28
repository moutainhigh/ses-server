package com.redescooter.ses.web.ros.vo.restproductionorder.Invoiceorder;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import lombok.*;

import io.swagger.annotations.*;

/**
 * The type Invoice sn result.
 * @author: alex
 * @Date: 2020 /10/27 16:49
 * @version：V ROS 1.8.3
 * @Description:
 */
@ApiModel(value = "查询序列号", description = "查询序列号")
@Data
@EqualsAndHashCode(callSuper = true)
public class InvoiceSnResult extends GeneralResult {
    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "颜色Id")
    private Long colorId;

    @ApiModelProperty(value = "分组Id")
    private Long groupId;

    @ApiModelProperty(value = "部件Id")
    private Long partId;

    @ApiModelProperty(value = "序列号")
    private String sn;

    @ApiModelProperty(value = "数量")
    private Integer qty;

    @ApiModelProperty(value = "产品类型")
    private Integer productType;
}
