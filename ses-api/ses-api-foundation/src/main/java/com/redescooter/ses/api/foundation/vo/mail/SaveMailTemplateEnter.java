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
 * @Date: 11/10/2019 3:17 下午
 * @ClassName: SaveMailTemplateEnter
 * @Function: TODO
 */
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@Builder
@EqualsAndHashCode(callSuper = false)
public class SaveMailTemplateEnter extends GeneralEnter {

    /**
     * 模板编号
     */
    @ApiModelProperty(value = "模板编号")
    private Integer mailTemplateNo;

    /**
     * 邮件模板名称
     */
    @ApiModelProperty(value = "name")
    private String name;

    /**
     * 邮件发送事件
     */
    @ApiModelProperty(value = "邮件发送事件")
    private String event;

    /**
     * 主题
     */
    @ApiModelProperty(value = "主题")
    private String subject;

    /**
     * 邮件模板内容
     */
    @ApiModelProperty(value = "内容")
    private String content;

    /**
     * 邮件模板备注
     */
    @ApiModelProperty(value = "备注")
    private String memo;

    /**
     * 邮件有效期，单位秒
     */
    @ApiModelProperty(value = "邮件有效期，单位秒")
    private Integer expire;

    /**
     * 邮件模板上传地址
     */
    @ApiModelProperty(value = "邮件模板上传地址")
    private String url;

}
