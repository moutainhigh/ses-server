package com.redescooter.ses.mobile.rps.vo.entrustorder;

import com.redescooter.ses.api.common.annotation.NotEmpty;
import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.mobile.rps.exception.ValidationExceptionCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 委托单发货入参对象 DTO
 * @author assert
 * @date 2021/1/4 11:34
 */
@Data
@ApiModel(value = "委托单发货入参对象")
public class EntrustOrderDeliverParamDTO extends GeneralEnter {

    @NotNull(code = ValidationExceptionCode.ENTRUST_ORDER_ID_IS_EMPTY, message = "委托单id不能为空")
    @ApiModelProperty(value = "委托单id", dataType = "Long")
    private Long id;

    // 正常应该是使用@NotBlank对String类型做校验的,不过这个项目里面并没有这个注解所以String类型可以使用@NotEmpty
    @NotEmpty(code = ValidationExceptionCode.LOGISTICS_COMPANY_IS_EMPTY, message = "物流公司不能为空")
    @ApiModelProperty(value = "物流公司", dataType = "String")
    private String logisticsCompany;

    @NotEmpty(code = ValidationExceptionCode.LOGISTICS_NO_IS_EMPTY, message = "物流单号不能为空")
    @ApiModelProperty(value = "物流单号", dataType = "String")
    private String logisticsNo;

    @ApiModelProperty(value = "备注说明,当前为保留字段(页面上并没有这个输入框)", dataType = "String")
    private String remark;

}
