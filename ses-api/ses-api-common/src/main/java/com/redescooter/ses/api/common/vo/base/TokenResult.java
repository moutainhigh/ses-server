package com.redescooter.ses.api.common.vo.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

/**
 * description: UserLoginCrmEnter
 * author: jerry.li
 * create: 2019-05-28 14:37
 */


@ApiModel(value = "登录成功返回值", description = "登录成功返回值")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@Builder
@EqualsAndHashCode(callSuper = false)
public class TokenResult extends GeneralResult {

    @ApiModelProperty(value = "token值")
    private String token;

}
