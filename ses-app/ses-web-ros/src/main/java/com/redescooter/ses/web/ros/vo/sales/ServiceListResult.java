package com.redescooter.ses.web.ros.vo.sales;

import java.util.Date;

import com.redescooter.ses.api.common.vo.base.GeneralResult;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @ClassName:ServiceListResult
 * @description: ServiceListResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/02/26 09:58
 */
@ApiModel(value = "增值服务列表出参", description = "增值服务列表出参")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class ServiceListResult extends GeneralResult {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "服务名称")
    private String serviceName;

    @ApiModelProperty(value = "服务描述")
    private String description;

    @ApiModelProperty(value = "法国报价")
    private String serviceFrPrice;

    @ApiModelProperty(value = "英国报价")
    private String serviceEnPrice;

    @ApiModelProperty(value = "刷新事件")
    private Date refuseTime;
}
