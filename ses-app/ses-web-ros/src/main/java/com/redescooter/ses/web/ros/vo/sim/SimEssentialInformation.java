package com.redescooter.ses.web.ros.vo.sim;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName SimEssentialInformation
 * @Description sim基本信息 /
 * @Author Charles
 * @Date 2021/05/27 11:58
 */
@Data
@Builder
@ApiModel("sim基本信息")
@NoArgsConstructor
@AllArgsConstructor
public class SimEssentialInformation {

    @ApiModelProperty("账号")
    private int number;

    @ApiModelProperty("账户名称")
    private String name;

    @ApiModelProperty("当前余额(美元)")
    private Double current_balance;

    @ApiModelProperty("最低余额")
    private Double minimum_balance;

    @ApiModelProperty("是否存在平衡历史")
    private boolean is_exist_balance_history;

    @ApiModelProperty("余额低吗")
    private boolean is_low_balance;

    @ApiModelProperty("是否允许折扣")
    private boolean is_allowed_discount;
}
