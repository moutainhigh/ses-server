package com.redescooter.ses.api.foundation.vo.message;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PinEnter extends GeneralEnter {

    @ApiModelProperty(value = "PIN")
    private String firstPin;

    @ApiModelProperty(value = "确认PIN")
    private String confirmPin;

}
