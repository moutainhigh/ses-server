package com.redescooter.ses.mobile.rps.vo.prowaitinwh;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

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
