package com.redescooter.ses.web.ros.vo.monday.enter;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.web.ros.enums.datatype.WebhookEventType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import io.swagger.annotations.*;

/**
 * @ClassName:MultipleWebhookEnter
 * @description: MultipleWebhookEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/08/03 10:58
 */
@ApiModel(value = "", description = "")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class MultipleWebhookEnter extends GeneralEnter {

    @ApiModelProperty(value = "板子Id",required  = true)
    private Long boardId;

    @ApiModelProperty(value = "回调地址",required  = true)
    private String url;

    @ApiModelProperty(value = "监听事件",required  = true)
    private WebhookEventType eventType;

    @ApiModelProperty(value = "钩子的配置 json格式参数暂时未知",required  = true)
    private String config;
}
