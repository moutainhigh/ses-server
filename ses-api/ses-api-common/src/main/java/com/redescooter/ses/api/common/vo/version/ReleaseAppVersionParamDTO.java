package com.redescooter.ses.api.common.vo.version;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.exception.ValidationExceptionBaseCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 发布版本入参DTO
 * @author assert
 * @date 2020/12/3 16:47
 */
@Data
@ApiModel(value = "发布版本入参")
public class ReleaseAppVersionParamDTO implements Serializable {

    @ApiModelProperty(value = "主键id", dataType = "Long")
    @NotNull(code = ValidationExceptionBaseCode.ID_IS_EMPTY, message = "id不能为空")
    private Long id;

    @ApiModelProperty(value = "应用类型 1-IOS 2-ANDROID 3-SCS(车载平板) 4-SaaS 5-Server(后台服务) 6-ROS",
            dataType = "Integer", required = true)
    @NotNull(code = ValidationExceptionBaseCode.APP_TYPE_IS_EMPTY, message = "应用类型不能为空")
    private Integer type;

    @ApiModelProperty(value = "发布类型 1-单个平板 2-多个平板 3-区域 4-全部平板(不需要传递平板序列号和区域id)",
            dataType = "Integer", required = true)
    @NotNull(code = ValidationExceptionBaseCode.RELEASE_TYPE_IS_EMPTY, message = "发布类型不能为空")
    private Integer releaseType;

    @ApiModelProperty(value = "平板序列号集合(用“,”号隔开)", dataType = "String")
    private String tabletSnList;

    @ApiModelProperty(value = "区域id集合(用“,”号隔开)", dataType = "String")
    private String areaIdList;

}
