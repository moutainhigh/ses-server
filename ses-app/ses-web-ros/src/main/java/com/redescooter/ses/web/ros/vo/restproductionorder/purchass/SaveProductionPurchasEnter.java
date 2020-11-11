package com.redescooter.ses.web.ros.vo.restproductionorder.purchass;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import lombok.*;

import java.util.Date;

import io.swagger.annotations.*;

/**
 * @ClassName:SaveProductionPurchasEnter
 * @description: SaveProductionPurchasEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/11/10 16:08 
 */
@ApiModel(value = "保存采购接口", description = "保存采购接口")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
public class SaveProductionPurchasEnter extends GeneralEnter {


    @ApiModelProperty(value = "供应商Id")
    private Long factoryId;

    @ApiModelProperty(value = "交货日期")
    private Date deliveryDate;

    @ApiModelProperty(value = "负责人")
    private Long factoryPrincipalId;

    @ApiModelProperty(value = "负责人")
    private String factoryPrincipalName;

    @ApiModelProperty(value = "负责人国家代码")
    private String factoryPrincipalCountryCode;

    @ApiModelProperty(value = "负责人电话")
    private String factoryPrincipalTelephone;

    @ApiModelProperty(value = "收货人Id")
    private Long consigneeUser;

    @ApiModelProperty(value = "收货人姓名")
    private String consigneeUserName;

    @ApiModelProperty(value = "收货人国家代码")
    private String consigneeCountryCode;

    @ApiModelProperty(value = "收货人电话")
    private String consigneeUserTelephone;

    @ApiModelProperty(value = "收货人地址")
    private String consigneeAddress;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "收货日期")
    private String productDate;

    @ApiModelProperty(value = "付款方式")
    private Integer paymentType;

    @ApiModelProperty(value = "付款数据")
    private String paymentDate;
}
