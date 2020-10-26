package com.redescooter.ses.web.ros.vo.restproductionorder.allocateorder;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.optrace.OpTraceResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @ClassNameAllocateOrderDetailResult
 * @Description
 * @Author Aleks
 * @Date2020/10/23 16:15
 * @Version V1.0
 **/
@Data
@ApiModel(value = "调拨单详情出参", description = "调拨单详情出参")
public class AllocateOrderDetailResult extends GeneralResult {

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

    @ApiModelProperty(value = "收货人国家编码如+86")
    private String consigneeCountryCode;

    @ApiModelProperty(value = "收货人电话")
    private String consigneeUserTelephone;

    @ApiModelProperty(value = "收货人邮箱")
    private String consigneeUserMail;

    @ApiModelProperty(value = "收货人邮编")
    private String consigneePostCode;

    @ApiModelProperty("通知人名称")
    private String notifyUserName;

    @ApiModelProperty("通知人国家编码如+86")
    private String notifyCountryCode;

    @ApiModelProperty("通知人电话")
    private Long notifyUserTelephone;

    @ApiModelProperty("通知人邮箱")
    private Long notifyUserMail;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty("车辆调拨产品")
    private List<AllocateOrderScooterDetailResult> scooters;

    @ApiModelProperty("组装件调拨产品")
    private List<AllocateOrderCombinDetailResult> combins;

    @ApiModelProperty("部件调拨产品")
    private List<AllocateOrderPartsDetailResult> parts;

    @ApiModelProperty("操作动态")
    private List<OpTraceResult> opTraces;


    // todo 关联的委托单

}
