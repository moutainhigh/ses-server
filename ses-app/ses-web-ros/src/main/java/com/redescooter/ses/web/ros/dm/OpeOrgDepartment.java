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

@ApiModel(value = "com-redescooter-ses-web-ros-dm-OpeOrgDepartment")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "ope_org_department")
public class OpeOrgDepartment implements Serializable {
    /**
     * ID
     */
    @TableId(value = "ID", type = IdType.INPUT)
    @ApiModelProperty(value = "ID")
    private Integer id;

    /**
     * 逻辑删除标识 0正常 1删除
     */
    @TableField(value = "dr")
    @TableLogic
    @ApiModelProperty(value = "逻辑删除标识 0正常 1删除")
    private Integer dr;

    /**
     * 部门名
     */
    @TableField(value = "name")
    @ApiModelProperty(value = "部门名")
    private String name;

    /**
     * 部门类型
     */
    @TableField(value = "type")
    @ApiModelProperty(value = "部门类型")
    private String type;

    @TableField(value = "manager")
    @ApiModelProperty(value = "")
    private Long manager;

    /**
     * 状态
     */
    @TableField(value = "status")
    @ApiModelProperty(value = "状态")
    private String status;

    /**
     * 上级部门
     */
    @TableField(value = "p_id")
    @ApiModelProperty(value = "上级部门")
    private Long pId;

    /**
     * 层级
     */
    @TableField(value = "level")
    @ApiModelProperty(value = "层级")
    private Integer level;

    @TableField(value = "headcount")
    @ApiModelProperty(value = "")
    private Integer headcount;

    /**
     * 所属国家
     */
    @TableField(value = "country_id")
    @ApiModelProperty(value = "所属国家")
    private Long countryId;

    /**
     * 所属城市
     */
    @TableField(value = "city_id")
    @ApiModelProperty(value = "所属城市")
    private Long cityId;

    /**
     * 创建人
     */
    @TableField(value = "CREATED_BY")
    @ApiModelProperty(value = "创建人")
    private Long createdBy;

    /**
     * 创建时间
     */
    @TableField(value = "CREATED_TIME")
    @ApiModelProperty(value = "创建时间")
    private Date createdTime;

    /**
     * 更新人
     */
    @TableField(value = "UPDATED_BY")
    @ApiModelProperty(value = "更新人")
    private Integer updatedBy;

    /**
     * 更新时间
     */
    @TableField(value = "UPDATED_TIME")
    @ApiModelProperty(value = "更新时间")
    private Date updatedTime;

    /**
     * 冗余字段
     */
    @TableField(value = "def1")
    @ApiModelProperty(value = "冗余字段")
    private String def1;

    /**
     * 冗余字段
     */
    @TableField(value = "def2")
    @ApiModelProperty(value = "冗余字段")
    private String def2;

    /**
     * 冗余字段
     */
    @TableField(value = "def3")
    @ApiModelProperty(value = "冗余字段")
    private String def3;

    /**
     * 冗余字段
     */
    @TableField(value = "def5")
    @ApiModelProperty(value = "冗余字段")
    private String def5;

    /**
     * 冗余字段
     */
    @TableField(value = "def6")
    @ApiModelProperty(value = "冗余字段")
    private Double def6;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "ID";

    public static final String COL_DR = "dr";

    public static final String COL_NAME = "name";

    public static final String COL_TYPE = "type";

    public static final String COL_MANAGER = "manager";

    public static final String COL_STATUS = "status";

    public static final String COL_P_ID = "p_id";

    public static final String COL_LEVEL = "level";

    public static final String COL_HEADCOUNT = "headcount";

    public static final String COL_COUNTRY_ID = "country_id";

    public static final String COL_CITY_ID = "city_id";

    public static final String COL_CREATED_BY = "CREATED_BY";

    public static final String COL_CREATED_TIME = "CREATED_TIME";

    public static final String COL_UPDATED_BY = "UPDATED_BY";

    public static final String COL_UPDATED_TIME = "UPDATED_TIME";

    public static final String COL_DEF1 = "def1";

    public static final String COL_DEF2 = "def2";

    public static final String COL_DEF3 = "def3";

    public static final String COL_DEF5 = "def5";

    public static final String COL_DEF6 = "def6";
}