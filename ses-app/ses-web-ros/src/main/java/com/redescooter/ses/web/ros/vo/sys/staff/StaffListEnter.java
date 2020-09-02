package com.redescooter.ses.web.ros.vo.sys.staff;

import com.redescooter.ses.api.common.vo.base.PageEnter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassNameStaffListEnter
 * @Description
 * @Author Aleks
 * @Date2020/9/2 11:17
 * @Version V1.0
 **/
@Data
public class StaffListEnter extends PageEnter {

    @ApiModelProperty(value = "员工状态 1：正常，2：禁用")
    private Integer status;

    @ApiModelProperty(value = "所属部门id")
    private Long deptId;

    @ApiModelProperty(value = "所属岗位id")
    private Long positionId;

    @ApiModelProperty(value = "所属角色id")
    private Long roleId;

    @ApiModelProperty("关键字")
    private String keyWord;
}
