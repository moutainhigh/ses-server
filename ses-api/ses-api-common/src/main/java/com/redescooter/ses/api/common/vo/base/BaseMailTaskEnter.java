package com.redescooter.ses.api.common.vo.base;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 10/10/2019 6:57 下午
 * @ClassName: SetPasswordMobileUserTaskEnter
 * @Function: TODO
 */
@Data //生成getter,setter等函数
public class BaseMailTaskEnter extends GeneralEnter {
    @ApiModelProperty(value = "验证码")
    private String code;
    @ApiModelProperty(value = "事件")
    private String event;
    @ApiModelProperty(value = "收件人姓名")
    private String name;
    @ApiModelProperty(value = "收件邮箱")
    private String toMail;
    @ApiModelProperty(value = "收件人ID")
    private Long toUserId;
    @ApiModelProperty(value="系统ID")
    private String MailSystemId;
    @ApiModelProperty(value="应用ID")
    private String mailAppId;
    @ApiModelProperty(value = "用户唯一请求ID")
    private String UserRequestId;
    @ApiModelProperty(value = "邮箱")
    private String email;
    @ApiModelProperty(value = "全名")
    private String fullName;
    @ApiModelProperty(value = "产品型号")
    private String model;
    @ApiModelProperty(value = "剩余支付金额")
    private String price;
    @ApiModelProperty("委托单号")
    private String entrustNo;
}
