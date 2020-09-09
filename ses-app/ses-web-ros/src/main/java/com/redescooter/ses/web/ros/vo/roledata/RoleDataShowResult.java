package com.redescooter.ses.web.ros.vo.roledata;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @ClassNameRoleDataShowResult
 * @Description
 * @Author Aleks
 * @Date2020/9/9 13:46
 * @Version V1.0
 **/
@Data
public class RoleDataShowResult extends GeneralResult {

    @ApiModelProperty("类型，1:全部,2:本人,3:本部门,4:本部门及下属部门")
    private Integer dataType;

    @ApiModelProperty("自定义部门树")
    private List<RoleDataTree> roleDataTree;


}
