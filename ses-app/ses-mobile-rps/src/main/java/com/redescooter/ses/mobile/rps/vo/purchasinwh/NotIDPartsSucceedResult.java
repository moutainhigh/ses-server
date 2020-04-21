package com.redescooter.ses.mobile.rps.vo.purchasinwh;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@ApiModel(value = "有ID采购入库成功", description = "有ID采购入库成功")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
class NotIdPartsSucceedResult extends GeneralResult {
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
