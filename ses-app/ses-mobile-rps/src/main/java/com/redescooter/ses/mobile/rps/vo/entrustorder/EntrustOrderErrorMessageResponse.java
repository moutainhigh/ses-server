package com.redescooter.ses.mobile.rps.vo.entrustorder;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 委托单异常消息响应对象 DTO
 * @author assert
 * @date 2021/1/11 18:21
 */
@Data
@ApiModel(value = "委托单异常消息响应对象")
public class EntrustOrderErrorMessageResponse extends GeneralResult {

    /**
     * 异常码,参考枚举：{@link com.redescooter.ses.mobile.rps.enums.EntrustOrderErrorMessageEnum}
     */
    @ApiModelProperty(value = "异常码", dataType = "String")
    private String errorCode;

    /**
     * 异常消息说明
     */
    @ApiModelProperty(value = "异常消息说明", dataType = "String")
    private String errorDesc;

}
