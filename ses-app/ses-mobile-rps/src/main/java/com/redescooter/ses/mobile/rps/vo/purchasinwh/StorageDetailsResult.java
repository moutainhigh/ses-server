package com.redescooter.ses.mobile.rps.vo.purchasinwh;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
@ApiModel(value = "采购仓库待入库详情信息", description = "采购仓库待入库详情信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StorageDetailsResult extends GeneralResult {
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    @ApiModelProperty(value = "采购单号")
    private String contractNo;
    @ApiModelProperty(value = "零配件中文名称")
    private String cnName;
    @ApiModelProperty(value = "部品号")
    private String artsNumber;
    @ApiModelProperty(value = "入库数量")
    private String inWaitWhTotal;
}
