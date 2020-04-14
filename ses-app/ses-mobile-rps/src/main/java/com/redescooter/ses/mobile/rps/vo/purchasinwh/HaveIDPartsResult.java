package com.redescooter.ses.mobile.rps.vo.purchasinwh;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import lombok.*;

import io.swagger.annotations.*;

@ApiModel(value = "有ID采购待入库", description = "有ID采购待入库")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class HaveIDPartsResult extends GeneralResult {
    @ApiModelProperty(value = "部品号")
    private String partsNumber;
    @ApiModelProperty(value = "质检批次号")
    private String batchNo;
    @ApiModelProperty(value = "零配件中文名称")
    private String cnName;
    @ApiModelProperty(value = "商品序列号")
    private  String serialNum;
    @ApiModelProperty(value = "待入库数量")
    private String inWaitWhQty;

}
