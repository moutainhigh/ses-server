package com.redescooter.ses.web.ros.vo.log;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @ClassNameLogDetailResult
 * @Description
 * @Author Aleks
 * @Date2020/9/16 20:33
 * @Version V1.0
 **/
@Data
public class LogDetailResult extends GeneralResult {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("操作模块")
    private String opModul = "";

    @ApiModelProperty("操作人部门")
    private String opUserDeptName = "";

    @ApiModelProperty("操作人姓名")
    private String opUserName = "";

    @ApiModelProperty("IP地址")
    private String loginIp = "";

    @ApiModelProperty("请求地址")
    private String requestAddress;

    @ApiModelProperty("请求方式")
    private String requestParam;

    @ApiModelProperty("返回参数")
    private String responseParam;

    @ApiModelProperty("异常信息")
    private String expMsg = "";

    @ApiModelProperty("请求是否成功，1：是，2：否")
    private Integer ifSuccess;
}
