package com.redescooter.ses.web.ros.vo.restproductionorder.purchaseorder;

import com.baomidou.mybatisplus.annotation.TableField;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @ClassNamePuraseListResult
 * @Description
 * @Author Aleks
 * @Date2020/10/23 19:50
 * @Version V1.0
 **/
@Data
public class PuraseListResult extends GeneralResult {

    @ApiModelProperty(value = "主键id")
    private Long id;

    @ApiModelProperty(value = "采购单号")
    private String purchaseNo;

    @ApiModelProperty(value = "采购单状态，0：草稿，10：待备货，20：备货中，30：待发货，40：待签收，50：已签收，60：已完成，70：已取消")
    private Integer purchaseStatus;

    @ApiModelProperty(value = "调拨数量")
    private Integer purchaseQty;

    @ApiModelProperty(value = "采购总金额")
    private BigDecimal purchaseAmount;


    @ApiModelProperty(value = "交货日期")
    private Date deliveryDate;

    @ApiModelProperty(value = "工厂id")
    private Long factoryId;

    @ApiModelProperty(value = "工厂名称")
    private String factoryName;

    @ApiModelProperty(value = "收货人id")
    private Long consigneeUser;

    @ApiModelProperty(value = "收货人名称")
    private String consigneeUserName;

    @ApiModelProperty(value = "创建人id")
    private Long createdBy;

    @ApiModelProperty(value = "创建人名称")
    private String createdByName;

    @ApiModelProperty(value = "创建时间")
    private Date createdTime;


}
