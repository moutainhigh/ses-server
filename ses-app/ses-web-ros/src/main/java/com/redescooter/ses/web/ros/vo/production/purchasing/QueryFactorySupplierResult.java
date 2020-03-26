package com.redescooter.ses.web.ros.vo.production.purchasing;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ClassName:PurchasingFactorySupplierResult
 * @description: PurchasingFactorySupplierResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/25 19:20
 */
@ApiModel(value = "查询采购单代工厂供应商", description = "查询采购单代工厂供应商")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class QueryFactorySupplierResult extends GeneralResult {
    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "工厂Id")
    private Long factoryId;

    @ApiModelProperty(value = "代工厂名字")
    private String factoryName;

    @ApiModelProperty(value = "供应商列表")
    private List<PurchasSupplierResult> purchasSupplierResultList;
}
