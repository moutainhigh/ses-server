package com.redescooter.ses.web.website.vo.product;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.exception.ValidationExceptionBaseCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author jerry
 * @Date 2021/1/5 10:16 下午
 * @Description 产品颜色编辑入参
 **/
@ApiModel(value = "产品颜色编辑入参", description = "产品颜色编辑入参")
@Data
@EqualsAndHashCode(callSuper = true)
public class ModityColourEnter extends AddColourEnter {

    @ApiModelProperty(value = "主键")
    @NotNull(code = ValidationExceptionBaseCode.ID_IS_EMPTY, message = "主建ID不能为空")
    private Long id;

}
