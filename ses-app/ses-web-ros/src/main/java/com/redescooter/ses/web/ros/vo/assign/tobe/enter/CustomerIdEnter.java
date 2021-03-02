package com.redescooter.ses.web.ros.vo.assign.tobe.enter;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.exception.ValidationExceptionBaseCode;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Description 客户id入参
 * @Author Chris
 * @Date 2020/12/30 14:13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "客户id入参", description = "客户id入参")
public class CustomerIdEnter extends GeneralEnter implements Serializable {

    private static final long serialVersionUID = 6339767677738461180L;

    @ApiModelProperty(value = "客户id", required = true)
    @NotNull(code = ValidationExceptionBaseCode.ID_IS_EMPTY, message = "主键不能为空")
    private Long customerId;

}
