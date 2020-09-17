package com.redescooter.ses.web.ros.vo.sys.dept;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @ClassNameDeptListEnter
 * @Description
 * @Author Joan
 * @Date2020/8/31 18:21
 * @Version V1.0
 **/
@ApiModel(value = "部门查询")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class DeptListEnter extends GeneralEnter {
    @ApiModelProperty(value = "部门名称", required = true)
    private String keyWrod;

    @ApiModelProperty(value = "部门类型", required = true)
    private String deptType;
}
