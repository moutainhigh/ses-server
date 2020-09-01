package com.redescooter.ses.web.ros.vo.sys.staff;

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
public class StaffOpEnter extends GeneralEnter {

    @ApiModelProperty("员工id")
    private Long id;

}
