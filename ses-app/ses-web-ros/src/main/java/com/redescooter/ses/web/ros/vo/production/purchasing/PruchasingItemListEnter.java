package com.redescooter.ses.web.ros.vo.production.purchasing;

import com.redescooter.ses.api.common.vo.base.PageEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @ClassName:PruchasingItemListEnter
 * @description: PruchasingItemListEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/19 10:27
 */
@ApiModel(value = "采购商品条目列表", description = "采购商品条目列表")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class PruchasingItemListEnter extends PageEnter {

    @ApiModelProperty(value = "类型")
    private String productType;

    @ApiModelProperty(value = "供应商")
    private Long supplierId;

    @ApiModelProperty(value = "关键字")
    private String keyword;
}