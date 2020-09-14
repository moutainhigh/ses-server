package com.redescooter.ses.web.ros.vo.sys.role;

import com.baomidou.mybatisplus.annotation.TableField;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @ClassNameRoleDetailResult
 * @Description
 * @Author Aleks
 * @Date2020/9/1 16:11
 * @Version V1.0
 **/
@Data
public class RoleDetailResult extends GeneralResult {

    @ApiModelProperty("角色id")
    private Long id;

    @ApiModelProperty("角色名称")
    private String roleName;

    @ApiModelProperty("角色编码")
    private String roleCode;

    @ApiModelProperty("所属岗位id")
    private Long positionId;

    @ApiModelProperty("所属岗位")
    private String positionName;

    @ApiModelProperty("角色排序")
    private Integer sort;

    @ApiModelProperty("角色状态  1：正常，2：禁用")
    private Integer roleStatus;

    @ApiModelProperty("角色人数")
    private Integer num = 0;

    @ApiModelProperty("角色描述")
    private String roleDesc;

    @ApiModelProperty(value = "创建人")
    private String createdName;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新人")
    private String updatedName;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty("是否开启销售区域功能 0：否，1：是")
    private Integer saleArea;
}
