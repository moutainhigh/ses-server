package com.redescooter.ses.mobile.rps.vo.prowaitinwh;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import lombok.*;

import io.swagger.annotations.*;

/**
 * @ClassNameProWaitInWHInfoResult
 * @Description
 * @Author kyle
 * @Date2020/4/14 17:10
 * @Version V1.0
 **/
@ApiModel(value = "生产仓库入库信息出参", description = "生产仓库入库信息出参")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class ProWaitInWHInfoResult extends GeneralResult {

    @ApiModelProperty(value = "主键",required = true)
    private Long id;

    @ApiModelProperty(value = "组装单id")
    private Long scooterId;

    @ApiModelProperty(value = "部品id")
    private Long partId;

    @ApiModelProperty(value = "部品名称")
    private String partName;

    @ApiModelProperty(value = "部品号")
    private String partNum;

    @ApiModelProperty(value = "批次号")
    private String batchNum;

    @ApiModelProperty(value = "应入库数量")
    private Integer shouldInWHNum;

    @ApiModelProperty(value = "入库数量")
    private Integer inWHNum;

    @ApiModelProperty(value = "剩余数量")
    private Integer residueNum;

}
