package com.redescooter.ses.web.ros.vo.specificat;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassNameSpecificatDefResult
 * @Description
 * @Author Aleks
 * @Date2020/9/29 11:03
 * @Version V1.0
 **/
@Data
public class SpecificatDefResult {

    @ApiModelProperty(value="自定义名称")
    private String defName;

    @ApiModelProperty(value="自定义的值")
    private String defValue;

}
