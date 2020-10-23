package com.redescooter.ses.web.ros.vo.restproductionorder.purchaseorder;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @ClassNamePurchaseCombinEnter
 * @Description
 * @Author Aleks
 * @Date2020/10/23 19:41
 * @Version V1.0
 **/
@Data
public class PurchaseCombinEnter extends GeneralEnter {

    @ApiModelProperty(value = "主键id")
    private Long id;

    @ApiModelProperty(value = "组装件名称(英文名称)")
    private String combinName;

    @ApiModelProperty(value = "组装件id")
    private Long productionCombinBomId;

    @ApiModelProperty(value = "数量")
    private Integer qty;

    @ApiModelProperty(value = "采购单价")
    private BigDecimal unitPrice;


}
