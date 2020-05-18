package com.redescooter.ses.api.foundation.vo.account;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import io.swagger.annotations.*;

/**
 * @ClassName:CheckOpenAccountEnter
 * @description: CheckOpenAccountEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/05/15 20:13
 */
@ApiModel(value = "校验账户是否已经激活", description = "校验账户是否已经激活")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class CheckOpenAccountEnter extends GeneralEnter {

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "账户类型")
    private Integer accountType;

}
