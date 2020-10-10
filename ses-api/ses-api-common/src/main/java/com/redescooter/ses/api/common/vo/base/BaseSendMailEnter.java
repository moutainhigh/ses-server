package com.redescooter.ses.api.common.vo.base;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.exception.ValidationExceptionCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @description: BaseSendMailEnter
 * @author: Darren
 * @create: 2019/04/03 11:10
 */
@ApiModel(value = "Sending mail into parameters", description = "Sending mail into parameters")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@Builder
@EqualsAndHashCode(callSuper = false)
public class BaseSendMailEnter extends GeneralEnter {

    @ApiModelProperty(value = "email accounts")
    @NotNull(code = ValidationExceptionCode.EMAIL_IS_EMPTY,message = "邮箱为空")
    private String mail;

}
