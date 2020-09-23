package com.redescooter.ses.web.ros.vo.restproduct.production;

import com.redescooter.ses.api.common.vo.base.GeneralResult;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

@Data // 生成getter,setter等函数
@AllArgsConstructor // 生成全参数构造函数
@NoArgsConstructor // 生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class RosProductionCombinationListResult extends GeneralResult {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "部件主键")
    private Long partsId;

    @ApiModelProperty(value = "产品编号")
    private String partsN;

    @ApiModelProperty(value = "部件区域")
    private String sec;

    @ApiModelProperty(value = "英文名称")
    private String enName;

    @ApiModelProperty(value = "中文名称")
    private String cnName;

    @ApiModelProperty(value = "法文名称")
    private String frName;

    @ApiModelProperty(value = "生产周期")
    private Integer productionCycle;

    @ApiModelProperty(value = "数量")
    private Integer qty;

    @ApiModelProperty(value = "部件列表")
    private List<RosProductionCombinationPartsResult> partsList;
}
