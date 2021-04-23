package com.redescooter.ses.web.ros.vo.sales;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.redescooter.ses.api.common.constant.DateConstant;
import com.redescooter.ses.api.common.vo.base.PageEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @program: ses-server
 * @description: 销售订单列表入参
 * @author: Jerry
 * @created: 2020/09/29 22:32
 */
@ApiModel(value = "销售订单列表入参", description = "销售订单列表入参")
@Data
public class SalesOrderEnter extends PageEnter {

    @ApiModelProperty(value = "状态")
    private String status;

    @ApiModelProperty(value = "颜色id")
    private Long color;

    @ApiModelProperty(value = "创建开始时间")
    @DateTimeFormat(pattern = DateConstant.DEFAULT_DATETIME_FORMAT)
    @JsonFormat(pattern = DateConstant.DEFAULT_DATETIME_FORMAT, timezone = DateConstant.UTC)
    private Date startCreatedTime;

    @ApiModelProperty(value = "创建结束时间")
    @DateTimeFormat(pattern = DateConstant.DEFAULT_DATETIME_FORMAT)
    @JsonFormat(pattern = DateConstant.DEFAULT_DATETIME_FORMAT, timezone = DateConstant.UTC)
    private Date endCreatedTime;

    @ApiModelProperty(value = "关键字")
    private String keyword;

}
