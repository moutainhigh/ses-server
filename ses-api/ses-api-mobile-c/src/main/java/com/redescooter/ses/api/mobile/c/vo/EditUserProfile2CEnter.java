package com.redescooter.ses.api.mobile.c.vo;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.exception.ValidationExceptionCode;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @ClassName:EditUserProfileEnter
 * @description: EditUserProfileEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/01/18 15:45
 */
@ApiModel(value = "修改个人信息", description = "修改个人信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class EditUserProfile2CEnter extends GeneralEnter {

    @ApiModelProperty(value = "租户Id", required = true)
    @NotNull(code = ValidationExceptionCode.ID_IS_EMPTY, message = "Id 不为空")
    private Long inputTenantId;

    @ApiModelProperty(value = "租户Id")
    @NotNull(code = ValidationExceptionCode.EMAIL_IS_EMPTY, message = "邮箱不为空")
    private String email;

    @ApiModelProperty(value = "名字")
    private String firstName;

    @ApiModelProperty(value = "名字")
    private String lastName;

}
