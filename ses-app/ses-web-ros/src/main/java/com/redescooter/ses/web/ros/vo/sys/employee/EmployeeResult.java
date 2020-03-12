package com.redescooter.ses.web.ros.vo.sys.employee;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
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
 * @ClassName:EmployeeResult
 * @description: EmployeeResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/06 09:51
 */
@ApiModel(value = "员工列表出参", description = "员工列表出参")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class EmployeeResult extends GeneralResult {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "公司名称")
    private String companyName;

    @ApiModelProperty(value = "员工名字")
    private String employeeFirstName;

    @ApiModelProperty(value = "员工名字")
    private String employeeLastName;

    @ApiModelProperty(value = "国家代码")
    private String countryCode;

    @ApiModelProperty(value = "电话")
    private String telephone;

    @ApiModelProperty(value = "邮件")
    private String email;

    @ApiModelProperty(value = "部门Id")
    private Long deptId;

    @ApiModelProperty(value = "部门名称")
    private String deptName;

    @ApiModelProperty(value = "职位Id")
    private Long positionId;

    @ApiModelProperty(value = "职位名字")
    private String positionName;

    @ApiModelProperty(value = "加入时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Date entryDate;

    @ApiModelProperty(value = "生日")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Date birthday;

    @ApiModelProperty(value = "地址国家代码")
    private String addressCountryCode;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "证件类型")
    private String certificateType;

    @ApiModelProperty(value = "证件正面")
    private String positivePicture;

    @ApiModelProperty(value = "证件反面")
    private String negativePicture;

    @ApiModelProperty(value = "办公区域")
    private String addressBureau;

}
