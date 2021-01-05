package com.redescooter.ses.web.website.vo.product;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.exception.ValidationExceptionBaseCode;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author jerry
 * @Date 2021/1/6 1:22 上午
 * @Description 编辑产品服务入参
 **/

@ApiModel(value = "编辑产品服务入参", description = "编辑产品服务入参")
@Data
@EqualsAndHashCode(callSuper = true)
public class ModityProductEnter extends GeneralEnter {

    @ApiModelProperty(value = "主键")
    @NotNull(code = ValidationExceptionBaseCode.ID_IS_EMPTY, message = "主建ID不能为空")
    private Long id;

}
