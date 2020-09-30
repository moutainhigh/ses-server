package com.redescooter.ses.web.ros.vo.restproduct;

import com.redescooter.ses.api.common.vo.base.GeneralResult;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @ClassName:SaveScooterPartListResult
 * @description: SaveScooterPartListResult
 * @author: Alex @Version：1.3
 * @create: 2020/02/25 12:39
 */
@ApiModel(value = "保存整车部件列表出参", description = "保存整车部件列表出参")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class RosProductionProductPartListResult extends GeneralResult {
    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "产品编号")
    private String partsNum;

    @ApiModelProperty(value = "部件区域Id")
    private Long secId;

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

    @ApiModelProperty(value = "图纸url")
    private String dwg;
}
