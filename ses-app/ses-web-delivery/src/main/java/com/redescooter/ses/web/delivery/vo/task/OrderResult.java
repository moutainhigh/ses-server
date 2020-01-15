package com.redescooter.ses.web.delivery.vo.task;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import io.swagger.annotations.*;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @ClassName:OrderResult
 * @description: OrderResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/01/15 15:48
 */
@ApiModel(value = "订单详情", description = "订单详情")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@Builder
@EqualsAndHashCode(callSuper = false)
public class OrderResult extends GeneralResult {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "状态")
    private String status;

    @ApiModelProperty(value = "收货方国家")
    private String recipientCountry;

    @ApiModelProperty(value = "收货方省份")
    private String recipientProvince;

    @ApiModelProperty(value = "收货方邮编")
    private String recipientPostcode;

    @ApiModelProperty(value = "收货方地址")
    private String recipientAddress;

    @ApiModelProperty(value = "EDT")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private String expectTimeBegin;

    @ApiModelProperty(value = "EDT")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private String expectTimeEnd;


}
