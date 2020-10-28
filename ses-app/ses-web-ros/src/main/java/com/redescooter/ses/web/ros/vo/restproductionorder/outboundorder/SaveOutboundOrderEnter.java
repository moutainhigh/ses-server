package com.redescooter.ses.web.ros.vo.restproductionorder.outboundorder;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *  @author: alex
 *  @Date: 2020/10/26 16:52
 *  @version：V ROS 1.8.3
 *  @Description:
 */
@Data
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

    @ApiModelProperty(value = "出库单状态，0：待出库，10：质检中，20：已出库，30：已取消")
    private Integer outWhStatus;

    @ApiModelProperty(value = "出库单类型，1：整车，2：组装件，3：部件")
    private Integer outWhType;

    @ApiModelProperty(value = "出库类型，1：销售调拨")
    private Integer outType;

    @ApiModelProperty(value = "出库数量")
    private Integer outWhQty;

    @ApiModelProperty(value = "国家编码如+86")
    private String countryCode;

    @ApiModelProperty(value = "联系人电话")
    private String telephone;

    @ApiModelProperty(value = "邮箱")
    private String mail;

    @ApiModelProperty(value = "备注")
    private String remark;
}
