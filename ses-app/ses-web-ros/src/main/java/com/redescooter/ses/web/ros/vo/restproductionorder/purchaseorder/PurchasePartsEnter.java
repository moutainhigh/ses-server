package com.redescooter.ses.web.ros.vo.restproductionorder.purchaseorder;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @ClassNamePurchasePartsEnter
 * @Description
 * @Author Aleks
 * @Date2020/10/23 19:41
 * @Version V1.0
 **/
@Data
@ApiModel(value = "采购单部件产品新增编辑入参",description = "采购单部件产品新增编辑入参")
public class PurchasePartsEnter extends GeneralEnter {

    @ApiModelProperty(value = "主键id")
    private Long id;

    @ApiModelProperty(value = "部件id")
    private Long partsId;

    @ApiModelProperty(value = "部件名称(英文名称)")
    private String partsName;

    @ApiModelProperty(value = "部件编号")
    private String partsNo;

    @ApiModelProperty(value = "部件类型，1：Parts，2：Accessory，3：Battery，4：Scooter，5：Combination")
    private Integer partsType;

    @ApiModelProperty(value = "数量")
    private Integer qty;

    @ApiModelProperty(value = "采购单价")
    private BigDecimal unitPrice;


}
