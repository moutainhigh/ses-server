package com.redescooter.ses.mobile.rps.vo.productwaitinwh;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.vo.base.PageEnter;
import com.redescooter.ses.mobile.rps.exception.ValidationExceptionCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @ClassNameProWaitInWHIdEnter
 * @Description
 * @Author kyle
 * @Date2020/4/26 15:54
 * @Version V1.0
 **/
@ApiModel(value = "整车部件入库项操作入参", description = "整车部件入库项操作入参")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class ProductDetailEnter extends PageEnter {

    @ApiModelProperty(value = "主单id")
    @NotNull(code = com.redescooter.ses.api.common.exception.ValidationExceptionCode.ID_IS_EMPTY,message = "Id为空")
    private Long id;

    @ApiModelProperty(value = "单据类型")
    @NotNull(code = ValidationExceptionCode.SOURCE_TYPE_IS_EMPTY,message = "单据类型为空")
    private String sourceType;
}
