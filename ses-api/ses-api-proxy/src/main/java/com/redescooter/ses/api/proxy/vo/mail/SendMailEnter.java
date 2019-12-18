package com.redescooter.ses.api.proxy.vo.mail;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description: SendMailEnter
 * @author: Darren
 * @create: 2019/04/03 11:10
 */
@ApiModel(value = "发送邮箱入参", description = "发送邮件")
@Data
public class SendMailEnter extends GeneralEnter {
    @ApiModelProperty(value = "邮箱账户")
    private String mail;
    @ApiModelProperty(value = "发送验证码事件")
    private String sendCodeEvent;
}
