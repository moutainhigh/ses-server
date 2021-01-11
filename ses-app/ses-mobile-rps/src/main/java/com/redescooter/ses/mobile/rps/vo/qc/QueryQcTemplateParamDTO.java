package com.redescooter.ses.mobile.rps.vo.qc;

import com.redescooter.ses.api.common.annotation.NotEmpty;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.mobile.rps.exception.ValidationExceptionCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 查询质检模板入参对象 DTO
 * @author assert
 * @date 2021/1/6 11:39
 */
@Data
@ApiModel(value = "查询质检模板入参对象")
public class QueryQcTemplateParamDTO extends GeneralEnter {

    @NotEmpty(code = ValidationExceptionCode.SERIAL_NUMBER_IS_EMPTY, message = "产品序列号不能为空")
    @ApiModelProperty(value = "产品序列号", dataType = "String")
    private String serialNum;

    @ApiModelProperty(value = "产品id", dataType = "Long", hidden = true)
    private Long productId;

    @ApiModelProperty(value = "产品类型", dataType = "Integer", hidden = true)
    private Integer productType;

}
