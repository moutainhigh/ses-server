package com.redescooter.ses.api.common.vo.oms;

import com.redescooter.ses.api.common.annotation.MaximumLength;
import com.redescooter.ses.api.common.annotation.MinimumLength;
import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.annotation.Regexp;
import com.redescooter.ses.api.common.constant.RegexpConstant;
import com.redescooter.ses.api.common.exception.ValidationExceptionBaseCode;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value = "OMS创建用户信息", description = "OMS创建用户信息")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
public class SaveAccountEnter extends GeneralEnter {

    /**
     * 冗余字段
     */
    @ApiModelProperty(value="使用者")
    @NotNull(code = ValidationExceptionBaseCode.NAME_ILLEAGE, message = "名称不能为空")
    private String name;

    /**
     * 冗余字段
     */
    @ApiModelProperty(value="部门名称")
    @NotNull(code = ValidationExceptionBaseCode.BUSSINESS_OBJ_IS_EMPTY, message = "部门不能为空")
    private String deptName;

    /**
     * 登录名
     */
    @ApiModelProperty(value="登录名")
    @NotNull(code = ValidationExceptionBaseCode.EMAIL_IS_EMPTY, message = "登录名不能为空")
    private String loginName;

}
