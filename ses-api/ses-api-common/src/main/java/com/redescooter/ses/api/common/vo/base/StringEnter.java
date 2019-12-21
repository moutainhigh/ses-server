package com.redescooter.ses.api.common.vo.base;


import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @description: KeywordEnter
 * @author: Darren
 * @create: 2019/08/16 13:33
 */
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@Builder
@EqualsAndHashCode(callSuper = false)
public class StringEnter extends GeneralEnter {

    @ApiModelProperty(value = "值")
    private String value;
}
