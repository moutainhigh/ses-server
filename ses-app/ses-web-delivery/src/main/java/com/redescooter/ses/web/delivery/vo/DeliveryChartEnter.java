package com.redescooter.ses.web.delivery.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 8/1/2020 10:37 上午
 * @ClassName: DeliveryChartEnter
 * @Function: TODO
 */
@ApiModel(value = "订单统计柱状图入参", description = "订单统计柱状图入参")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@EqualsAndHashCode(callSuper = true)
public class DeliveryChartEnter extends GeneralEnter {

    @ApiModelProperty(value = "时间点")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Date dateTimes;
    @ApiModelProperty(value = "天数")
    private int heavens;
    @ApiModelProperty(value = "状态")
    private String status;
    @ApiModelProperty(value = "司机的userId")
    private long id;
}
