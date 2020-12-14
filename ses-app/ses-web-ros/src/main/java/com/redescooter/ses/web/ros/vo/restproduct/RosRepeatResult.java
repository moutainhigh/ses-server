package com.redescooter.ses.web.ros.vo.restproduct;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassNameRosRepeatResult
 * @Description
 * @Author Aleks
 * @Date2020/9/25 16:35
 * @Version V1.0
 **/
@Data
public class RosRepeatResult implements Serializable {

    @ApiModelProperty("部件号")
    private String partsNo;

    @ApiModelProperty("中文名称")
    private String cnName;

    @ApiModelProperty("英文名称")
    private String enName;

    @ApiModelProperty("法文名称")
    private String frName;

}
