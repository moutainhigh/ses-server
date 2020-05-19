package com.redescooter.ses.web.ros.vo.inquiry;

import com.redescooter.ses.api.common.annotation.NotNull;
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
 * @create: 2020/03/05 14:48
 */
@ApiModel(value = "编辑询价单", description = "编辑询价单")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class NewSaveInquiryEnter extends GeneralEnter {

    @ApiModelProperty(value = "邮箱")
    @NotNull(code = com.redescooter.ses.api.common.exception.ValidationExceptionCode.EMAIL_IS_EMPTY, message = "邮箱不能为空")
    private String email;

    @NotNull(code = ValidationExceptionCode.REGION_IS_EMPTY, message = "区域为空")
    private String distrust;

    @ApiModelProperty(value = "客户名字")
    @NotNull(code = ValidationExceptionCode.FIRST_NAME_IS_EMPTY,message = "姓名为空")
    private String firstName;

    @ApiModelProperty(value = "客户姓氏")
    @NotNull(code = ValidationExceptionCode.LAST_NAME_IS_EMPTY,message = "姓名为空")
    private String lastName;

    @ApiModelProperty(value = "国家编码，如手机号 中国 +86")
    @NotNull(code = ValidationExceptionCode.COUNTRY_CODE_IS_EMPTY, message = "邮箱不能为空")
    private String countryCode;

    @ApiModelProperty(value = "电话")
    @NotNull(code = ValidationExceptionCode.CONTACT_PHONE_IS_EMPTY, message = "邮箱不能为空")
    private String telephone;

    @ApiModelProperty(value = "备注")
    private String remark;
}
