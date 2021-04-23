package com.redescooter.ses.api.common.vo.base;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.exception.ValidationExceptionBaseCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.*;

@ApiModel(value = "Primary key parameters", description = "Primary key parameters")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@Builder
@EqualsAndHashCode(callSuper = false)
public class UpdateInfoResult extends GeneralEnter {

    @ApiModelProperty(value = "specificatTypeId",required = true)
    @NotNull(code = ValidationExceptionBaseCode.ID_IS_EMPTY, message = "specificatTypeId不能为空")
    private Long specificatTypeId;

    @ApiModelProperty(value = "colorId",required = true)
    @NotNull(code = ValidationExceptionBaseCode.ID_IS_EMPTY, message = "colorId不能为空")
    private Long colorId;

    @ApiModelProperty(value = "productQty",required = true)
    private Integer productQty;


    @ApiModelProperty(value = "客户咨询管理主键id",required = true)
    @NotNull(code = ValidationExceptionBaseCode.ID_IS_EMPTY, message = "客户咨询管理主键id不能为空")
    private Long id;


}
