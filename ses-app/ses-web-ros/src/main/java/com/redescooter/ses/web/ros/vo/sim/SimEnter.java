package com.redescooter.ses.web.ros.vo.sim;

import com.redescooter.ses.api.common.vo.base.PageEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName SimEnter
 * @Description sim 请求数据
 * @Author Charles
 * @Date 2021/05/27 13:16
 */
@Data
@ApiModel("sim 请求数据")
public class SimEnter extends PageEnter {

    @ApiModelProperty("充值时间")
    private String transactionDate;

    @ApiModelProperty("充值id")
    private String transactionId;

    @ApiModelProperty("停用时间")
    private String deactivationDate;

    @ApiModelProperty("状态: {已激活:1-Activated, 激活就绪:4-Activation Ready, 停用:2-Deactivated, 暂停:3-Suspended}")
    private String status;

    @ApiModelProperty("iccid")
    private String iccid;

    @ApiModelProperty("连接开始时间")
    private String connectStartTime;

    @ApiModelProperty("连接结束时间")
    private String connectOverTime;

    @ApiModelProperty("国家代码")
    private String countryCode;

    @ApiModelProperty("每日统计日期  年月")
    private String dailyStatisticsDate;
}
