package com.redescooter.ses.mobile.rps.vo.prowaitinwh;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.Date;
import java.util.List;

/**
 * @ClassNameProWaitInWHItemResult
 * @Description
 * @Author kyle
 * @Date2020/4/14 15:59
 * @Version V1.0
 **/
@ApiModel(value = "生产待入库部件详情出参", description = "生产待入库部件详情出参")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class ProWaitInWHItemResult extends GeneralResult {

    @ApiModelProperty(value = "主键", required = true)
    private Long id;

    @ApiModelProperty(value = "部件名称")
    private String partName;

    @ApiModelProperty(value = "组装单子表id")
    private Long scooterBId;

    @ApiModelProperty(value = "部件id")
    private Long partId;

    @ApiModelProperty(value = "部件编号")
    private String partStr;

    @ApiModelProperty(value = "部件数量")
    private Integer partNum;

}
