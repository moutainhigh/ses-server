package com.redescooter.ses.web.ros.vo.restproductionorder.outboundorder;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.Invoiceorder.ProductEnter;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 *  @author: alex
 *  @Date: 2020/10/26 16:52
 *  @version：V ROS 1.8.3
 *  @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SaveOutboundOrderEnter extends GeneralEnter {
    @ApiModelProperty(value = "主键id")
    private Long id;

    @ApiModelProperty(value = "关联的发货单id")
    private Long invoiceId;

    @ApiModelProperty(value = "发货单号")
    private String invoiceNo;

    @ApiModelProperty(value = "出库单号")
    private String outWhNo;

    @ApiModelProperty(value = "出库单类型，1：整车，2：组装件，3：部件")
    private Integer outWhType;

    @ApiModelProperty(value = "出库类型，1：销售调拨")
    private Integer outType;

    @ApiModelProperty(value = "出库数量")
    private Integer outWhQty;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "产品列表")
    List<ProductEnter> productEnterList;
}
