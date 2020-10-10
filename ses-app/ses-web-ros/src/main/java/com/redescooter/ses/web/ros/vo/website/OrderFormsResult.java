package com.redescooter.ses.web.ros.vo.website;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.redescooter.ses.api.common.constant.DateConstant;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import io.swagger.annotations.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @ClassName:OrderFormListResult
 * @description: OrderFormListResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/05/14 14:53
 */
@ApiModel(value = "Order Result", description = "Order Result")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class OrderFormsResult extends GeneralResult {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "product Model")
    private String productModel;

    @ApiModelProperty(value = "Order number")
    private String orderNo;

    @ApiModelProperty(value = "status")
    private String status;

    @ApiModelProperty(value = "color")
    private String color;

    @ApiModelProperty(value = "Order time")
    @DateTimeFormat(pattern = DateConstant.DEFAULT_DATETIME_FORMAT)
    @JsonFormat(pattern = DateConstant.DEFAULT_DATETIME_FORMAT, timezone = DateConstant.UTC)
    private Date orderTime;

}
