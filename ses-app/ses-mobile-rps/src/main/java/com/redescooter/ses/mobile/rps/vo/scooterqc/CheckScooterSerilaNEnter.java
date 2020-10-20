package com.redescooter.ses.mobile.rps.vo.scooterqc;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.exception.ValidationExceptionBaseCode;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.mobile.rps.exception.ValidationExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import io.swagger.annotations.*;

/**
 * @ClassName:CheckScooterSerilaNEnter
 * @description: CheckScooterSerilaNEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/05/09 19:28
 */
@ApiModel(value = "是否已经质检过", description = "是否已经质检过")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class CheckScooterSerilaNEnter extends GeneralEnter {

    @ApiModelProperty(value = "产品Id")
    @NotNull(code = ValidationExceptionBaseCode.ID_IS_EMPTY,message = "Id为空")
    private Long productId;

    @ApiModelProperty(value = "序列号")
    @NotNull(code = ValidationExceptionCode.SERIAL_NUM,message = "产品序列号为空")
    private String serialN;
}
