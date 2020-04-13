package com.redescooter.ses.web.ros.vo.bom.sales;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @ClassName SubentryProductResult
 * @Author Jerry
 * @date 2020/03/06 14:28
 * @Description:
 */
@ApiModel(value = "产品子条目", description = "产品子条目")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class SubentryProductResult extends GeneralResult {

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "产品编码")
    private String partsNumber;

    @ApiModelProperty(value = "中文名称")
    private String cnName;

    @ApiModelProperty(value = "法文名称")
    private String frName;

    @ApiModelProperty(value = "英文名称")
    private String enName;

    @ApiModelProperty(value = "数量")
    private String qty;
}
