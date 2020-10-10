package com.redescooter.ses.web.ros.vo.website;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.web.ros.exception.ValidationExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import io.swagger.annotations.*;

/**
 * @ClassName:ScooterListEnter
 * @description: ScooterListEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/05/13 15:47
 */
@ApiModel(value = "Vehicle list input", description = "Vehicle list input")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class ScootersEnter extends GeneralEnter {

    @ApiModelProperty(value = "Vehicle model")
    @NotNull(code = ValidationExceptionCode.MODEL_IS_EMPTY, message = "型号为空")
    private String modelCode;
}
