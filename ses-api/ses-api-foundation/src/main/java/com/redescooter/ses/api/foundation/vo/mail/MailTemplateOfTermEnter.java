package com.redescooter.ses.api.foundation.vo.mail;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 11/10/2019 3:22 下午
 * @ClassName: MailTemplateOfTermEnter
 * @Function: TODO
 */
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@Builder
@EqualsAndHashCode(callSuper = false)
public class MailTemplateOfTermEnter extends GeneralEnter {

    /**
     * 模板名称
     */
    @ApiModelProperty(value = "name")
    private String name;

    /**
     * Normal正常的，Disabled失效的
     */
    @ApiModelProperty(value = "Normal正常的，Disabled失效的")
    private String status;

    /**
     * 邮件发送事件
     */
    @ApiModelProperty(value = "邮件发送事件")
    private String event;

    /**
     * 模板编号
     */
    @ApiModelProperty(value = "模板编号")
    private Integer mailTemplateNo;
}
