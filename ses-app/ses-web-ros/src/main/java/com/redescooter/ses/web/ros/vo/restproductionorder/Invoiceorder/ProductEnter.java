package com.redescooter.ses.web.ros.vo.restproductionorder.Invoiceorder;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.web.ros.exception.ValidationExceptionCode;
import lombok.*;

import io.swagger.annotations.*;

/**
 *  @author: alex
 *  @Date: 2020/10/28 14:18
 *  @version：V ROS 1.8.3
 *  @Description:
 */
@ApiModel(value = "产品列表入参", description = "产品列表入参")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ProductEnter extends GeneralEnter {

    @ApiModelProperty(value = "产品Id")
    private Long productId;

    @ApiModelProperty(value = "颜色类型 整车是传递")
    private Long colorId;

    @ApiModelProperty(value = "分组类型 整车是传递")
    private Long groupId;

    @ApiModelProperty(value = "数量")
    @NotNull(code = ValidationExceptionCode.QTY_IS_EMPTY, message = "数量为空")
    private Integer qty;

    @ApiModelProperty(value = "备注")
    private String remark;
}
