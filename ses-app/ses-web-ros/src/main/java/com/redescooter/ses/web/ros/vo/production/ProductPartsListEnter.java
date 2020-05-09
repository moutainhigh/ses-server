package com.redescooter.ses.web.ros.vo.production;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import io.swagger.annotations.*;

/**
 * @ClassName:ProductPartsListEnter
 * @description: ProductPartsListEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/19 15:46
 */
@ApiModel(value = "部件列表", description = "部件列表")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class ProductPartsListEnter extends GeneralEnter {

    @ApiModelProperty(value = "供应商Id 根据调用场景，决定是否需要传")
    private String productType;

    @ApiModelProperty(value = "供应商Id 根据调用场景，决定是否需要传")
    private String supplierId;

    @ApiModelProperty(value = "关键字")
    private String keyword;
}
