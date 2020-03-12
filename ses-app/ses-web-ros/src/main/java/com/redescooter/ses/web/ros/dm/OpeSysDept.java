package com.redescooter.ses.web.ros.dm;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@ApiModel(value = "com-redescooter-ses-web-ros-dm-OpeSysDept")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "ope_sys_dept")
public class OpeSysDept implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "")
    private Long id;

    /**
     * 逻辑删除
     */
    @TableField(value = "dr")
    @TableLogic
    @ApiModelProperty(value = "逻辑删除")
    private Integer dr;

    /**
     * 父级部门id
     */
    @TableField(value = "p_id")
    @ApiModelProperty(value = "父级部门id")
    private Long pId;

    /**
     * 租户id
     */
    @TableField(value = "tenant_id")
    @ApiModelProperty(value = "租户id")
    private Long tenantId;

    /**
     * 负责人
     */
    @TableField(value = "principal")
    @ApiModelProperty(value = "负责人")
    private Long principal;

    /**
     * 级别0公司，1部门
     */
    @TableField(value = "level")
    @ApiModelProperty(value = "级别0公司，1部门")
    private Integer level;

    @TableField(value = "name")
    @ApiModelProperty(value = "")
    private String name;

    /**
     * 排序
     */
    @TableField(value = "sort")
    @ApiModelProperty(value = "排序")
    private Integer sort;

    /**
     * 创建人
     */
    @TableField(value = "created_by")
    @ApiModelProperty(value = "创建人")
    private Long createdBy;

    /**
     * 创建时间
     */
    @TableField(value = "created_time")
    @ApiModelProperty(value = "创建时间")
    private Date createdTime;

    /**
     * 更新人
     */
    @TableField(value = "updated_by")
    @ApiModelProperty(value = "更新人")
    private Long updatedBy;

    /**
     * 修改时间
     */
    @TableField(value = "updated_time")
    @ApiModelProperty(value = "修改时间")
    private Date updatedTime;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_DR = "dr";

    public static final String COL_P_ID = "p_id";

    public static final String COL_TENANT_ID = "tenant_id";

    public static final String COL_PRINCIPAL = "principal";

    public static final String COL_LEVEL = "level";

    public static final String COL_NAME = "name";

    public static final String COL_SORT = "sort";

    public static final String COL_CREATED_BY = "created_by";

    public static final String COL_CREATED_TIME = "created_time";

    public static final String COL_UPDATED_BY = "updated_by";

    public static final String COL_UPDATED_TIME = "updated_time";
}