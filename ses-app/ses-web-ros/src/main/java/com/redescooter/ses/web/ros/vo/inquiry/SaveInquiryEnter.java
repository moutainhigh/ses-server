package com.redescooter.ses.web.ros.vo.inquiry;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.annotation.Regexp;
import com.redescooter.ses.api.common.constant.RegexpConstant;
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

    @ApiModelProperty(value = "国家Id")
    @NotNull(code = ValidationExceptionCode.COUNTRY_CITY_MSG_IS_EMPTY, message = "国家城市代码为空")
    private Long countryId;

    @ApiModelProperty(value = "城市")
    @NotNull(code = ValidationExceptionCode.COUNTRY_CITY_MSG_IS_EMPTY, message = "国家城市代码为空")
    private Long cityId;

    @ApiModelProperty(value = "区域")
    private Long distrustId;

    @ApiModelProperty(value = "邮箱")
    @NotNull(code = com.redescooter.ses.api.common.exception.ValidationExceptionCode.EMAIL_IS_EMPTY, message = "邮箱不能为空")
    @Regexp(value = RegexpConstant.email,code = ValidationExceptionCode.EMAIL_CHAR_IS_ILLEGAL,message = "邮箱非法")
    private String email;

    @ApiModelProperty(value = "客户名字")
    @Regexp(value = RegexpConstant.name,code = ValidationExceptionCode.NAME_IS_ILLEGAL,message = "名字非法")
    private String customerFirstName;

    @ApiModelProperty(value = "客户姓氏")
    @Regexp(value = RegexpConstant.name,code = ValidationExceptionCode.NAME_IS_ILLEGAL,message = "名字非法")
    private String customerLastName;

    @ApiModelProperty(value = "企业名称")
    @Regexp(value = RegexpConstant.name,code = ValidationExceptionCode.NAME_IS_ILLEGAL,message = "名字非法")
    private String companyName;

    @ApiModelProperty(value = "客户类型,ENTERPRISE-公司-1, PERSONAL-个人-2")
    @NotNull(code = ValidationExceptionCode.CUSTOMER_TYPE_IS_EMPTY, message = "客户类型为空")
    private String customerType;

    @ApiModelProperty(value = "客户行业类型,RESTAURANT-餐厅-1, EXPRESS_DELIVERY-快递-2")
    @NotNull(code = ValidationExceptionCode.INDUSTY_TYPE_IS_EMPTY, message = "行业类型为空")
    private String industryType;

    @ApiModelProperty(value = "联系人名字")
    @Regexp(value = RegexpConstant.name,code = ValidationExceptionCode.NAME_IS_ILLEGAL,message = "名字非法")
    private String contactFirstName;

    @ApiModelProperty(value = "联系人姓氏")
    @Regexp(value = RegexpConstant.name,code = ValidationExceptionCode.NAME_IS_ILLEGAL,message = "名字非法")
    private String contactLastName;

    @ApiModelProperty(value = "国家编码，如手机号 中国 +86")
    @NotNull(code = ValidationExceptionCode.COUNTRY_CODE_IS_EMPTY, message = "国家手机号编码不能为空")
    private String countryCode;

    @ApiModelProperty(value = "电话")
    @NotNull(code = ValidationExceptionCode.CONTACT_PHONE_IS_EMPTY, message = "联系人手机号不能为空")
    @Regexp(value = RegexpConstant.number,code = ValidationExceptionCode.TELEPHONE_IS_NUMBER,message = "号码必须为数字0~9")
    private String telephone;

    @ApiModelProperty(value = "车辆数量")
    @NotNull(code = ValidationExceptionCode.QTY_IS_EMPTY, message = "车辆数量为空")
    @Regexp(value = RegexpConstant.twoNumber,code = ValidationExceptionCode.QTY_IS_NUMBER,message = "数量必须为数字")
    private Integer scooterQuantity;

    @ApiModelProperty(value = "备注")
    @Regexp(value = RegexpConstant.specialCharacters,code = ValidationExceptionCode.REMARK_ILLEGAL_CHARACTER,message = "备注存在非法字符")
    private String remark;
}
