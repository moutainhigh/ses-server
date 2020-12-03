package com.redescooter.ses.api.foundation.vo.app;

import com.redescooter.ses.api.common.annotation.NotNull;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 发布版本入参DTO
 * @author assert
 * @date 2020/12/3 16:47
 */
@Data
@ApiModel(value = "发布版本入参")
public class ReleaseAppVersionParamDTO {

    @ApiModelProperty(value = "主键id", dataType = "Long", required = true)
    @NotNull(code = 1, message = "id不能为空")
    private Long id;

    @ApiModelProperty(value = "发布类型 1-单个平板 2-多个平板 3-区域 4-全部平板(不需要传递平板序列号和区域id)",
            dataType = "Integer", required = true)
    @NotNull(code = 1, message = "发布类型不能为空")
    private Integer releaseType;

    @ApiModelProperty(value = "平板序列号集合", dataType = "List<String>")
    private List<String> tabletSnList;

    @ApiModelProperty(value = "区域id集合", dataType = "List<Long>")
    private List<Long> areaIdList;

}
