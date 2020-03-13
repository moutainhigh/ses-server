package com.redescooter.ses.web.ros.vo.sys.dept;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.web.ros.exception.ValidationExceptionCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName SaveDeptEnter
 * @Author Jerry
 * @date 2020/03/11 20:09
 * @Description:
 */
@ApiModel(value = "部门创建")
@Data
public class EditDeptEnter extends SaveDeptEnter {

    @ApiModelProperty(value = "部门主键")
    @NotNull(code = ValidationExceptionCode.ID_IS_EMPTY, message = "id 为空")
    private Long id;
}
