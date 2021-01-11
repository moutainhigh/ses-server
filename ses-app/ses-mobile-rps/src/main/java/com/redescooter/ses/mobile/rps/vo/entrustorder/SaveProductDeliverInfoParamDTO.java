package com.redescooter.ses.mobile.rps.vo.entrustorder;

import com.redescooter.ses.api.common.annotation.NotEmpty;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.mobile.rps.exception.ValidationExceptionCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 保存产品发货信息入参对象
 * @author assert
 * @date 2021/1/7 18:09
 */
@Data
@ApiModel(value = "保存产品发货信息入参对象")
public class SaveProductDeliverInfoParamDTO extends GeneralEnter {

    @NotEmpty(code = ValidationExceptionCode.SERIAL_NUMBER_IS_EMPTY, message = "产品序列号不能为空")
    @ApiModelProperty(value = "产品序列号", dataType = "String")
    private String serialNum;

}
