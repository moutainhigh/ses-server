package com.redescooter.ses.api.foundation.vo.app;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.exception.ValidationExceptionBaseCode;
import com.redescooter.ses.api.common.vo.base.page.PageDTO;
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
public class QueryAppVersionParamDTO extends PageDTO {

    @ApiModelProperty(value = "版本状态 0未发布 1已发布 2使用中", dataType = "Integer")
    private Integer status;

    @ApiModelProperty(value = "版本号", dataType = "String")
    private String versionNumber;

    @ApiModelProperty(value = "关键字(更新内容 or 标签)", dataType = "String")
    private String keyWord;

    @ApiModelProperty(value = "应用类型 1-APP(Ios,安卓) 3-SCS(车载平板) 4-SaaS 5-Server(后台服务) 6-ROS",
            dataType = "Integer", required = true)
    @NotNull(code = ValidationExceptionBaseCode.APP_TYPE_IS_EMPTY, message = "应用类型不能为空")
    private Integer type;

}
