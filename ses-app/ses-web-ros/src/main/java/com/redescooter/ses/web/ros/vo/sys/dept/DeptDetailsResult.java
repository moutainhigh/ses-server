package com.redescooter.ses.web.ros.vo.sys.dept;

import com.baomidou.mybatisplus.annotation.TableField;
import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.web.ros.exception.ValidationExceptionCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.Date;

/**
 * @ClassNameDeptDetailsResult
 * @Description
 * @Author Joan
 * @Date2020/9/2 13:53
 * @Version V1.0
 **/
@ApiModel(value = "查询部门详情入参", description = "查询部门详情入参")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class DeptDetailsResult extends GeneralResult {
    @ApiModelProperty(value = "id")
    protected long id;

    @ApiModelProperty(value = "部门名称")
    private String name;

    @ApiModelProperty(value = "部门编码")
    private String code;

    @ApiModelProperty(value = "上级部门")
    private Long pId;

    @ApiModelProperty(value = "级别0公司，1部门")
    private Integer level;

    @ApiModelProperty(value = "负责人")
    private Long principal;

    @ApiModelProperty(value = "部门人数")
    private int employeeCount;

    @ApiModelProperty(value = "下属部门数")
    private int deptCount;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @TableField(value = "created_by")
    @ApiModelProperty(value = "创建人")
    private Long createdBy;

    @ApiModelProperty(value = "创建时间")
    private Date createdTime;

    @ApiModelProperty(value = "更新人")
    private Long updatedBy;

    @ApiModelProperty(value = "修改时间")
    private Date updatedTime;

    @ApiModelProperty(value = "部门状态 1：正常，2：禁用")
    private Integer deptStatus;

}
