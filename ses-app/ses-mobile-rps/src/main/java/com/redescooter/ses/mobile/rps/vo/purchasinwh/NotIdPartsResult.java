package com.redescooter.ses.mobile.rps.vo.purchasinwh;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@ApiModel(value = "无ID采购仓库待入库", description = "无ID采购仓库待入库详情信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class NotIdPartsResult extends GeneralResult {
    @ApiModelProperty(value = "id")
    private Long id;
    @ApiModelProperty(value = "部品号")
    private String partsNumber;
    @ApiModelProperty(value = "质检批次号")
    private String batchNo;
    @ApiModelProperty(value = "零配件中文名称")
    private String cnName;
    @ApiModelProperty(value = "待入库数量")
    private int inWaitWhQty;
}
