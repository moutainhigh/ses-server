package com.redescooter.ses.web.ros.vo.factory;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.redescooter.ses.api.common.annotation.MaximumLength;
import com.redescooter.ses.api.common.annotation.MinimumLength;
import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.annotation.Regexp;
import com.redescooter.ses.api.common.constant.RegexpConstant;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.web.ros.exception.ValidationExceptionCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@ApiModel(value = "代工厂创建入参", description = "代工厂创建")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@Builder
@EqualsAndHashCode(callSuper = false)
public class FactorySaveEnter extends GeneralEnter {

    /**
     * 代工厂名称
     */
    @ApiModelProperty(value = "代工厂名称")
    //@Regexp(value = RegexpConstant.name,code = ValidationExceptionCode.NAME_IS_ILLEGAL,message = "名字非法")
    @NotNull(code = ValidationExceptionCode.FACTORY_NAME_IS_EMPTY, message = "代工厂名称为空")
    private String factoryName;

    /**
     * 代工厂地址
     */
    @ApiModelProperty(value = "代工厂地址")
    @NotNull(code = ValidationExceptionCode.ADDRESS_INFO_IS_EMPTY, message = "地址不能为空")
    //@Regexp(value = RegexpConstant.specialCharacters,code = ValidationExceptionCode.FACTORY_ADDRESS_ILLEGAL_CHARACTER,message = "代工厂地址存在非法字符")
    private String factoryAddress;

    /**
     * 代工厂国家
     */
    @ApiModelProperty(value = "代工厂国家，与手机号归属国家对应")
    @NotNull(code = ValidationExceptionCode.ADDRESS_COUNTRY_IS_EMPTY, message = "地址对应国家不能为空")
    private String factoryCountry;

    /**
     * 代工厂经度
     */
    @ApiModelProperty(value = "代工厂经度")
    private String factoryLongitude;

    /**
     * 代工厂纬度
     */
    @ApiModelProperty(value = "代工厂纬度")
    private String factoryLatitude;

    @ApiModelProperty(value = "placeId")
    private String placeId;
    /**
     * 代工厂标签
     */
    @ApiModelProperty(value = "代工厂标签")
    @NotNull(code = ValidationExceptionCode.FACTORY_TAG_IS_EMPTY, message = "代工厂标签不能为空")
    @Regexp(value = RegexpConstant.specialCharacters,code = ValidationExceptionCode.FACTORY_TAG_ILLEGAL_CHARACTER,message = "代工厂标签存在非法字符")
    private String factoryTag;

    /**
     * 代工厂备注
     */
    @ApiModelProperty(value = "代工厂备注")
    @Regexp(value = RegexpConstant.specialCharacters,code = ValidationExceptionCode.REMARK_ILLEGAL_CHARACTER,message = "代工厂备注存在非法字符")
    private String factoryMemo;

    /**
     * 联系人名字
     */
    @ApiModelProperty(value = "联系人名字")
    @Regexp(value = RegexpConstant.name,code = ValidationExceptionCode.NAME_IS_ILLEGAL,message = "名字非法")
    private String contactFirstName;

    /**
     * 联系人姓氏
     */
    @ApiModelProperty(value = "联系人姓氏")
    @Regexp(value = RegexpConstant.name,code = ValidationExceptionCode.NAME_IS_ILLEGAL,message = "名字非法")
    private String contactLastName;

    /**
     * 联系人全名
     */
    @ApiModelProperty(value = "联系人全名")
    @NotNull(code = ValidationExceptionCode.CONTACT_FULLNAME_IS_EMPTY, message = "联系人全名不能为空")
    @MinimumLength(code = ValidationExceptionCode.CHARACTER_IS_TOO_SHORT, message = "名字字符长度为2-20字符")
    @MaximumLength(code = ValidationExceptionCode.CHARACTER_IS_TOO_LONG, message = "名字符长度为2-20字符")
    @Regexp(value = RegexpConstant.name,code = ValidationExceptionCode.NAME_IS_ILLEGAL,message = "名字非法")
    private String contactFullName;

