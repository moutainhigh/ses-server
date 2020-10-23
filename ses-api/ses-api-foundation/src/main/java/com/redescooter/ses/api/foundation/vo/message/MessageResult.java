package com.redescooter.ses.api.foundation.vo.message;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @ClassName:MessageResult
 * @description: MessageResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/01/10 19:12
 */
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@Builder
@EqualsAndHashCode(callSuper = false)
public class MessageResult extends GeneralResult {
    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "逻辑删除标识 0正常 1删除")

    private Integer dr;

    @ApiModelProperty(value = "系统ID")
    private String systemId;

    @ApiModelProperty(value = "应用ID")
    private String appId;

    @ApiModelProperty(value = "租户ID")
    private Integer tenantId;

    @ApiModelProperty(value = "用户ID")
    private Long userId;

    @ApiModelProperty(value = "消息类型：推送消息PUSH 1，站内消息SITE，2", allowableValues = "1,2")
    private String messageType;

    @ApiModelProperty(value = "消息类型,1 订单 2 维修 3 系统", allowableValues = "1,2,3")
    private String bizType;

    @ApiModelProperty(value = "消息状态 1 已读，2未读", allowableValues = "1,2")
    private String status;

    @ApiModelProperty(value = "当前业务的业务状态 跟随业务状态保持一致")
    private String businessStatus;

    @ApiModelProperty(value = "消息标题")
    private String title;

    @ApiModelProperty(value = "1 无需提示 2 小红点 3 强提醒", allowableValues = "1,2,3")
    private String messagePriority;

    @ApiModelProperty(value = "消息内容")
    private String content;

    @ApiModelProperty(value = "消息内容参数")
    private String memo;

    @ApiModelProperty(value = "发送时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Date sendTime;

    @ApiModelProperty(value = "已读时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Date readTime;

    @ApiModelProperty(value = "创建人")
    private Long createdBy;

    @ApiModelProperty(value = "创建时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Date createdTime;

    @ApiModelProperty(value = "更新人")
    private Long updatedBy;

    @ApiModelProperty(value = "更新时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Date updatedTime;

    @ApiModelProperty(value = "业务Id")
    private String bizId;

}
