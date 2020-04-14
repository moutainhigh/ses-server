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

    @ApiModelProperty(value = "组装单号")
    private String scooterNum;

    @ApiModelProperty(value = "组装单id")
    private Long scooterId;

    @ApiModelProperty(value = "部件名称")
    private String partName;

    @ApiModelProperty(value = "部件型号/备注")
    private String partType;

    @ApiModelProperty(value = "部件id")
    private Long partId;

    @ApiModelProperty(value = "待组装总数")
    private Integer waitInWHNum;

}
