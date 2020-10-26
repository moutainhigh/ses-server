package com.redescooter.ses.web.ros.vo.restproductionorder.consignorder;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 *  @author: alex
 *  @Date: 2020/10/22 13:56
 *  @version：V ROS 1.8.3
 *  @Description:
 */
@ApiModel(value = "委托单列表", description = "委托单列表")
@Data
@EqualsAndHashCode(callSuper = true)
public class ConsignOrderListResult extends GeneralResult {
    @ApiModelProperty(value = "委托单Id")
    private Long id;

    @ApiModelProperty(value = "委托单编号")
    private String consignOrderNo;

    @ApiModelProperty(value = "委托单状态")
    private Integer status;

    @ApiModelProperty(value = "出库单Id")
    private Long outwhId;

    @ApiModelProperty(value = "出库单编号")
    private String outwhNo;

    @ApiModelProperty(value = "数量")
    private Integer qty;

    @ApiModelProperty(value = "交货时间")
    private Date deliveryDate;

    @ApiModelProperty(value = "收货人")
    private Long consigneeId;

    @ApiModelProperty(value = "收货人国家代码")
    private Long consigneeCountryCodeId;

    @ApiModelProperty(value = "收货人国家代码")
    private String consigneeCountryCode;

    @ApiModelProperty(value = "收货人国家电话")
    private String consigneeTelephone;

    @ApiModelProperty(value = "收货人邮箱")
    private String consigneeMail;

    @ApiModelProperty(value = "创建人")
    private Long createById;

    @ApiModelProperty(value = "创建人名称")
    private String createByFirstName;

    @ApiModelProperty(value = "创建人名称")
    private String createByLastName;

    @ApiModelProperty(value = "创建时间")
    private Date createByDate;
}
