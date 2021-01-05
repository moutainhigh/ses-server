package com.redescooter.ses.web.website.vo.product;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @Author jerry
 * @Date 2021/1/5 3:53 下午
 * @Description 产品种类新增入参
 **/

@ApiModel(value = "产品种类新增入参", description = "产品种类新增入参")
@Data
@EqualsAndHashCode(callSuper = true)
public class modityProductClassEnter extends addProductClassEnter {

    /**
     * 主键 主键
     */
    @ApiModelProperty(value = "主键")
    private Long id;

}
