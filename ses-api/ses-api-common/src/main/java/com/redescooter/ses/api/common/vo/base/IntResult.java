package com.redescooter.ses.api.common.vo.base;


import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @description: BooleanResult
 * @author: Darren
 * @create: 2019/08/19 11:24
 */
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@Builder
@EqualsAndHashCode(callSuper = false)
public class IntResult extends GeneralResult {

    @ApiModelProperty(value = "值")
    private int value;
}
