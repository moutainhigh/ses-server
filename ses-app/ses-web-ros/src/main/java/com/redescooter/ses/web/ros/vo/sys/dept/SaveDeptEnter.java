package com.redescooter.ses.web.ros.vo.sys.dept;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
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
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class SaveDeptEnter extends GeneralEnter {

    @ApiModelProperty(value = "父级部门id")
    private Long pId;

    @ApiModelProperty(value = "负责人")
    private Integer principal;

    @ApiModelProperty(value = "级别0公司，1部门")
    private Integer level;

    @ApiModelProperty(value = "部门名称")
    private String name;

    @ApiModelProperty(value = "部门编码")
    private String code;

    @ApiModelProperty(value = "排序")
    private Integer sort;

}
