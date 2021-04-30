package com.redescooter.ses.api.foundation.vo.login;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @description: LoginResult
 * @author: Darren
 * @create: 2019/01/16 09:57
 */
@ApiModel(value = "登录结果", description = "登录结果")
@Data // 生成getter,setter等函数
@AllArgsConstructor // 生成全参数构造函数
@NoArgsConstructor // 生成无参构造函数
@Builder
@EqualsAndHashCode(callSuper = false)
public class LoginResult extends GeneralResult {
    
    private String token;

    private String refreshToken;
    
    private boolean noPassword;
    
    private Integer accountType;
    
    List<AccountsDto> accountSelectionList;
}
