package com.redescooter.ses.api.hub.vo;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.hub.exception.ValidationExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import io.swagger.annotations.*;

/**
 * @ClassName:EditUserProfileResult
 * @description: EditUserProfileResult 暂时只修改名字
 * @author: Alex
 * @Version：1.3
 * @create: 2020/01/18 15:41
 */
@ApiModel(value = "修改个人信息", description = "修改个人信息")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@EqualsAndHashCode(callSuper = true)
public class EditUserProfileEnter extends GeneralResult {

    @ApiModelProperty(value = "租户Id", required = true)
    @NotNull(code = ValidationExceptionCode.ID_IS_EMPTY, message = "Id 不为空")
    private Long inputTenantId;

    @ApiModelProperty(value = "租户Id")
    @NotNull(code = ValidationExceptionCode.EMAIL_IS_EMPTY, message = "邮箱不为空")
    private String email;

    // 上面两个参数是为了 进行唯一性过滤的

    @ApiModelProperty(value = "姓名")
    private String firstName;

    @ApiModelProperty(value = "姓名")
    private String lastName;

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty(value = "电话号")
    private String telNumber1;
}
