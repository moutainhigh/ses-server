package com.redescooter.ses.web.ros.vo.website;

import com.redescooter.ses.api.common.annotation.MaximumLength;
import com.redescooter.ses.api.common.annotation.MinimumLength;
import com.redescooter.ses.api.common.annotation.NotEmpty;
import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.annotation.Regexp;
import com.redescooter.ses.api.common.constant.RegexpConstant;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.web.ros.exception.ValidationExceptionCode;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassNameEditCustomerEnter
 * @Description
 * @Author Aleks
 * @Date2020/5/26 15:28
 * @Version V1.0
 **/
@Data
public class WebEditCustomerEnter extends GeneralEnter {

    @ApiModelProperty(value = "姓名")
    @NotNull(code = ValidationExceptionCode.FIRST_NAME_IS_EMPTY,message = "名字为空")
    @MaximumLength(value ="60", code = ValidationExceptionCode.NAME_IS_ILLEGAL,message = "名字不合法")
    @MinimumLength(value = "2",code = ValidationExceptionCode.NAME_IS_ILLEGAL,message = "名字不合法")
    @Regexp(value = RegexpConstant.specialCharacters,code = ValidationExceptionCode.NAME_IS_ILLEGAL,message = "名字不合法")
    private String firstName;

    @ApiModelProperty(value = "姓名")
    @NotNull(code = ValidationExceptionCode.LAST_NAME_IS_EMPTY,message = "名字为空")
    @MaximumLength(value ="60", code = ValidationExceptionCode.NAME_IS_ILLEGAL,message = "名字不合法")
    @MinimumLength(value = "2",code = ValidationExceptionCode.NAME_IS_ILLEGAL,message = "名字不合法")
    @Regexp(value = RegexpConstant.specialCharacters,code = ValidationExceptionCode.NAME_IS_ILLEGAL,message = "名字不合法")
    private String lastName;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "电话")
    private String telephone;

    @ApiModelProperty(value = "地址")
    private String address;

}
