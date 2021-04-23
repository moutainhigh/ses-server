package com.redescooter.ses.web.ros.vo.specificat;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SpecificatTypeResult {
    @ApiModelProperty(value="规格id")
    private Long specificatId;

    @ApiModelProperty(value="规格名称")
    private String specificatName;

}
