package com.redescooter.ses.web.ros.vo.bom;

import com.redescooter.ses.api.common.vo.base.GeneralResult;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @ClassName:SaveScooterPartListResult
 * @description: SaveScooterPartListResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/02/25 12:39
 */
@ApiModel(value = "保存整车部件列表出参", description = "保存整车部件列表出参")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class QueryPartListResult extends GeneralResult {
    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "部件主键")
    private Long pratsId;

    @ApiModelProperty(value = "产品编号")
    private String partsNumber;

    @ApiModelProperty(value = "部件区域")
    private String sec;

    @ApiModelProperty(value = "英文名称")
    private String enName;

    @ApiModelProperty(value = "中文名称")
    private String cnName;

    @ApiModelProperty(value = "法文名称")
    private String frName;

    @ApiModelProperty(value = "生产周期")
    private String productionCycle;

    @ApiModelProperty(value = "数量")
    private Integer qty;
}
