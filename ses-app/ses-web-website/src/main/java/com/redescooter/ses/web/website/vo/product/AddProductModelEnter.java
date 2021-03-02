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
 * @Date 2021/1/5 6:00 下午
 * @Description 产品类型新增入参
 **/
@ApiModel(value = "产品型号新增入参", description = "产品型号新增入参")
@Data
@EqualsAndHashCode(callSuper = true)
public class AddProductModelEnter extends GeneralEnter {

    /**
     * 产品种类主建
     */
    @NotNull(code = ValidationExceptionBaseCode.ID_IS_EMPTY, message = "产品种类主建ID不能为空")
    @ApiModelProperty(value = "产品种类主建")
    private Long productClassId;

    /**
     * 产品类型单项名称
     */
    @ApiModelProperty(value = "产品类型单项名称")
    private String productModelName;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

}
