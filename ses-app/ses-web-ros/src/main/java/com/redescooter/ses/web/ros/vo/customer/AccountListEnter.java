package com.redescooter.ses.web.ros.vo.customer;

import com.redescooter.ses.api.common.vo.base.PageEnter;
import lombok.Data;

import io.swagger.annotations.*;

/**
 * @ClassName:AccountListEnter
 * @description: AccountListEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2019/12/23 13:20
 */
@ApiModel(value = "账户列表入参", description = "账户列表入参")
@Data
public class AccountListEnter extends PageEnter {

    @ApiModelProperty(value = "状态")
    private String status;

    @ApiModelProperty(value = "客户类型")
    private String customerType;

    @ApiModelProperty(value = "行业")
    private String industryType;

    @ApiModelProperty(value = "激活开始时间")
    private String startActivationTime;

    @ApiModelProperty(value = "激活结束时间")
    private String endActivationTime;

    @ApiModelProperty(value = "到期开始时间")
    private String endExpirationTime;

    @ApiModelProperty(value = "到期结束时间")
    private String startExpirationTime;

    @ApiModelProperty(value = "关键字")
    private String keyword;
}
