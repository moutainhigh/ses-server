package com.redescooter.ses.api.foundation.vo.app;

import com.redescooter.ses.api.common.annotation.NotNull;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 查询应用版本入参 DTO
 * @author assert
 * @date 2020/12/3 14:13
 */
@Data
@ApiModel(value = "查询应用版本入参")
public class QueryAppVersionParamDTO {

    @ApiModelProperty(value = "版本状态 0未发布 1已发布 2使用中", dataType = "Integer")
    private Integer status;

    @ApiModelProperty(value = "版本编码", dataType = "Integer")
    private Integer versionCode;

    @ApiModelProperty(value = "更新内容", dataType = "String")
    private String description;

    @ApiModelProperty(value = "时间排序类型 1正序 2倒序", dataType = "Integer")
    private Integer timeSortType;

    @ApiModelProperty(value = "应用类型 1-ROS 2-APP(Ios,安卓) 3-SCS(车载平板) 4-SaaS 5-Server(后台服务)", dataType = "Integer")
    @NotNull(code = 1, message = "应用类型不能为空")
    private Integer type;

}
