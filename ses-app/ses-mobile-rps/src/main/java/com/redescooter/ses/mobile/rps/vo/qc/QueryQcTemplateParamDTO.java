package com.redescooter.ses.mobile.rps.vo.qc;

import com.redescooter.ses.api.common.annotation.NotNull;
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

    @ApiModelProperty(value = "部件号(部件类型时传递该值)", dataType = "String")
    private String partsNo;

    @ApiModelProperty(value = "序列号(车辆和组装件类型时传递)", dataType = "String")
    private String serialNum;

    @NotNull(code = ValidationExceptionCode.PRODUCT_TYPE_IS_EMPTY, message = "产品类型不能为空")
    @ApiModelProperty(value = "产品类型 1车辆 2组装件 3部件", dataType = "Integer")
    private Integer productType;

}
