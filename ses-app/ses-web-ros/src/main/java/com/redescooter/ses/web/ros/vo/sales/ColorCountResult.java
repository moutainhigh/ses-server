package com.redescooter.ses.web.ros.vo.sales;

import com.redescooter.ses.api.foundation.vo.setting.GroupResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: ses-server
 * @description: 颜色列表
 * @author: Jerry
 * @created: 2020/09/30 13:43
 */
@ApiModel(value = "颜色列表", description = "颜色列表")
@Data
public class ColorCountResult extends GroupResult {

    @ApiModelProperty(value = "颜色key")
    private int id;

    @ApiModelProperty(value = "颜色value")
    private String name;
}
