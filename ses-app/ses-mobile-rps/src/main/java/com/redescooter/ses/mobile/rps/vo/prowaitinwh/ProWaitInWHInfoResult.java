package com.redescooter.ses.mobile.rps.vo.prowaitinwh;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import lombok.*;

import io.swagger.annotations.*;

import java.util.Date;

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

    @ApiModelProperty(value = "部品名称")
    private String partName;

    @ApiModelProperty(value = "部品号")
    private String partNum;

    @ApiModelProperty(value = "批次号")
    private String batchNum;

    @ApiModelProperty(value = "剩余数量")
    private Integer residueNum;

    @ApiModelProperty(value = "生产日期")
    private Date proTime;

    @ApiModelProperty(value = "序列号")
    private String Num;

}
