package com.redescooter.ses.api.foundation.vo.message;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.foundation.exception.ValidationExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

import io.swagger.annotations.*;

/**
 * @ClassName:ReadMessageEnter
 * @description: ReadMessageEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/01/10 19:35
 */
@ApiModel(value = "读取消息入参", description = "读取消息入参")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@Builder
@EqualsAndHashCode(callSuper = false)
public class ReadMessageEnter extends GeneralEnter {

    @ApiModelProperty(value = "消息id")
    private List<Long> ids;
}
