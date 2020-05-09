package com.redescooter.ses.mobile.rps.vo.productwaitinwh;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

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
public class ProductWaitInWhItemResult extends GeneralResult {

    @ApiModelProperty(value = "组装单子表id")
    private Long id;

    @ApiModelProperty(value = "产品名称")
    private String productName;

    @ApiModelProperty(value = "产品id")
    private Long productId;

    @ApiModelProperty(value = "产品编号")
    private String productN;

    @ApiModelProperty(value = "产品数量")
    private Integer productNum;

    @ApiModelProperty(value = "单据类型")
    private String sourceType;

    @ApiModelProperty(value = "可选序列号集合")
    private List<String> serialNumList;


}
