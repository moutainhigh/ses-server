package com.redescooter.ses.web.ros.vo.log;

import com.redescooter.ses.api.common.vo.base.PageEnter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * @ClassNameLogListEnter
 * @Description
 * @Author Aleks
 * @Date2020/9/16 19:46
 * @Version V1.0
 **/
@Data
public class LogListEnter extends PageEnter {

    @ApiModelProperty("关键字")
    private String keyword;


    @ApiModelProperty(value = "开始时间")
    private String createStartDateTime;


    @ApiModelProperty(value = "结束时间")
    private String createEndDateTime;


    @ApiModelProperty("类型，1：登陆，2：操作，3：错误")
    private String classType;

}
