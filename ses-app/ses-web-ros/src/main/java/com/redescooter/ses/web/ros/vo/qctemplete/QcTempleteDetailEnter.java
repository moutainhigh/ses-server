package com.redescooter.ses.web.ros.vo.qctemplete;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.exception.ValidationExceptionBaseCode;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.web.ros.exception.ValidationExceptionCode;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName:QcTempleteDetailEnter
 * @description: QcTempleteDetailEnter
 * @author: Alex @Version：1.3
 * @create: 2020/10/13 15:56
 */
@Data // 生成getter,setter等函数
@AllArgsConstructor
@NoArgsConstructor
public class QcTempleteDetailEnter extends GeneralEnter {

    @ApiModelProperty(value = "id")
    @NotNull(code = ValidationExceptionBaseCode.ID_IS_EMPTY, message = "Id为空")
    private Long id;

    @ApiModelProperty(value = "产品类型Id")
    @NotNull(code = ValidationExceptionCode.TYPE_IS_EMPTY, message = "TYPE为空")
    private Integer productionProductType;
}
