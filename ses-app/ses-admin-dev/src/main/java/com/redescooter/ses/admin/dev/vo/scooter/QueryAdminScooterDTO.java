package com.redescooter.ses.admin.dev.vo.scooter;

import com.redescooter.ses.api.common.vo.base.page.PageDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 查询车辆列表入参 DTO
 * @author assert
 * @date 2020/12/8 19:59
 */
@Data
@ApiModel(value = "查询车辆列表入参")
public class QueryAdminScooterDTO extends PageDTO {

    @ApiModelProperty(value = "-现在列表查询暂时只有分页参数", dataType = "String")
    private String temp;

}
