package com.redescooter.ses.web.ros.vo.sim;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName SimCardSessionsResult
 * @Description sim card session /sessions
 * @Author Charles
 * @Date 2021/05/27 13:57
 */
@Data
@Builder
@ApiModel("sim card session")
@NoArgsConstructor
@AllArgsConstructor
public class SimCardSessionsResult {

    @ApiModelProperty(value = "iccid", hidden = true)
    private String iccid;

    @ApiModelProperty("开始时间")
    private String start_date;

    @ApiModelProperty("结束时间")
    private String end_date;

    @ApiModelProperty(value = "计费周期", hidden = true)
    private String billing_cycle;

    @ApiModelProperty("国家代码")
    private String country_code;

    @ApiModelProperty("持续时间秒")
    private String duration_sec;

    @ApiModelProperty("使用kb")
    private String usage_kb;
}
