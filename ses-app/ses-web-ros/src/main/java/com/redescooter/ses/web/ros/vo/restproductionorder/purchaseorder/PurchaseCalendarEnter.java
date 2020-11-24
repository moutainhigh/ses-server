package com.redescooter.ses.web.ros.vo.restproductionorder.purchaseorder;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassNamePurchaseCalendarEnter
 * @Description
 * @Author Aleks
 * @Date2020/10/28 14:56
 * @Version V1.0
 **/
@Data
@ApiModel(value = "采购单日历入参",description = "采购单日历入参")
public class PurchaseCalendarEnter extends GeneralEnter {

    @ApiModelProperty("交货日期")
    private String deliveryDate;

}
