package com.redescooter.ses.web.ros.vo.sellsy.enter.client;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.web.ros.exception.ThirdValidationExceptionCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@ApiModel(value = "客户地址", description = "客户地址")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class SellsyClientAddressEnter {

    @ApiModelProperty(value = "客户Id")
    @NotNull(code = ThirdValidationExceptionCode.SELLSY_CLIENT_ID_IS_EMPTY, message = "客户Id 为空")
    private int clientid;

    @ApiModelProperty(value = "客户地址")
    @NotNull(code = ThirdValidationExceptionCode.SELLSY_ADDRESS_ID_EMPTY, message = "地址Id 为空")
    private int addressid;
}
