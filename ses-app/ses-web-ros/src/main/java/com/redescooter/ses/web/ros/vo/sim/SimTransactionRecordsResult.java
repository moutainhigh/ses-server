package com.redescooter.ses.web.ros.vo.sim;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName SimTransactionRecordsResult
 * @Description sim卡交易记录 /balance_payments
 * @Author Charles
 * @Date 2021/05/27 11:49
 */
@Data
@Builder
@ApiModel("sim卡交易记录")
@NoArgsConstructor
@AllArgsConstructor
public class SimTransactionRecordsResult {

    @ApiModelProperty("充值时间")
    private String date;

    @ApiModelProperty("充值金额")
    private String amount;

    @ApiModelProperty("交易id")
    private String transaction_id;

    @ApiModelProperty("最后信用卡数字")
    private String last_credit_card_digits;

    @ApiModelProperty("创建时间")
    private String created_at;

    @ApiModelProperty("修改时间")
    private String updated_at;
}
