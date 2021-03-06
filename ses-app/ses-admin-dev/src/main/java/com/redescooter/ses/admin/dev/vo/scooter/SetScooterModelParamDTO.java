package com.redescooter.ses.admin.dev.vo.scooter;

import com.redescooter.ses.admin.dev.exception.ValidationExceptionCode;
import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 设置车辆软体入参 DTO
 * @author assert
 * @date 2020/12/14 13:58
 */
@Data
@ApiModel(value = "设置车辆软体入参")
public class SetScooterModelParamDTO extends GeneralEnter {

    @NotNull(code = ValidationExceptionCode.ID_IS_NOT_EMPTY, message = "id不能为空")
    @ApiModelProperty(value = "主键Id", dataType = "Long", required = true)
    private Long id;

    @NotNull(code = ValidationExceptionCode.MODEL_ID_IS_NOT_EMPTY, message = "车辆型号id不能为空")
    @ApiModelProperty(value="车辆型号Id", dataType = "Long", required = true)
    private Long modelId;

    @ApiModelProperty(value = "设置类型 1设置软体 2重置软体", dataType = "Integer", hidden = true)
    private Integer type;

}
