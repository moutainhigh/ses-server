package com.redescooter.ses.mobile.rps.vo.productwaitinwh;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

/**
 * @ClassNameAllocateAndProductResult
 * @Description
 * @Author kyle
 * @Date2020/4/26 14:30
 * @Version V1.0
 **/
@ApiModel(value = "调拨待入库单列表出参", description = "调拨待入库单列表出参")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class AllocateAndProductResult extends GeneralResult {

    @ApiModelProperty(value = "生产仓库（组装）可入库集合")
    private List<ProductWaitInWhOneResult> productWaitInWhOneResultList;

    @ApiModelProperty(value = "生产仓库（调拨）可入库集合")
    private List<AllocateWaitInWhOneResult> allocateWaitInWhOneResultList;


}
