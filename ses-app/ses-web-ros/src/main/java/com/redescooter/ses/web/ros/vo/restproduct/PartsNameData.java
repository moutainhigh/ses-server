package com.redescooter.ses.web.ros.vo.restproduct;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassNameCombinData
 * @Description
 * @Author Aleks
 * @Date2020/10/20 13:05
 * @Version V1.0
 **/
@Data
public class PartsNameData {

//    @ApiModelProperty("部件id")
//    private Long partsId;

    @ApiModelProperty("部件名称")
    private String partsName;

}
