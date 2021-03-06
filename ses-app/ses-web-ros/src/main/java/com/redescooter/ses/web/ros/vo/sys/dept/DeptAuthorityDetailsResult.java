package com.redescooter.ses.web.ros.vo.sys.dept;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.web.ros.vo.tree.MenuTreeResult;
import com.redescooter.ses.web.ros.vo.tree.SalesAreaTressResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @ClassName DeptAuthorityDetailsResult
 * @Author Jerry
 * @date 2020/03/13 10:38
 * @Description:
 */
@ApiModel(value = "部门权限详情")
@Data
public class DeptAuthorityDetailsResult extends GeneralResult {

    @ApiModelProperty(value = "销售区域权限")
    private List<SalesAreaTressResult> salesAreaResult;

    @ApiModelProperty(value = "菜单权限")
    private List<MenuTreeResult> menuResult;
}
