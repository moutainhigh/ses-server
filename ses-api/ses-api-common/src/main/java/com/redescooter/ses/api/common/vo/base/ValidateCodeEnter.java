package com.redescooter.ses.api.common.vo.base;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @description: ValidateCodeEnter
 * @author: Darren
 * @create: 2019/04/03 11:12
 */
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@Builder
@EqualsAndHashCode(callSuper = false)
public class ValidateCodeEnter<T> extends GeneralEnter {
    @ApiModelProperty(value = "验证码")
    private String code;

    @ApiModelProperty(value = "事件")
    private String event;

    private T t;
}
