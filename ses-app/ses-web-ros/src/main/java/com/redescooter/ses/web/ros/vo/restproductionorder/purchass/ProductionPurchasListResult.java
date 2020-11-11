package com.redescooter.ses.web.ros.vo.restproductionorder.purchass;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import lombok.*;

import java.util.Date;

import io.swagger.annotations.*;

/**
 * @ClassName:ProductionPurchasListResult
 * @description: ProductionPurchasListResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/11/10 14:43 
 */
@ApiModel(value = "采购单列表", description = "采购单列表")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
public class ProductionPurchasListResult extends GeneralResult {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "订单编号")
    private String purchaseNo;

    @ApiModelProperty(value = "状态")
    private Integer purchaseStatus;

    @ApiModelProperty(value = "数量")
    private Integer purchaseQty;

    @ApiModelProperty(value = "配送订单")
    private Date deliveryDate;

    @ApiModelProperty(value = "金额")
    private String purchaseAmount;

    @ApiModelProperty(value = "供应商Id")
    private Long factoryId;

    @ApiModelProperty(value = "供应商名称")
    private String factoryName;

    @ApiModelProperty(value = "采购人对接人Id")
    private Long dockingUser;

    @ApiModelProperty(value = "采购人对接人名称")
    private String dockingUserName;

    @ApiModelProperty(value = "采购人对接人国家代码")
    private String dockingCountryCode;

    @ApiModelProperty(value = "采购人对接人电话")
    private String dockingUserTelephone;

    @ApiModelProperty(value = "创建人")
    private Long createById;

    @ApiModelProperty(value = "创建人名字")
    private String createByFirstName;

    @ApiModelProperty(value = "创建人名字")
    private String createByLastName;

    @ApiModelProperty(value = "创建日期")
    private Date createDate;
}
