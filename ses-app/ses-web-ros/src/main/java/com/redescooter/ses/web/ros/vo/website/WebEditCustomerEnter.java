package com.redescooter.ses.web.ros.vo.website;

import com.redescooter.ses.api.common.annotation.MaximumLength;
import com.redescooter.ses.api.common.annotation.MinimumLength;
import com.redescooter.ses.api.common.annotation.NotEmpty;
import com.redescooter.ses.api.common.annotation.NotNull;
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
    @MaximumLength(value ="60", code = ValidationExceptionCode.CHARACTER_IS_TOO_LONG,message = "字符过长")
    @MinimumLength(value = "2",code = ValidationExceptionCode.CHARACTER_IS_TOO_SHORT,message = "字符过短")
    private String firstName;

    @ApiModelProperty(value = "姓名")
    @MaximumLength(value ="60", code = ValidationExceptionCode.CHARACTER_IS_TOO_LONG,message = "字符过长")
    @MinimumLength(value = "2",code = ValidationExceptionCode.CHARACTER_IS_TOO_SHORT,message = "字符过短")
    private String lastName;

    @ApiModelProperty(value = "邮箱")
    @NotNull(code = com.redescooter.ses.api.common.exception.ValidationExceptionCode.EMAIL_IS_EMPTY,message = "邮箱为空")
    @MinimumLength(value = "2",code = ValidationExceptionCode.CHARACTER_IS_TOO_SHORT,message = "字符过短")
    private String email;

    @ApiModelProperty(value = "电话")
    @NotNull(code = ValidationExceptionCode.CONTACT_PHONE_IS_EMPTY,message = "电话为空")
    private String telephone;

    @ApiModelProperty(value = "地址")
    @MaximumLength(value ="120", code = ValidationExceptionCode.CHARACTER_IS_TOO_LONG,message = "字符过长")
    @MinimumLength(value = "2",code = ValidationExceptionCode.CHARACTER_IS_TOO_SHORT,message = "字符过短")
    @NotNull(code = ValidationExceptionCode.ADDRESS_INFO_IS_EMPTY,message = "地址为空")
    private String address;

}
