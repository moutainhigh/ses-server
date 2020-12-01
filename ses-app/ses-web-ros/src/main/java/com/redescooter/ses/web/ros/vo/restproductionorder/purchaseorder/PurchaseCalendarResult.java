package com.redescooter.ses.web.ros.vo.restproductionorder.purchaseorder;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @ClassNamePurchaseCalendarResult
 * @Description
 * @Author Aleks
 * @Date2020/10/28 14:59
 * @Version V1.0
 **/
@ApiModel(value = "采购单日历入参",description = "采购单日历出参")
@Data
public class PurchaseCalendarResult {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("采购单号")
    private String purchaseNo;

    @ApiModelProperty("采购单状态，0：草稿，10：待备货，20：备货中，30：待发货，40：待签收，50：已签收，60：已完成，70：已取消")
    private Integer purchaseStatus;

    @ApiModelProperty("采购数量")
    private Integer purchaseQty;

    @ApiModelProperty("交货日期")
    private Date deliveryDate;

    @ApiModelProperty("采购单类型，1：整车，2：组装件，3：部件")
    private Integer purchaseType;

}
