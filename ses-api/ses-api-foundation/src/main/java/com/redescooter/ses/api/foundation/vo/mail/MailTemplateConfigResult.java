package com.redescooter.ses.api.foundation.vo.mail;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author jerry
 * @Date 2021/2/9 12:14 下午
 * @Description 邮件模板配置列表参数详情
 **/
@ApiModel(value = "邮件模板参数结果集", description = "邮件模板参数结果集")
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@Data
public class MailTemplateConfigResult extends GeneralResult {

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "模板编号")
    private Integer mailTemplateNo;

    @ApiModelProperty(value = "name")
    private String name;

    @ApiModelProperty(value = "邮件发送事件")
    private String event;

    @ApiModelProperty(value = "主题")
    private String subject;

    List<MailTemplateSystemInfoResult> systemInfoList = new ArrayList<>();
}







