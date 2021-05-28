package com.redescooter.ses.web.ros.vo.sim;

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

    @ApiModelProperty("账户名称")
    private String account_name;

    @ApiModelProperty("速率计划")
    private String rate_plan;

    @ApiModelProperty("计费周期")
    private String billing_cycle;

    @ApiModelProperty("每日使用量")
    private String daily_usage_mb;

    @ApiModelProperty("每月使用量")
    private String monthly_usage_mb;

    @ApiModelProperty("仪表sn")
    private String tabledSn;

    @ApiModelProperty("绘画计数")
    private String count_of_sessions;

    @ApiModelProperty("已发送短信")
    private String sms_sent;

    @ApiModelProperty("记录")
    private String notes;

    @ApiModelProperty("停用时间")
    private String deactivation_date;
}
