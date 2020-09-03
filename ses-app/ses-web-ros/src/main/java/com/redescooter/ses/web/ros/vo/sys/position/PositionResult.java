package com.redescooter.ses.web.ros.vo.sys.position;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.PageEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.Date;

/**
 * @ClassNamePositionResult
 * @Description
 * @Author Joan
 * @Date2020/9/3 13:17
 * @Version V1.0
 **/
@ApiModel(value = "岗位列表", description = "岗位列表")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class PositionResult extends GeneralResult {
    @ApiModelProperty(value = "id", required = true)
    private long id;

    @ApiModelProperty(value = "岗位名称")
    private String positionName;

    @ApiModelProperty(value = "岗位编码")
    private String positionCode;


    @ApiModelProperty(value = "所属部门名称")
    private String deptName;

    @ApiModelProperty(value = "岗位排序")
    private Integer sort;

    @ApiModelProperty(value = "岗位人数")
    private Integer positionPersonnel;

    @ApiModelProperty(value = "岗位状态  1：正常，2：禁用")
    private Integer positionStatus;

    @ApiModelProperty(value = "创建时间")
    private Date createdTime;
}
