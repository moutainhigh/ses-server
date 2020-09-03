package com.redescooter.ses.web.ros.vo.sys.staff;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassNameStaffOpEnter
 * @Description
 * @Author Aleks
 * @Date2020/9/1 19:57
 * @Version V1.0
 **/
@Data
public class StaffDeleteEnter extends GeneralEnter {

    @ApiModelProperty("员工id,多选用，拼接")
    @NotNull(code = com.redescooter.ses.api.common.exception.ValidationExceptionCode.ID_IS_EMPTY, message = "Id 为空")
    private String ids;

}
