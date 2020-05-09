package com.redescooter.ses.api.foundation.vo.user;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.foundation.exception.ValidationExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import io.swagger.annotations.*;

/**
 * @ClassName:SaveAccountNodeEnter
 * @description: SaveAccountNodeEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/17 15:41
 */
@ApiModel(value = "保存账户节点入参", description = "保存账户节点入参")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@Builder
@EqualsAndHashCode(callSuper = false)
public class SaveAccountNodeEnter extends GeneralEnter {

    @ApiModelProperty(value = "事件")
    @NotNull(code = ValidationExceptionCode.EVENT_IS_EMPTY, message = "事件为空")
    private String event;

    @ApiModelProperty(value = "目标用户Id")
    @NotNull(code = ValidationExceptionCode.ID_IS_EMPTY, message = "Id为空")
    private Long inputUserId;

    @ApiModelProperty(value = "备注")
    private String memo;

}
