package com.redescooter.ses.mobile.rps.vo.restproductionorder.optrace;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type List by buss id enter.
 * @author: alex
 * @Date: 2020 /10/28 17:17
 * @version：V ROS 1.8.3
 * @Description:
 */
@ApiModel(value = "查询操作动态", description = "查询操作动态")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ListByBussIdEnter extends GeneralEnter {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "订单状态")
    private Integer orderType;
}
