package com.redescooter.ses.web.website.vo.order;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

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
    private Long orderId;

    /**
     * 配件列表
     */
    @ApiModelProperty(value = "配件列表")
    List<AddPartListEnter> partslist = new ArrayList<>();

}
