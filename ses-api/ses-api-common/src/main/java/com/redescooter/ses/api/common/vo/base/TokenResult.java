package com.redescooter.ses.api.common.vo.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * description: UserLoginCrmEnter
 * author: jerry.li
 * create: 2019-05-28 14:37
 */


@ApiModel(value = "Token Result", description = "Token Result")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@Builder
@EqualsAndHashCode(callSuper = false)
public class TokenResult extends GeneralResult {

    @ApiModelProperty(value = "token")
    private String token;

    @ApiModelProperty(value = "刷新token")
    private String refreshToken;

}
