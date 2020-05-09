package com.redescooter.ses.mobile.rps.vo.purchasinwh;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import lombok.*;

import io.swagger.annotations.*;

@ApiModel(value = "有ID采购待入库", description = "有ID采购待入库")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class HaveIdPartsResult extends GeneralResult {
    @ApiModelProperty(value = "id")
    private Long id;
    @ApiModelProperty(value = "待入库数量")
    private int inWaitWhQty;


}
