package com.redescooter.ses.web.ros.vo.sim;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName SimResult
 * @Description sim 卡信息
 * @Author Charles
 * @Date 2021/05/26 21:14
 */
@Data
@Builder
@ApiModel("sim 卡信息")
@NoArgsConstructor
@AllArgsConstructor
public class SimResult {

    @ApiModelProperty(value = "可用余额")
    private Double currentBalance;

    @ApiModelProperty(value = "sim卡数")
    private int simCount;

    @ApiModelProperty(value = "已激活卡数")
    private int activatedCount;

    @ApiModelProperty(value = "激活就绪卡数")
    private int activationReadyCount;

    @ApiModelProperty(value = "停用卡数")
    private int deactivatedCount;

    @ApiModelProperty(value = "暂停卡数")
    private int suspended;
}
