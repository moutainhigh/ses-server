package com.redescooter.ses.web.ros.vo.organization.employee;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "公司Id")
    private Long companyId;

    @ApiModelProperty(value = "办公区域Id")
    private Long addressBureauId;

    @ApiModelProperty(value = "部门Id")
    private Long deptId;

    @ApiModelProperty(value = "职位id")
    private Long positionId;

    @ApiModelProperty(value = "员工姓名")
    private String employeeFirstName;

    @ApiModelProperty(value = "员工姓名")
    private String empployeeLastName;

    @ApiModelProperty(value = "电话国家代码")
    private String telCountryCode;

    @ApiModelProperty(value = "电话")
    private String telephone;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "生日")
    private Date birthday;

    @ApiModelProperty(value = "地址国家代码")
    private String addressCountryCode;

    @ApiModelProperty(value = "证件类型")
    private String certificateType;

    @ApiModelProperty(value = "证件正面")
    private String positivePicture;

    @ApiModelProperty(value = "证反面")
    private String negativePicture;
}
