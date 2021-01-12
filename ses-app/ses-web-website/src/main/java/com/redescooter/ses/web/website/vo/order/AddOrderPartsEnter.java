package com.redescooter.ses.web.website.vo.order;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @Author jerry
 * @Date 2021/1/6 3:41 上午
 * @Description 新增订单入参
 **/
@ApiModel(value = "Add order into parameter", description = "新增订单入参")
@Data
@EqualsAndHashCode(callSuper = true)
public class AddOrderPartsEnter extends GeneralEnter {

    /**
     * 客户主建
     */
    @ApiModelProperty(value = "customer_id")
    private Long customerId;

    /**
     * 订单主建
     */
    @ApiModelProperty(value = "orderId")
    private Long orderId;

    /**
     * 配件ID
     */
    @ApiModelProperty(value = "partsId")
    private Long partsId;

    /**
     * 配件数量
     */
    @ApiModelProperty(value = "Number of accessories")
    private String qty;

    /**
     * 备注
     */
    @ApiModelProperty(value = "remark")
    private String remark;


}
