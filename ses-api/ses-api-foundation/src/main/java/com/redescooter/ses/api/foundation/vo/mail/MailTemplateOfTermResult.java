package com.redescooter.ses.api.foundation.vo.mail;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 11/10/2019 3:19 下午
 * @ClassName: MailTemplateOfTermResult
 * @Function: TODO
 */
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@Builder
@EqualsAndHashCode(callSuper = false)
public class MailTemplateOfTermResult extends GeneralResult {
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private Long id;

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

    /**
     * 主题
     */
    @ApiModelProperty(value = "主题")
    private String subject;

    /**
     * 邮件有效期，单位秒
     */
    @ApiModelProperty(value = "邮件有效期，单位秒")
    private Integer expire;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private Long createdBy;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="UTC")
    private Date createdTime;

}
