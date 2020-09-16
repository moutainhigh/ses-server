package com.redescooter.ses.web.ros.vo.log;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassNameLogCountResult
 * @Description
 * @Author Aleks
 * @Date2020/9/16 20:29
 * @Version V1.0
 **/
@Data
public class LogCountResult extends GeneralResult {

    @ApiModelProperty("日志类型，1:登陆日志，2：操作日志，3：错误日志")
    private Integer type = 1;

    @ApiModelProperty("数量")
    private Integer num = 0;

}
