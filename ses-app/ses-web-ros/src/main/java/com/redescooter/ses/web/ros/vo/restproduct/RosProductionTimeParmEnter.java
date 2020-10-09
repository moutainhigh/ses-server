package com.redescooter.ses.web.ros.vo.restproduct;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data // 生成getter,setter等函数
@AllArgsConstructor // 生成全参数构造函数
@NoArgsConstructor // 生成无参构造函数
@Builder
@EqualsAndHashCode(callSuper = false)
public class RosProductionTimeParmEnter extends GeneralEnter {
    @ApiModelProperty(value = "日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Date dateTime;

    @ApiModelProperty(value = "产品类型")
    private Integer productionType;

    @ApiModelProperty(value = "productN")
    private String productN;
}
