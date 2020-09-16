package com.redescooter.ses.web.ros.vo.log;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @ClassNameLogListEnter
 * @Description
 * @Author Aleks
 * @Date2020/9/16 19:39
 * @Version V1.0
 **/
@Data
public class LogListResult extends GeneralResult {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("操作人编号")
    private String opUserCode = "";

    @ApiModelProperty("操作人姓名")
    private String opUserName = "";


    @ApiModelProperty("登陆时间/操作时间")
    private Date createdTime;

    @ApiModelProperty("IP地址")
    private String loginIp = "";

    @ApiModelProperty("耗时（毫秒）")
    private Long timeConsum;



}
