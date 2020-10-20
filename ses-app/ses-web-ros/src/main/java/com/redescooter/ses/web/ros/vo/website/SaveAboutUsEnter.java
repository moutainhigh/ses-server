package com.redescooter.ses.web.ros.vo.website;

import com.redescooter.ses.api.common.annotation.MaximumLength;
import com.redescooter.ses.api.common.annotation.MinimumLength;
import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.exception.ValidationExceptionBaseCode;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.web.ros.exception.ValidationExceptionCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @ClassName:saveInquiryEnter
 * @description: saveInquiryEnter
 * @author: Alex
 * @Version：1.3
 * @create: 4040/03/05 14:48
 */
@ApiModel(value = "contact us", description = "contact us")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class SaveAboutUsEnter extends GeneralEnter {

    @ApiModelProperty(value = "email")
    @NotNull(code = ValidationExceptionBaseCode.EMAIL_IS_EMPTY, message = "邮箱不能为空")
    private String email;

    @ApiModelProperty(value = "Country")
    private String customerCountry;

    @ApiModelProperty(value = "Country Id")
    private Long countryId;

    @ApiModelProperty(value = "city")
    private String city;

    @ApiModelProperty(value = "distrust")
    private String distrust;

    @ApiModelProperty(value = "first name")
    @NotNull(code = ValidationExceptionCode.FIRST_NAME_IS_EMPTY, message = "姓名为空")
    @MinimumLength(value = "2", code = ValidationExceptionCode.NAME_IS_ILLEGAL, message = "长度过短")
    @MaximumLength(value = "40", code = ValidationExceptionCode.NAME_IS_ILLEGAL, message = "长度过长")
    private String firstName;

    @ApiModelProperty(value = "last name")
    @NotNull(code = ValidationExceptionCode.LAST_NAME_IS_EMPTY, message = "姓名为空")
    @MinimumLength(value = "2", code = ValidationExceptionCode.NAME_IS_ILLEGAL, message = "长度过短")
    @MaximumLength(value = "40", code = ValidationExceptionCode.NAME_IS_ILLEGAL, message = "长度过长")
    private String lastName;

    @ApiModelProperty(value = "countryCode",notes = "Such as mobile phone number China + 86")
    private String countryCode;

    @ApiModelProperty(value = "telephone")
    @NotNull(code = ValidationExceptionCode.CONTACT_PHONE_IS_EMPTY, message = "电话不能为空")
    private String telephone;

    @ApiModelProperty(value = "remark")
    @MaximumLength(value = "1000", code = ValidationExceptionCode.REMARK_ILLEGAL, message = "备注非法")
    private String remark;

    @ApiModelProperty("address")
    private String address;

    @ApiModelProperty(value = "Are formal customers allowed to leave messages in contact with us")
    private Boolean whetherConstantUs = Boolean.FALSE;
}
