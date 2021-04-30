package com.redescooter.ses.web.website.vo.order;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.web.website.exception.SiteValidationExceptionCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author jerry
 * @Date 2021/1/6 3:41 上午
 * @Description 新增订单配件数量
 **/
@ApiModel(value = "Add order parts qty", description = "新增订单配件数量")
@Data
@EqualsAndHashCode(callSuper = true)
public class AddOrderPartsEnter extends GeneralEnter {

    /**
     * 订单主建
     */
    @ApiModelProperty(value = "orderId")
    @NotNull(code = SiteValidationExceptionCode.ID_IS_EMPTY,message = "ID为空")
    private Long orderId;

    /**
     * 配件列表
     */
    @ApiModelProperty(value = "parts list")
    private String partslist;

    /**
     * 支付方式id+期数
     */
    @ApiModelProperty(value = "支付方式id+期数")
    private String paymentTypeId;

    /**
     * 车型id
     */
    @ApiModelProperty(value = "车型id")
    private Long modelId;

    /**
     * 电池数
     */
    @ApiModelProperty(value = "电池数")
    private Integer battery;

}
