package com.redescooter.ses.web.ros.vo.sys.position;

import com.redescooter.ses.api.common.vo.base.PageEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @ClassNamePositionEnter
 * @Description
 * @Author Joan
 * @Date2020/9/3 14:20
 * @Version V1.0
 **/
@ApiModel(value = "岗位列表", description = "岗位列表")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class PositionEnter extends PageEnter {
    @ApiModelProperty(value = "所属部门id")
    private Long deptId;

    @ApiModelProperty(value = "岗位状态  1：正常，2：禁用")
    private Integer positionStatus;

    @ApiModelProperty(value = "关键字")
    private String keyWord;

}
