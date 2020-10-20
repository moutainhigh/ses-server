package com.redescooter.ses.web.ros.vo.deliveryopion;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.exception.ValidationExceptionBaseCode;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.web.ros.exception.ValidationExceptionCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@ApiModel(value = "Delivery Option Edit Enter", description = "Delivery Option Edit Enter")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
public class DeliveryOptionEditEnter extends GeneralEnter {

    @ApiModelProperty(value = "ID")
    @NotNull(code = ValidationExceptionCode.ID_IS_EMPTY, message = "主键不能为空")
    private Long id;

    @ApiModelProperty(value = "name")
    private String optionNeme;

    @ApiModelProperty(value = "price")
    private Double price = 0.0;

    @ApiModelProperty(value = "memo")
    private String memo;

    @ApiModelProperty(value = "Custom fields")
    private String def1;
}
