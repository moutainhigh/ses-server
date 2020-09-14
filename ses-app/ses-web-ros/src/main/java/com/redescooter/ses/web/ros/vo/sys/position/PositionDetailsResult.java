package com.redescooter.ses.web.ros.vo.sys.position;

import com.baomidou.mybatisplus.annotation.TableField;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.Date;

/**
 * @ClassNamePositionDetailsResult
 * @Description
 * @Author Joan
 * @Date2020/9/3 18:48
 * @Version V1.0
 **/
@ApiModel(value = "岗位新增", description = "岗位新增")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class PositionDetailsResult extends GeneralResult {
    @ApiModelProperty(value = "id", required = true)
    private long id;

    @ApiModelProperty(value = "岗位名称")
    private String positionName;

    @ApiModelProperty(value = "岗位编码")
    private String positionCode;

    @ApiModelProperty(value = "所属部门名称")
    private String deptName;

    @ApiModelProperty(value = "所属部门id")
    private String deptId;

    @ApiModelProperty(value = "岗位排序")
    private Integer sort;

    @ApiModelProperty(value = "岗位人数")
    private Integer positionPersonnel;

    @ApiModelProperty(value = "岗位状态  1：正常，2：禁用")
    private Integer positionStatus;

    @ApiModelProperty(value = "创建人")
    private String createdName;

    @ApiModelProperty(value = "创建时间")
    private Date createdTime;

    @ApiModelProperty(value = "更新人")
    private String updatedName;

    @ApiModelProperty(value = "更新时间")
    private Date updatedTime;

}
