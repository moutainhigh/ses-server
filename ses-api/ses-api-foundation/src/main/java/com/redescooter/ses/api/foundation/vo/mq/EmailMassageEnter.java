package com.redescooter.ses.api.foundation.vo.mq;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @ClassNameEmailMassageEnter
 * @Description
 * @Author Joan
 * @Date2020/5/8 10:17
 * @Version V1.0
 **/

@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@Builder
@EqualsAndHashCode(callSuper = false)
public class EmailMassageEnter extends GeneralEnter {
    @ApiModelProperty(value = "消息id")
    private  long id;
}
