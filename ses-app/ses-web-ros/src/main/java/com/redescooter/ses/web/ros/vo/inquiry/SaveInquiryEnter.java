package com.redescooter.ses.web.ros.vo.inquiry;

import com.redescooter.ses.api.common.annotation.MaximumLength;
import com.redescooter.ses.api.common.annotation.MinimumLength;
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
public class SaveInquiryEnter extends GeneralEnter {

    @ApiModelProperty(value = "邮箱")
    @NotNull(code = com.redescooter.ses.api.common.exception.ValidationExceptionCode.EMAIL_IS_EMPTY, message = "邮箱不能为空")
//    @MinimumLength(value = "2", code = ValidationExceptionCode.EMAIL_CHAR_IS_ILLEGAL, message = "长度过短")
//    @MaximumLength(value = "50", code = ValidationExceptionCode.EMAIL_CHAR_IS_ILLEGAL, message = "长度过长")
    private String email;

    @ApiModelProperty(value = "国家")
    private String customerCountry;

    @ApiModelProperty(value = "国家Id")
    private Long countryId;

    @ApiModelProperty(value = "城市")
    private String city;

    @ApiModelProperty(value = "区域")
    private String distrust;

    @ApiModelProperty(value = "客户名字")
    @NotNull(code = ValidationExceptionCode.FIRST_NAME_IS_EMPTY, message = "姓名为空")
    @MinimumLength(value = "2", code = ValidationExceptionCode.NAME_IS_ILLEGAL, message = "长度过短")
    @MaximumLength(value = "20", code = ValidationExceptionCode.NAME_IS_ILLEGAL, message = "长度过长")
    private String firstName;

    @ApiModelProperty(value = "客户姓氏")
    @NotNull(code = ValidationExceptionCode.LAST_NAME_IS_EMPTY, message = "姓名为空")
    @MinimumLength(value = "2", code = ValidationExceptionCode.NAME_IS_ILLEGAL, message = "长度过短")
    @MaximumLength(value = "20", code = ValidationExceptionCode.NAME_IS_ILLEGAL, message = "长度过长")
    private String lastName;

    @ApiModelProperty(value = "国家编码，如手机号 中国 +86")
    private String countryCode;

    @ApiModelProperty(value = "电话")
    @NotNull(code = ValidationExceptionCode.CONTACT_PHONE_IS_EMPTY, message = "电话不能为空")
    //@MaximumLength(value = "10", code = ValidationExceptionCode.CHARACTER_IS_TOO_LONG, message = "长度过长")
    private String telephone;

    @ApiModelProperty(value = "备注")
    @MaximumLength(value = "1000", code = ValidationExceptionCode.REMARK_ILLEGAL, message = "备注非法")
    private String remark;

    @ApiModelProperty("地址")
    private String address;

    @ApiModelProperty("是否允许正式客户在联系我们中留言")
    private Boolean whetherConstantUs = Boolean.FALSE;
}
