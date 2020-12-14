package com.redescooter.ses.web.ros.vo.sys.role;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.exception.ValidationExceptionBaseCode;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.web.ros.exception.ValidationExceptionCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassNameRoleSaveEnter
 * @Description
 * @Author Aleks
 * @Date2020/9/1 10:31
 * @Version V1.0
 **/
@Data
@ApiModel(value = "新增角色")
public class RoleSaveOrEditEnter extends GeneralEnter {

    @ApiModelProperty("角色id")
    private Long id;

    @ApiModelProperty(value = "岗位ID")
    @NotNull(code = ValidationExceptionBaseCode.ID_IS_EMPTY, message = "Id 为空")
    private Long positionId;

    @ApiModelProperty(value = "角色名称")
    @NotNull(code = ValidationExceptionCode.ROLE_NAME_IS_EMPTY, message = "职位名称为空")
    private String roleName;

    @ApiModelProperty(value = "角色编码")
    private String roleCode;

    @ApiModelProperty(value = "角色描述")
    private String roleDesc;

    @ApiModelProperty("角色状态  1：正常，2：禁用")
    private Integer roleStatus;

    @ApiModelProperty("是否开启销售区域功能 0：否，1：是")
    private Integer saleArea;

    @ApiModelProperty("排序")
    private Integer sort;


}
