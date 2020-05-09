package com.redescooter.ses.web.ros.vo.bom.sales;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @ClassName ProductGetTypeResult
 * @Author Jerry
 * @date 2020/03/06 16:51
 * @Description:
 */

@ApiModel(value = "产品类型出参", description = "产品类型出参")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class ProductGetTypeResult extends GeneralResult{

    @ApiModelProperty(value = "key值")
    private String key;

    @ApiModelProperty(value = "value值")
    private Integer value;
}
