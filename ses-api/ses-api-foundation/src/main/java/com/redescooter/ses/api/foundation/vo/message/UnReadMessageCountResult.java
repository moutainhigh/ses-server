package com.redescooter.ses.api.foundation.vo.message;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import io.swagger.annotations.*;

/**
 * @ClassName:UnReadMessageResult
 * @description: UnReadMessageResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/01/14 14:03
 */
@ApiModel(value = "未读消息统计", description = "未读消息统计")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@Builder
@EqualsAndHashCode(callSuper = false)
public class UnReadMessageCountResult extends GeneralResult {

    @ApiModelProperty(value = "未读统计")
    private int unReadTotal = 0;

    @ApiModelProperty(value = "强提醒数据")
    private MessageResult message;
}
