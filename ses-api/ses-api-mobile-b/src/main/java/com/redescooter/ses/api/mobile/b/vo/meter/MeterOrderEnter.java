package com.redescooter.ses.api.mobile.b.vo.meter;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import lombok.*;

import io.swagger.annotations.*;

/**
 * @ClassName:MeterOrderEnter
 * @description: MeterOrderEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/11/16 17:52 
 */
@ApiModel(value = "仪表订单入参", description = "仪表订单入参")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class MeterOrderEnter extends GeneralEnter {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "序列号")
    private String sn;
}
