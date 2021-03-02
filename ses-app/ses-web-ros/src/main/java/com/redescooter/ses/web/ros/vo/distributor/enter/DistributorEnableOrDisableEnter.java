package com.redescooter.ses.web.ros.vo.distributor.enter;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.exception.ValidationExceptionBaseCode;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.web.ros.exception.ValidationExceptionCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Description 经销商启用/禁用入参
 * @Author Chris
 * @Date 2020/12/16 16:48
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "经销商启用/禁用入参", description = "经销商启用/禁用入参")
public class DistributorEnableOrDisableEnter extends GeneralEnter implements Serializable {

    private static final long serialVersionUID = 3196095928213331003L;

    @ApiModelProperty(value = "主鍵", required = true)
    @NotNull(code = ValidationExceptionBaseCode.ID_IS_EMPTY, message = "Id为空")
    private Long id;

    @ApiModelProperty(value = "状态 若启用传1 若停用传2", required = true)
    @NotNull(code = ValidationExceptionCode.STORE_STATUS_IS_NOT_EMPTY, message = "状态为空")
    private Integer status;

}
