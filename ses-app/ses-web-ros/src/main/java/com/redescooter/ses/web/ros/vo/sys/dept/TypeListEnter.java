package com.redescooter.ses.web.ros.vo.sys.dept;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.web.ros.exception.ValidationExceptionCode;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassNameTypeListEnter
 * @Description 组织架构选择部门 岗位 角色的下拉传参
 * @Author Aleks
 * @Date2020/11/24 19:14
 * @Version V1.0
 **/
@Data
public class TypeListEnter extends GeneralEnter {

    @ApiModelProperty("类型，1：查询全部的，2:查询未禁用的")
    @NotNull(code = ValidationExceptionCode.TYPE_IS_EMPTY, message = "TYPE为空")
    private Integer type;

}
