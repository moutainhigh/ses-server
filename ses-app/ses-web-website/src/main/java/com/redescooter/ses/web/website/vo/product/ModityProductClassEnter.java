package com.redescooter.ses.web.website.vo.product;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.exception.ValidationExceptionBaseCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author jerry
 * @Date 2021/1/5 3:53 下午
 * @Description 产品种类新增入参
 **/

@ApiModel(value = "产品种类新增入参", description = "产品种类新增入参")
@Data
@EqualsAndHashCode(callSuper = true)
public class ModityProductClassEnter extends AddProductClassEnter {

    /**
     * 主键
     */
    @NotNull(code = ValidationExceptionBaseCode.ID_IS_EMPTY, message = "产品种类主建ID不能为空")
    @ApiModelProperty(value = "主键")
    private Long id;

}
