package com.redescooter.ses.web.ros.vo.restproduct;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassNamePartsNoData
 * @Description
 * @Author Aleks
 * @Date2020/10/20 14:47
 * @Version V1.0
 **/
@Data
public class PartsNoData {

    @ApiModelProperty("部件编号")
    private String partsNo;

    @ApiModelProperty("部件id")
    private Long partsId;
}
