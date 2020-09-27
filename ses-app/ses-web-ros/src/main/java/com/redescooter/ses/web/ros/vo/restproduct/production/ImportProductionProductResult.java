package com.redescooter.ses.web.ros.vo.restproduct.production;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.web.ros.vo.restproduct.RosProductionProductPartListResult;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

@Data // 生成getter,setter等函数
@AllArgsConstructor // 生成全参数构造函数
@NoArgsConstructor // 生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class ImportProductionProductResult extends GeneralResult {

    @ApiModelProperty(value = "是否成功")
    private Boolean success;

    @ApiModelProperty(value = "成功的部件")
    private List<RosProductionProductPartListResult> successProductPartListResult;

    @ApiModelProperty(value = "失败的部件")
    private List<RosProductionProductPartListResult> failProductPartListResult;
}
