package com.redescooter.ses.web.delivery.vo.edorder;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.web.delivery.exception.ValidationExceptionCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @ClassName GetAddressOfLonLatEnter
 * @Author Jerry
 * @date 2020/02/24 11:22
 * @Description:
 */

@ApiModel(value = "地址经纬度获取", description = "地址经纬度获取")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@Builder
@EqualsAndHashCode(callSuper = false)
public class GetAddressOfLonLatEnter extends GeneralEnter {

    @ApiModelProperty(value = "订单主键", required = true)
    @NotNull(code = ValidationExceptionCode.ID_IS_EMPTY, message = "id 不为空")
    private long id;
    @ApiModelProperty(value = "收件人纬度", required = true)
    //@NotNull(code = ValidationExceptionCode.LAT_IS_EMPTY, message = "纬度不为空")
    private String recipientLatitude;
    @ApiModelProperty(value = "收件人经度", required = true)
    //@NotNull(code = ValidationExceptionCode.LNG_IS_EMPTY, message = "经度不为空")
    private String recipientLongitude;
    @ApiModelProperty(value = "寄件人纬度", required = true)
    //@NotNull(code = ValidationExceptionCode.LAT_IS_EMPTY, message = "纬度不为空")
    private String senderLatitude;
    @ApiModelProperty(value = "寄件人经度", required = true)
    //@NotNull(code = ValidationExceptionCode.LNG_IS_EMPTY, message = "经度不为空")
    private String senderLongitude;


}
