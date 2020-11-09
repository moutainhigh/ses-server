package com.redescooter.ses.web.ros.vo.restproduct;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.web.ros.exception.ValidationExceptionCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassNameCombinNameEnter
 * @Description
 * @Author Aleks
 * @Date2020/11/9 13:46
 * @Version V1.0
 **/
@Data
@ApiModel(value = "选组装件名字下拉",description = "选组装件名字下拉")
public class CombinNameEnter extends GeneralEnter {

    @ApiModelProperty("类型，1：生效的，2:生效和待生效的")
    @NotNull(code = ValidationExceptionCode.TYPE_IS_EMPTY, message = "TYPE为空")
    private Integer type;

}
