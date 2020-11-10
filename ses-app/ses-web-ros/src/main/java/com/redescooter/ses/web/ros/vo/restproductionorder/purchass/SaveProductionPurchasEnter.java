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
@ApiModel(value = "", description = "")
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

    @ApiModelProperty(value = "")
    private String consigneeUserName;

    @ApiModelProperty(value = "")
    private String consigneeCountryCode;

    @ApiModelProperty(value = "")
    private String consigneeUserTelephone;

    @ApiModelProperty(value = "")
    private String consigneeAddress;

    @ApiModelProperty(value = "")
    private String remark;

    @ApiModelProperty(value = "")
    private String productDate;

    @ApiModelProperty(value = "")
    private Integer paymentType;

    @ApiModelProperty(value = "")
    private String paymentDate;
}
