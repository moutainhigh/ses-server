package com.redescooter.ses.mobile.rps.vo.purchasinwh;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.sql.Date;
@ApiModel(value = "采购仓库待入库信息", description = "采购仓库待入库信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class PutStorageResult extends GeneralResult {
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    @ApiModelProperty(value = "采购单号")
    private String purchasId;
    @ApiModelProperty(value = "总数量")
    private String inWaitWhTotal;
    @ApiModelProperty(value = "部品id")
    private String partId;
}
