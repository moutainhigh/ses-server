package com.redescooter.ses.web.ros.vo.sys.role;

import com.redescooter.ses.api.common.vo.base.PageEnter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassNameRoleQueryListEnter
 * @Description
 * @Author Aleks
 * @Date2020/9/1 16:38
 * @Version V1.0
 **/
@Data
public class RoleQueryListEnter extends PageEnter {

    @ApiModelProperty("角色状态  1：正常，2：禁用")
    private Integer roleStatus;

    @ApiModelProperty(value = "岗位ID")
    private Long positionId;

    @ApiModelProperty("关键字")
    private String keyWord;

}
