package com.redescooter.ses.mobile.rps.vo.outwhorder;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 出库单异常消息响应对象 DTO
 * @author assert
 * @date 2021/1/11 18:11
 */
@Data
@ApiModel(value = "出库单异常消息响应对象")
public class OutWhOrderErrorMessageResponse extends GeneralResult {

    @ApiModelProperty(value = "异常消息类型", dataType = "Integer")
    private Integer errorType;

    @ApiModelProperty(value = "异常消息说明", dataType = "String")
    private String errorDesc;

}
