package com.redescooter.ses.web.ros.vo.sys.staff;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassNameStaffDataResult
 * @Description
 * @Author Aleks
 * @Date2020/9/2 14:14
 * @Version V1.0
 **/
@Data
public class StaffDataResult {

    @ApiModelProperty("负责人id")
    private String principal;

    @ApiModelProperty("负责人名称")
    private String principalName;

}
