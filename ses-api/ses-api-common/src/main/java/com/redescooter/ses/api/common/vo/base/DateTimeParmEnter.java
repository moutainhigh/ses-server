package com.redescooter.ses.api.common.vo.base;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.Date;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 12/10/2019 5:04 下午
 * @ClassName: FreezeWarnWebTaskEnter
 * @Function: TODO
 */
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@Builder
@EqualsAndHashCode(callSuper = false)
public class DateTimeParmEnter<T> extends GeneralEnter {

    @ApiModelProperty(value = "日期")
    private Date dateTime;

    @ApiModelProperty(value = "开始日期")
    private Date startDateTime;

    @ApiModelProperty(value = "结束日期")
    private Date endDateTime;

    private T t;
}
