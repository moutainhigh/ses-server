package com.redescooter.ses.web.ros.vo.tree;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassNameMenuDatasListResult
 * @Description
 * @Author Aleks
 * @Date2020/9/7 15:45
 * @Version V1.0
 **/
@Data
public class MenuDatasListResult extends GeneralResult {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("id")
    private String name;

}
