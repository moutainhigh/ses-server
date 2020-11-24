package com.redescooter.ses.web.ros.vo.sys.position;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.web.ros.exception.ValidationExceptionCode;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassNamePositionIdEnter
 * @Description
 * @Author Aleks
 * @Date2020/9/8 14:55
 * @Version V1.0
 **/
@Data
public class PositionIdEnter extends GeneralEnter {

    @ApiModelProperty("岗位id")
    private Long positionId;

    @ApiModelProperty("类型，1：查询全部的，2:查询未禁用的")
    @NotNull(code = ValidationExceptionCode.TYPE_IS_EMPTY, message = "TYPE为空")
    private Integer type;

}
