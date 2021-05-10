package com.redescooter.ses.web.ros.vo.app;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @Description
 * @Author Chris
 * @Date 2021/5/10 10:35
 */
@Data
@ApiModel(value = "仓库账号新增入参")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class WarehouseAccountSaveEnter extends GeneralEnter {

    @ApiModelProperty(value = "账号类型 1公司账号 2第三方账号")
    private Integer type;

    @ApiModelProperty(value = "使用者")
    private String name;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "职位")
    private String position;

    @ApiModelProperty(value = "可操作系统 1FR 2CH")
    private Integer system;

    @ApiModelProperty(value = "新密码")
    private String newPassword;

}
