package com.redescooter.ses.web.ros.vo.restproductionorder.outboundorder;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

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
    private Long purhcasId;

    @ApiModelProperty(value = "采购单编号")
    private String purhcasNo;

    @ApiModelProperty(value = "出库单编号")
    private String outWhNo;

    @ApiModelProperty(value = "出库单状态")
    private Integer outWhStatus;

    @ApiModelProperty(value = "出库单类型")
    private Integer outWhType;

    @ApiModelProperty(value = "出库数量")
    private Integer outWhQty;

    @ApiModelProperty(value = "创建人Id")
    private Long createById;

    @ApiModelProperty(value = "创建人名称")
    private String createBFirstName;

    @ApiModelProperty(value = "创建人名称")
    private String createByLastName;

    @ApiModelProperty(value = "创建人电话国家代码Id")
    private Long createByCountryCodeId;

    @ApiModelProperty(value = "创建人电话国家代码")
    private String createByCountryCode;

    @ApiModelProperty(value = "创建人电话")
    private String createByTelephone;

    @ApiModelProperty(value = "创建人创建日期")
    private Date createDate;

}
