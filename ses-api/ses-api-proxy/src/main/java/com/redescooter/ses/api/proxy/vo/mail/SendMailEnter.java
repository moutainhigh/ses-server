package com.redescooter.ses.api.proxy.vo.mail;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @description: SendMailEnter
 * @author: Darren
 * @create: 2019/04/03 11:10
 */
@ApiModel(value = "邮件发送入参", description = "邮件发送入参")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@Builder
@EqualsAndHashCode(callSuper = false)
public class SendMailEnter extends GeneralEnter {

    @ApiModelProperty(value = "邮箱账户")
    private String mail;

}
