package com.redescooter.ses.web.ros.vo.restproduct.production;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import lombok.*;

import io.swagger.annotations.*;

@ApiModel(value = "版本状态出参", description = "版本出参")
@Data // 生成getter,setter等函数
@AllArgsConstructor // 生成全参数构造函数
@NoArgsConstructor // 生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class RosProductionProdductVersionResult extends GeneralResult {

    @ApiModelProperty(value = "版本Id")
    private Long id;

    @ApiModelProperty(value = "版本状态名称")
    private String name;

    @ApiModelProperty(value = "版本状态状态")
    private Integer status;

    @ApiModelProperty(value = "激活版本")
    private Boolean activate;
}
