package com.redescooter.ses.web.ros.vo.sys.menu;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.web.ros.vo.tree.MenuTreeResult;
import io.swagger.annotations.ApiModel;
import lombok.*;

import java.util.List;

/**
 * @ClassName ModulePermissionsResult
 * @Author Jerry
 * @date 2020/03/13 14:49
 * @Description:
 */
@ApiModel(value = "模块权限")
@Data
public class ModulePermissionsResult extends GeneralResult {
    private Long id;
    private Long pId;
    private String name;
    private String code;
    private Integer sort;

    private List<MenuTreeResult> permissions;
}
