package com.redescooter.ses.web.ros.vo.sim;

import com.fasterxml.jackson.annotation.JsonAlias;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName SimCardListResult
 * @Description sim card list  /iot_sim_cards
 * @Author Charles
 * @Date 2021/05/27 12:54
 */
@Data
@Builder
@ApiModel("sim card list")
@NoArgsConstructor
@AllArgsConstructor
public class SimCardListResult {

    @ApiModelProperty("状态: {已激活:1-Activated, 激活就绪:4-Activation Ready, 停用:2-Deactivated, 暂停:3-Suspended}")
    private String status;

    @ApiModelProperty("iccid")
    private String iccid;

    @JsonAlias(value = {"account_name", "accountName"})
    @ApiModelProperty("账户名称")
    private String accountName;

    @JsonAlias(value = {"rate_plan", "ratePlan"})
    @ApiModelProperty("速率计划")
    private String ratePlan;

    @JsonAlias(value = {"billing_cycle", "billingCycle"})
    @ApiModelProperty("计费周期")
    private String billingCycle;

    @JsonAlias(value = {"daily_usage_mb", "dailyUsageMb"})
    @ApiModelProperty("每日使用量")
    private String dailyUsageMb;

    @JsonAlias(value = {"monthly_usage_mb", "monthlyUsageMb"})
    @ApiModelProperty("每月使用量")
    private String monthlyUsageMb;

    @ApiModelProperty("仪表sn")
    private String tabledSn;

    @JsonAlias(value = {"count_of_sessions", "countOfSessions"})
    @ApiModelProperty("绘画计数")
    private String countOfSessions;

    @JsonAlias(value = {"sms_sent", "smsSent"})
    @ApiModelProperty("已发送短信")
    private String smsSent;

    @ApiModelProperty("记录")
    private String notes;

    @JsonAlias(value = {"deactivation_date", "deactivationDate"})
    @ApiModelProperty("停用时间")
    private String deactivationDate;
}
