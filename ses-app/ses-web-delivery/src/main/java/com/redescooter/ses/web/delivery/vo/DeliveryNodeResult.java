package com.redescooter.ses.web.delivery.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 5/1/2020 9:37 下午
 * @ClassName: DeliveryNodeResult
 * @Function: TODO
 */
@ApiModel(value = "订单节点", description = "订单节点")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@Data
public class DeliveryNodeResult extends GeneralResult {


    @ApiModelProperty(value = "ID")
    private Long id;
    @ApiModelProperty(value = "事件")
    private String event;
    @ApiModelProperty(value = "状态")
    private String status;
    @ApiModelProperty(value = "司机userID")
    private Long driverUserId;
    @ApiModelProperty(value = "司机名字")
    private String driverFirstName;
    @ApiModelProperty(value = "司机名字")
    private String driverLastName;
    @ApiModelProperty(value = "事件时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Date eventTime;
}
