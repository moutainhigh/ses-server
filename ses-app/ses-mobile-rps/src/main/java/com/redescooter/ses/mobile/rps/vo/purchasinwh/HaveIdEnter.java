package com.redescooter.ses.mobile.rps.vo.purchasinwh;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.exception.ValidationExceptionCode;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @ClassNameHave
 * @Description
 * @Author Joan
 * @Date2020/4/28 18:33
 * @Version V1.0
 **/
@ApiModel(value = "有ID入参", description = "有ID入参")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class HaveIdEnter extends GeneralEnter {
    @ApiModelProperty(value = "主键",required = true)
    @NotNull(code = ValidationExceptionCode.ID_IS_EMPTY, message = "主键不能为空")
    private Long id;
    @NotNull(code = com.redescooter.ses.mobile.rps.exception.ValidationExceptionCode.SERIAL_NUM, message = "商品序列号不能为空")
    @ApiModelProperty(value = "商品序列号",required = true)
    private  String serialNum;

}
