package com.redescooter.ses.api.mobile.b.vo;

import com.redescooter.ses.api.common.annotation.MaximumLength;
import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.annotation.Regexp;
import com.redescooter.ses.api.common.constant.RegexpConstant;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.mobile.b.exception.ValidationExceptionCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @ClassName:RefuseEnter
 * @description: RefuseEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2019/12/29 15:24
 */
@ApiModel(value = "拒绝入参", description = "拒绝入参")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
public class RefuseEnter extends GeneralEnter {
    @ApiModelProperty(value = "id")
    @NotNull
    private Long id;

    @ApiModelProperty(value = "拒绝原因")
    @NotNull(code = ValidationExceptionCode.REASON_IS_EMPTY, message = "拒绝原因为空")
    @MaximumLength(value = "300", code = ValidationExceptionCode.REASON_CHARACTER_IS_TOO_LONG, message = "原因字符过长")
    @Regexp(value = RegexpConstant.specialCharacters, code = ValidationExceptionCode.REASON_IS_ILLEGAL, message = "原因非法")
    private String reason;
}
