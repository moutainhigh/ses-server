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
public class QueryAdminScooterParamDTO extends PageDTO {

    @ApiModelProperty(value = "分组id", dataType = "Long")
    private Long groupId;

    @ApiModelProperty(value = "颜色id", dataType = "Long")
    private Long colorId;

    @ApiModelProperty(value = "关键字(序列号、蓝牙名称、蓝牙地址)", dataType = "String")
    private String keyWord;

}
