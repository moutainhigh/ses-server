package com.redescooter.ses.web.ros.vo.restproduct;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.web.ros.exception.ValidationExceptionCode;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassNameBomNoEnter
 * @Description
 * @Author Aleks
 * @Date2020/10/20 13:16
 * @Version V1.0
 **/
@Data
public class BomNoEnter {

    @ApiModelProperty("组装件名称")
    private String combinName;

    @ApiModelProperty("类型，1：生效的，2:生效和待生效的,3:全部")
    @NotNull(code = ValidationExceptionCode.TYPE_IS_EMPTY, message = "TYPE为空")
    private Integer type;
}
