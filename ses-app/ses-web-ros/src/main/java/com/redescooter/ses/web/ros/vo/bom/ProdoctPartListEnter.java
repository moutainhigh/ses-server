package com.redescooter.ses.web.ros.vo.bom;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @ClassName:ProdoctPartListEnter
 * @description: ProdoctPartListEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/02/27 10:57
 */
@ApiModel(value = "产品部品列表入参", description = "产品部品列表入参")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class ProdoctPartListEnter extends GeneralEnter {
    @ApiModelProperty(value = "部品Id")
    private Long id;

    @ApiModelProperty(value = "数量")
    private Integer qty;
}
