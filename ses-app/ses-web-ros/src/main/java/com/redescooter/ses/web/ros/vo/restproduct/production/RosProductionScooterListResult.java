package com.redescooter.ses.web.ros.vo.restproduct.production;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import lombok.*;

import io.swagger.annotations.*;

@ApiModel(value = "车辆列表出参", description = "车辆列表出参")
@Data // 生成getter,setter等函数
@AllArgsConstructor // 生成全参数构造函数
@NoArgsConstructor // 生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class RosProductionScooterListResult extends GeneralEnter {
    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "产品名称")
    private String productNum;

    @ApiModelProperty(value = "分组Id")
    private Long groupId;

    @ApiModelProperty(value = "分组名字")
    private String groupName;

    @ApiModelProperty(value = "产品名称")
    private String enName;

    @ApiModelProperty(value = "产品名称")
    private String cnName;

    @ApiModelProperty(value = "产品名称")
    private String frName;

    @ApiModelProperty(value = "产品名称")
    private Integer qty;

    @ApiModelProperty(value = "生产周期")
    private Integer productionCycle;
}
