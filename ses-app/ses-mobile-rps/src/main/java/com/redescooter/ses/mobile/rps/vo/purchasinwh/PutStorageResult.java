package com.redescooter.ses.mobile.rps.vo.purchasinwh;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.redescooter.ses.api.common.constant.DateConstant;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@ApiModel(value = "采购仓库待入库信息", description = "采购仓库待入库信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class PutStorageResult extends GeneralResult {
    @ApiModelProperty(value = "id")
    private Long id;
    @DateTimeFormat(pattern = DateConstant.DEFAULT_DATETIME_FORMAT)
    @JsonFormat(pattern = DateConstant.DEFAULT_DATETIME_FORMAT, timezone = DateConstant.UTC)
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    @ApiModelProperty(value = "采购单号")
    private String contractNo;
    @ApiModelProperty(value = "待入库总数量")
    private int inWaitWhTotal;
}
