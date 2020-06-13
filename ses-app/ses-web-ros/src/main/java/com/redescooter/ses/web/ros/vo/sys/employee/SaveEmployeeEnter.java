package com.redescooter.ses.web.ros.vo.sys.employee;

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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @ClassName:SaveEmployeeEnter
 * @description: SaveEmployeeEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/06 16:05
 */
@ApiModel(value = "保存员工信息", description = "保存员工信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class SaveEmployeeEnter extends GeneralEnter {

    @ApiModelProperty(value = "id", required = false)
    private Long id;

    @ApiModelProperty(value = "公司Id", required = false)
    @NotNull(code = ValidationExceptionCode.COMPANY_IS_EMPTY, message = "公司为空")
    private Long companyId;

    @ApiModelProperty(value = "办公区域Id", required = true)
    @NotNull(code = ValidationExceptionCode.ADDRESS_BUREAU_IS_EMPTY, message = "办公区域为空")
    private Long addressBureauId;

    @ApiModelProperty(value = "部门Id", required = true)
    @NotNull(code = ValidationExceptionCode.DEPT_IS_EMPTY, message = "部门为空")
    private Long deptId;

    @ApiModelProperty(value = "职位id", required = true)
    @NotNull(code = ValidationExceptionCode.POSITION_IS_EMPTY, message = "职位为空")
    private Long positionId;

    @ApiModelProperty(value = "员工姓名", required = true)
    @NotNull(code = ValidationExceptionCode.FIRST_NAME_IS_EMPTY, message = "名为空")
    @Regexp(value = RegexpConstant.name,code = ValidationExceptionCode.NAME_IS_ILLEGAL,message = "名字非法")
    private String employeeFirstName;

    @ApiModelProperty(value = "员工姓名", required = true)
    @NotNull(code = ValidationExceptionCode.LAST_NAME_IS_EMPTY, message = "姓为空")
    @Regexp(value = RegexpConstant.name,code = ValidationExceptionCode.NAME_IS_ILLEGAL,message = "名字非法")
    private String employeeLastName;

    @ApiModelProperty(value = "电话国家代码", required = true)
    @NotNull(code = ValidationExceptionCode.COUNTRY_CODE_IS_EMPTY, message = "国家代码为空")
    private String telCountryCode;

    @ApiModelProperty(value = "电话", required = true)
    @NotNull(code = ValidationExceptionCode.CONTACT_PHONE_IS_EMPTY, message = "电话为空")
    private String telephone;

    @ApiModelProperty(value = "邮箱", required = true)

    @NotNull(code = com.redescooter.ses.api.common.exception.ValidationExceptionCode.EMAIL_IS_EMPTY, message = "邮箱为空")
    @Regexp(value = RegexpConstant.email,code = ValidationExceptionCode.EMAIL_CHAR_IS_ILLEGAL,message = "邮箱非法")
    @MinimumLength(code = ValidationExceptionCode.EMAIL_CHAR_IS_ILLEGAL, message = "地址字符长度不合法，2-20字符")
    @MaximumLength(code = ValidationExceptionCode.EMAIL_CHAR_IS_ILLEGAL, message = "地址字符长度不合法，2-20字符")
    private String email;

    @ApiModelProperty(value = "生日", required = true)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    @NotNull(code = ValidationExceptionCode.BIRTHDAY_IS_EMPTY, message = "生日为空")
    private Date birthday;

    @ApiModelProperty(value = "地址国家代码", required = true)
    @NotNull(code = ValidationExceptionCode.ADDRESS_COUNTRY_IS_EMPTY, message = "国家代码为空")
    private String addressCountryCode;

    @ApiModelProperty(value = "地址", required = true)
    @NotNull(code = ValidationExceptionCode.ADDRESS_INFO_IS_EMPTY, message = "地址为空")
    @MinimumLength(code = ValidationExceptionCode.ADDRESS_CHAR_IS_NOT_ILLEGAL, message = "地址字符长度不合法，2-200字符")
    @MaximumLength(code = ValidationExceptionCode.ADDRESS_CHAR_IS_NOT_ILLEGAL, message = "地址字符长度不合法，2-200字符")
    private String address;

    @ApiModelProperty(value = "证件类型", required = true)
    @NotNull(code = ValidationExceptionCode.CERTIFICATE_TYPE_IS_EMPTY, message = "证件类型为空")
    private String certificateType;

    @ApiModelProperty(value = "证件正面", required = true)
    @NotNull(code = ValidationExceptionCode.CERTIFICATE_POSITIVE_PICTURE_IS_EMPTY, message = "证件类型为空")
    private String positivePicture;

    @ApiModelProperty(value = "证反面", required = false)
    private String negativePicture;

    @ApiModelProperty(value = "头像", required = false)
    private String avatar;
}
