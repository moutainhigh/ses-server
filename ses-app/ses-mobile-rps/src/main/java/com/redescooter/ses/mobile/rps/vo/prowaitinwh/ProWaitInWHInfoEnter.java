package com.redescooter.ses.mobile.rps.vo.prowaitinwh;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.Date;

/**
 * @ClassNameProWaitInWHInfoEnter
 * @Description
 * @Author kyle
 * @Date2020/4/14 17:15
 * @Version V1.0
 **/
@ApiModel(value = "生产仓库入库信息入参", description = "生产仓库入库信息入参")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class ProWaitInWHInfoEnter extends GeneralEnter {

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
