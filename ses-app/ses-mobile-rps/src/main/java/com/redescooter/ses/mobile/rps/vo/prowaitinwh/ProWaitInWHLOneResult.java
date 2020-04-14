package com.redescooter.ses.mobile.rps.vo.prowaitinwh;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.Date;

/**
 * @ClassNameProWaitInWHLOneResult
 * @Description
 * @Author kyle
 * @Date2020/4/14 15:38
 * @Version V1.0
 **/
@ApiModel(value = "生产待入库单列表出参", description = "生产待入库单列表出参")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class ProWaitInWHLOneResult extends GeneralResult {

    @ApiModelProperty(value = "主键", required = true)
    private Long id;

    @ApiModelProperty(value = "待入库单单号")
    private String waitInWHStr;

    @ApiModelProperty(value = "组装单id")
    private Long scooterId;

    @ApiModelProperty(value = " 加入待入库列表时间")
    private Date inWHTListTime;

    @ApiModelProperty(value = "待入库总数")
    private Integer waitInWHNum;

}
