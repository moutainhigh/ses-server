package com.redescooter.ses.mobile.rps.vo.purchasinwh;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value = "无ID采购仓库待入库", description = "无ID采购仓库待入库详情信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotIDPartsListResult extends GeneralResult {
    @ApiModelProperty(value = "部品号")
    private String artsNumber;
    @ApiModelProperty(value = "质检批次号")
    private String batchNo;
    @ApiModelProperty(value = "零配件中文名称")
    private String cnName;
}
