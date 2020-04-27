package com.redescooter.ses.mobile.rps.vo.purchasinwh;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
@ApiModel(value = "采购仓库待入库信息", description = "采购仓库待入库信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class PurchasDetailsListResult extends GeneralResult {
    @ApiModelProperty(value = "id")
    private Long id;
    @ApiModelProperty(value = "采购单号")
    private String contractNo;
    @ApiModelProperty(value = "待入库数量")
    private int inWaitWhQty;
    @ApiModelProperty(value = "部品号")
    private String partsNumber;
    @ApiModelProperty(value = "零配件中文名称")
    private String cnName;
    @ApiModelProperty(value = "是否有ID")
    private Boolean idClass;
}
