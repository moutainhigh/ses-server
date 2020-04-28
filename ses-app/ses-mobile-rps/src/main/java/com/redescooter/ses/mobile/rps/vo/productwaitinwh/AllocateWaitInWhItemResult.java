package com.redescooter.ses.mobile.rps.vo.productwaitinwh;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @ClassNameAllocateWaitInWHItemResult
 * @Description
 * @Author kyle
 * @Date2020/4/26 16:54
 * @Version V1.0
 **/
@ApiModel(value = "调拨单待入库部件详情出参", description = "调拨单待入库部件详情出参")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class AllocateWaitInWhItemResult extends GeneralResult {

    @ApiModelProperty(value = "主键", required = true)
    private Long id;

    @ApiModelProperty(value = "部件名称")
    private String partName;

    @ApiModelProperty(value = "调拨单子表id")
    private Long allocateBId;

    @ApiModelProperty(value = "调拨单编号")
    private String allocateNum;

    @ApiModelProperty(value = "部件id")
    private Long partId;

    @ApiModelProperty(value = "部件编号")
    private String partStr;

    @ApiModelProperty(value = "部件数量")
    private Integer partNum;

    @ApiModelProperty(value = "仓库类型")
    private String wHType;

    @ApiModelProperty(value = "有无id")
    private Boolean idFlag;

}
