package com.redescooter.ses.web.ros.vo.production.allocate;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.web.ros.exception.ValidationExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import io.swagger.annotations.*;

/**
 * @ClassName:SaveAllocateEnter
 * @description: SaveAllocateEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/19 15:38
 */
@ApiModel(value = "保存调拨单", description = "保存调拨单")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class SaveAllocateEnter extends GeneralEnter {

    @ApiModelProperty(value = "收货人")
    @NotNull(code = ValidationExceptionCode.CONSIGNEE_ID__IS_EMPTY, message = "收货人id为空")
    private Long consigneeId;

    @ApiModelProperty(value = "部件列表 格式：id：10000，qty：10000")
    private String partList;
}
