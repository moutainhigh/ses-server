package com.redescooter.ses.api.foundation.vo.message;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.PageEnter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import io.swagger.annotations.*;

/**
 * @ClassName:MessageListEnter
 * @description: MessageListEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/01/10 19:18
 */
@ApiModel(value = "消息列表入参", description = "消息列表入参")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@Builder
@EqualsAndHashCode(callSuper = false)
public class MessageListEnter extends PageEnter {
    @ApiModelProperty(value = "消息类型")
    private String messageType;

    @ApiModelProperty(value = "推送开始时间")
    private String sendStartTime;

    @ApiModelProperty(value = "推送结束时间")
    private String sendEndTime;
}
