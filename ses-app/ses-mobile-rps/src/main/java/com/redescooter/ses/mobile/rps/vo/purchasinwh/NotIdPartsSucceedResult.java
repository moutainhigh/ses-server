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
public class NotIdPartsSucceedResult extends GeneralResult {
    @ApiModelProperty(value = "id")
    private Long id;
    @ApiModelProperty(value = "待入库数量")
    private int inWaitWhQty;

}
