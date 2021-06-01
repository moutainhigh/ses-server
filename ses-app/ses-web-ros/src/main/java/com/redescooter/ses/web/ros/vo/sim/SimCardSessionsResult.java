package com.redescooter.ses.web.ros.vo.sim;

import com.fasterxml.jackson.annotation.JsonAlias;
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

    @JsonAlias(value = {"start_date", "startDate"})
    @ApiModelProperty("开始时间")
    private String startDate;

    @JsonAlias(value = {"end_date", "endDate"})
    @ApiModelProperty("结束时间")
    private String endDate;

    @JsonAlias(value = {"billing_cycle", "billingCycle"})
    @ApiModelProperty(value = "计费周期")
    private String billingCycle;

    @JsonAlias(value = {"country_code", "countryCode"})
    @ApiModelProperty("国家代码")
    private String countryCode;

    @JsonAlias(value = {"duration_sec", "durationSec"})
    @ApiModelProperty("持续时间秒")
    private String durationSec;

    @JsonAlias(value = {"usage_kb", "usageKb"})
    @ApiModelProperty("使用kb")
    private String usageKb;
}
