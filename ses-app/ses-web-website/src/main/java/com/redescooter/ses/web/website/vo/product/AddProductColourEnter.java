package com.redescooter.ses.web.website.vo.product;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author jerry
 * @Date 2021/1/6 1:02 上午
 * @Description 产品颜色关系新增入参
 **/

@ApiModel(value = "产品颜色关系新增入参", description = "产品颜色关系新增入参")
@Data
@EqualsAndHashCode(callSuper = true)
public class AddProductColourEnter extends GeneralEnter {
    /**
     * 主键
     */
    @ApiModelProperty(value="主键")
    private Long productId;

    /**
     * 主键
     */
    @ApiModelProperty(value="产品颜色")
    private Long colourId;

}
