package com.redescooter.ses.web.ros.vo.restproduct;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;

import lombok.*;

import io.swagger.annotations.*;

@ApiModel(value = "", description = "")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class RosProuductionTypeEnter extends GeneralEnter {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "产品类型")
    private Integer productionProductType;

    @ApiModelProperty(value = "table类型")
    private Integer classType;
}
