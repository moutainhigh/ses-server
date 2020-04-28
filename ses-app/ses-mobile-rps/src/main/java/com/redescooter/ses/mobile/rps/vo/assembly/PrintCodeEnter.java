package com.redescooter.ses.mobile.rps.vo.assembly;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.exception.ValidationExceptionCode;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import io.swagger.annotations.*;

/**
 * @ClassName:PrintCodeEnter
 * @description: PrintCodeEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/04/14 10:03
 */
@ApiModel(value = "打印条码入参", description = "打印条码入参")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class PrintCodeEnter extends GeneralEnter {

    @ApiModelProperty(value = "商品组装单Id")
    @NotNull(code = ValidationExceptionCode.ID_IS_EMPTY, message = "Id 不能为空")
    private Long id;

    @ApiModelProperty(value = "打印结果")
    @NotNull(code = com.redescooter.ses.mobile.rps.exception.ValidationExceptionCode.PRINT_CODE_RESULT, message = "打印结果为空")
    private Boolean printCodeResult;
}
