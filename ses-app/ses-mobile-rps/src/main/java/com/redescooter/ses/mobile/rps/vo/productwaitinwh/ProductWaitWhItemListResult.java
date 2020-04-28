package com.redescooter.ses.mobile.rps.vo.productwaitinwh;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

/**
 * @ClassNameProWaitWHItemListResult
 * @Description
 * @Author kyle
 * @Date2020/4/14 16:51
 * @Version V1.0
 **/
@ApiModel(value = "生产待入库部件详情列表出参", description = "生产待入库部件详情列表出参")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class ProductWaitWhItemListResult extends GeneralResult {

    @ApiModelProperty(value = "主键", required = true)
    private Long id;

    @ApiModelProperty(value = "组装单号")
    private List<ProductWaitInWhItemResult> productWaitInWhItemResultList;

}
