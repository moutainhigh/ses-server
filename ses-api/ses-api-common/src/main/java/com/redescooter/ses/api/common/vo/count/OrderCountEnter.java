package com.redescooter.ses.api.common.vo.count;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @ClassName:OrderCountDto
 * @description: OrderCountDto
 * @author: Aleks
 * @Version：1.3
 * @create: 2020/12/16 16:36
 */
@Data
public class OrderCountEnter extends GeneralEnter {

    @ApiModelProperty("类型，1：年，2：月，3：日")
    private Integer type;

    @ApiModelProperty("时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Date dateTime;



}
