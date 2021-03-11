package com.redescooter.ses.web.ros.vo.restproductionorder.outboundorder;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 *  @author: alex
 *  @Date: 2020/10/22 13:47
 *  @version：V ROS 1.8.3
 *  @Description:
 */
@ApiModel(value = "出库单列表", description = "出库单列表")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class OutboundOrderListResult extends GeneralResult {
    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "采购单Id")
    private Long invoiceId;

    @ApiModelProperty(value = "采购单编号")
    private String invoiceNo;

    @ApiModelProperty(value = "出库单编号")
    private String outWhNo;

    @ApiModelProperty(value = "出库单状态，参考 OutBoundOrderStatusEnums")
    private Integer outWhStatus;

    @ApiModelProperty(value = "出库单类型 参考 productTypeEnums")
    private Integer outWhType;

    @ApiModelProperty(value = "出库数量")
    private Integer outWhQty;

    @ApiModelProperty(value = "出库单类型 销售、采购  参考 OutBoundOrderTypeEnums ")
    private Integer outType;

    @ApiModelProperty(value = "创建人Id")
    private Long createById;

    @ApiModelProperty(value = "创建人名称")
    private String createByFirstName;

    @ApiModelProperty(value = "创建人名称")
    private String createByLastName;

    @ApiModelProperty(value = "创建人电话国家代码")
    private String countryCode;

    @ApiModelProperty(value = "创建人电话")
    private String telephone;

    @ApiModelProperty(value = "创建人创建日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Date createDate;

    @ApiModelProperty(value = "创建人名称")
    private String createdByName = "--";
}
