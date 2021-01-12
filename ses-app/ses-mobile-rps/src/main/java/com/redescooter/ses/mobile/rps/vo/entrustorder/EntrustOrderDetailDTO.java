package com.redescooter.ses.mobile.rps.vo.entrustorder;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.redescooter.ses.api.common.constant.DateConstant;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * 委托单详情返回结果对象 DTO
 * @author assert
 * @date 2021/1/4 10:22
 */
@Data
@ApiModel(value = "委托单详情返回结果对象")
public class EntrustOrderDetailDTO extends GeneralResult {

    @ApiModelProperty(value = "委托单id", dataType = "Long")
    private Long id;

    @ApiModelProperty(value = "委托单号", dataType = "String")
    private String entrustNo;

    @ApiModelProperty(value = "委托单状态，0：待发货 10：待签收 20：已签收", dataType = "Integer")
    private Integer status;

    @ApiModelProperty(value = "委托单类型，1：整车，2：组装件，3：部件", dataType = "Integer")
    private Integer type;

    @DateTimeFormat(pattern = DateConstant.DEFAULT_DATE_FORMAT)
    @JsonFormat(pattern = DateConstant.DEFAULT_DATE_FORMAT, timezone = DateConstant.UTC)
    @ApiModelProperty(value = "交货日期", dataType = "Date")
    private Date deliveryDate;

    @DateTimeFormat(pattern = DateConstant.DEFAULT_DATETIME_FORMAT)
    @JsonFormat(pattern = DateConstant.DEFAULT_DATETIME_FORMAT, timezone = DateConstant.UTC)
    @ApiModelProperty(value = "实际发货时间", dataType = "Date")
    private Date actualDeliveryTime;

    @ApiModelProperty(value = "运输方式 1：海运，2：陆运，3：空运", dataType = "Integer")
    private Integer transType;

    @ApiModelProperty(value = "应发货数量", dataType = "Integer")
    private Integer qty;

    @ApiModelProperty(value = "已发货数量", dataType = "Integer")
    private Integer alreadyConsignorQty;

    @ApiModelProperty(value = "委托单产品信息 车辆、组装件、部件信息")
    private List<EntrustOrderProductDTO> productList;

}
