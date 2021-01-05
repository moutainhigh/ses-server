package com.redescooter.ses.web.website.vo.product;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.exception.ValidationExceptionBaseCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author jerry
 * @Date 2021/1/6 1:03 上午
 * @Description 产品颜色关系编辑入参
 **/
@ApiModel(value = "产品颜色关系编辑入参", description = "产品颜色关系编辑入参")
@Data
@EqualsAndHashCode(callSuper = true)
public class ModityProductColourEnter extends AddProductColourEnter{
    /**
     * 主键
     */
    @NotNull(code = ValidationExceptionBaseCode.ID_IS_EMPTY, message = "产品颜色关系主建ID不能为空")
    @ApiModelProperty(value = "主键")
    private Long id;
}
