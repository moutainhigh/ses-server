package com.redescooter.ses.web.ros.vo.account;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import io.swagger.annotations.*;

/**
 * @ClassName:CreateCustomerAccountEnter
 * @description: OpenAccountEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2019/12/18 17:39
 */
@ApiModel(value = "客户开通", description = "客户开通")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class OpenAccountEnter extends GeneralEnter {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "开始激活时间")
    private String startActivationTime;

    @ApiModelProperty(value = "结束激活时间")
    private String endActivationTime;
}
