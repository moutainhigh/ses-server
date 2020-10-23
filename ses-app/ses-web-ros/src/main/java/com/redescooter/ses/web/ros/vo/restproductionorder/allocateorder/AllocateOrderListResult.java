package com.redescooter.ses.web.ros.vo.restproductionorder.allocateorder;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @ClassNameAllocateOrderListEnter
 * @Description
 * @Author Aleks
 * @Date2020/10/23 11:56
 * @Version V1.0
 **/
@Data
public class AllocateOrderListResult extends GeneralResult {

    @ApiModelProperty(value = "主键id")
    private Long id;

    @ApiModelProperty(value = "调拨单号")
    private String allocateNo;

    @ApiModelProperty(value = "调拨单状态，0：草稿，10：待处理，20：采购中，30：待发货，40：待签收，50：已签收，60：已完成，70：已取消")
    private Integer allocateStatus;

    @ApiModelProperty(value = "调拨数量")
    private Integer allocateQty;

    @ApiModelProperty(value = "调拨单类型，1：整车，2：组装件，3：部件")
    private Integer allocateType;

    @ApiModelProperty(value = "发货仓库id")
    private Long shipWh;

    @ApiModelProperty(value = "发货仓库名称")
    private String shipWhName;

    @ApiModelProperty(value = "接受仓库id")
    private Long receiptWh;

    @ApiModelProperty(value = "接受仓库名称")
    private String receiptWhName;

    @ApiModelProperty(value = "收货人id")
    private Long consigneeUser;

    @ApiModelProperty(value = "收货人名称")
    private String consigneeUserName;

    @ApiModelProperty(value = "收货人电话")
    private String consigneeUserTelephone;

    @ApiModelProperty(value = "收货人邮箱")
    private String consigneeUserMail;

    @ApiModelProperty(value = "创建时间")
    private Date createdTime;



}
