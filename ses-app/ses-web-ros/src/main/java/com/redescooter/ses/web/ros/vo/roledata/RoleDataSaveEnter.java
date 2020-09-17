package com.redescooter.ses.web.ros.vo.roledata;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassNameRoleDataSaveEnter
 * @Description
 * @Author Aleks
 * @Date2020/9/9 11:30
 * @Version V1.0
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class RoleDataSaveEnter extends GeneralEnter {

    @ApiModelProperty("角色id")
    private Long roleId;

    @ApiModelProperty("菜单id,多个用,隔开")
    private String deptId;

    @ApiModelProperty("类型，1:全部,2:本人,3:本部门,4:本部门及下属部门")
    private Integer dataType;

}
