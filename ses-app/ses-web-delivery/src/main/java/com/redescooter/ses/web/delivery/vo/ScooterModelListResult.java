package com.redescooter.ses.web.delivery.vo;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;
@ApiModel(value = "车辆型号出参", description = "车辆型号出参")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@Data
public class ScooterModelListResult extends GeneralResult {

    @ApiModelProperty(value = "车辆型号")
    private List<String> modelList;
}
