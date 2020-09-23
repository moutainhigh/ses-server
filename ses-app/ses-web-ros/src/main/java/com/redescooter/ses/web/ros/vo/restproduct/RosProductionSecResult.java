package com.redescooter.ses.web.ros.vo.restproduct;

import com.redescooter.ses.api.common.vo.base.GeneralResult;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @ClassName:SecResult
 * @description: SecResult
 * @author: Alex @Version：1.3
 * @create: 2020/02/25 12:01
 */
@ApiModel(value = "sec区域出参", description = "sec区域出参")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class RosProductionSecResult extends GeneralResult {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "名字")
    private String name;

    @ApiModelProperty(value = "编码")
    private String code;

    @ApiModelProperty(value = "区域描述")
    private String description;
}
