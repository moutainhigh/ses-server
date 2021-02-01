package com.redescooter.ses.web.ros.vo.restproductionorder.purchaseorder;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.exception.ValidationExceptionBaseCode;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassNameCancalOrderEnter
 * @Description
 * @Author Aleks
 * @Date2020/10/28 16:43
 * @Version V1.0
 **/
@Data
@ApiModel(value = "取消订单入参", description = "取消订单入参")
public class CancelOrderEnter extends GeneralEnter {

    @ApiModelProperty("取消理由")
    private String remark;

    @ApiModelProperty(value = "ID", required = true)
    @NotNull(code = ValidationExceptionBaseCode.ID_IS_EMPTY, message = "主键不能为空")
    private Long id;

}
