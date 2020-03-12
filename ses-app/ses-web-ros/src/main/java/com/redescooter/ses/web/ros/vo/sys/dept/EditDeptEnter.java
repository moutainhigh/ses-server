package com.redescooter.ses.web.ros.vo.sys.dept;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @ClassName SaveDeptEnter
 * @Author Jerry
 * @date 2020/03/11 20:09
 * @Description:
 */
@ApiModel(value = "部门创建")
@Data
public class EditDeptEnter extends SaveDeptEnter {

    @ApiModelProperty(value = "部门主键")
    private Long id;
}