    /**
     * 联系人邮箱
     */
    @ApiModelProperty(value = "联系人邮箱")
    @NotNull(code = ValidationExceptionCode.CONTACT_EMAIL_IS_EMPTY, message = "联系人邮箱不能为空")
    @MinimumLength(code = ValidationExceptionCode.CHARACTER_IS_TOO_SHORT, message = "名字字符长度为2-20字符")
    @MaximumLength(code = ValidationExceptionCode.CHARACTER_IS_TOO_LONG, message = "名字符长度为2-20字符")
    @Regexp(value = RegexpConstant.email,code = ValidationExceptionCode.EMAIL_CHAR_IS_ILLEGAL,message = "邮箱非法")
    private String contactEmail;
    /**
     * 国家编码，如手机号 中国 +86
     */
    @ApiModelProperty(value = "国家编码，如手机号 中国 +86")
    @NotNull(code = ValidationExceptionCode.COUNTRY_CODE_IS_EMPTY, message = "国家手机号编码不能为空")
    private String contactPhoneCountryCode;

    /**
     * 联系人手机号
     */
    @ApiModelProperty(value = "联系人手机号")
    @NotNull(code = ValidationExceptionCode.CONTACT_PHONE_IS_EMPTY, message = "联系人手机号不能为空")
    @MinimumLength(code = ValidationExceptionCode.CHARACTER_IS_TOO_SHORT, message = "名字字符长度为2-20字符")
    @MaximumLength(code = ValidationExceptionCode.CHARACTER_IS_TOO_LONG, message = "名字符长度为2-20字符")
    @Regexp(value = RegexpConstant.number,code = ValidationExceptionCode.TELEPHONE_IS_NUMBER,message = "号码必须为数字0~9")
    private String contactPhone;

    /**
     * 付款周期
     */
    @ApiModelProperty(value = "付款周期")
    @NotNull(code = ValidationExceptionCode.PAYMENT_CYCLE_IS_EMPTY, message = "付款周期不能为空")
    private Integer paymentCycle;

    /**
     * 合作开始时间
     */
    @ApiModelProperty(value = "合作开始时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Date cooperationTimeStart;

    /**
     * 合作结束时间
     */
    @ApiModelProperty(value = "合作结束时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Date cooperationTimeEnd;

    /**
     * 营业执照编号
     */
    @ApiModelProperty(value = "营业执照编号")
    @NotNull(code = ValidationExceptionCode.BUSINESS_NUMBER_IS_EMPTY, message = "营业执照编号不能为空")
    @MinimumLength(code = ValidationExceptionCode.CHARACTER_IS_TOO_SHORT, message = "名字字符长度为2-20字符")
    @MaximumLength(code = ValidationExceptionCode.CHARACTER_IS_TOO_LONG, message = "名字符长度为2-20字符")
    @Regexp(value = RegexpConstant.specialCharacters,code = ValidationExceptionCode.BUSINESS_LICENSE_NUM_ILLEGAL_CHARACTER,message = "营业执照编号存在非法字符")
    private String businessNumber;

    /**
     * 营业执照附件
     */
    @ApiModelProperty(value = "营业执照附件")
    @NotNull(code = ValidationExceptionCode.BUSINESS_LICENSE_ANNEX_IS_EMPTY, message = "营业执照附件不能为空")
    private String businessLicenseAnnex;

    /**
     * 合同编号
     */
    @ApiModelProperty(value = "合同编号")
    @Regexp(value = RegexpConstant.specialCharacters,code = ValidationExceptionCode.CONTRACT_NUMBER_ILLEGAL_CHARACTER,message = "备注存在非法字符")
    private String contractNumber;

    /**
     * 合同附件
     */
    @ApiModelProperty(value = "合同附件")
    @NotNull(code = ValidationExceptionCode.CONTRACT_ANNEX_IS_EMPTY, message = "合同附件不能为空")
    private String contractAnnex;


}
