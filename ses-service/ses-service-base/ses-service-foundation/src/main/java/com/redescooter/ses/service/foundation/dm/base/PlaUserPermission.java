package com.redescooter.ses.service.foundation.dm.base;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

@ApiModel(value = "com-redescooter-ses-service-foundation-dm-base-PlaUserPermission")
@Data
@TableName(value = "pla_user_permission")
public class PlaUserPermission implements Serializable {
    /**
     * ID
     */
    @TableId(value = "ID", type = IdType.INPUT)
    @ApiModelProperty(value = "ID")
    private Long id;

    /**
     * 逻辑删除标识 0正常 1删除
     */
    @TableField(value = "dr")
    @ApiModelProperty(value = "逻辑删除标识 0正常 1删除")
    private Integer dr;

    /**
     * 用户ID
     */
    @TableField(value = "USER_ID")
    @ApiModelProperty(value = "用户ID")
    private Long userId;

    /**
     * 系统ID
     */
    @TableField(value = "SYSTEM_ID")
    @ApiModelProperty(value = "系统ID")
    private String systemId;

    /**
     * 应用ID
     */
    @TableField(value = "APP_ID")
    @ApiModelProperty(value = "应用ID")
    private String appId;

    /**
     * 状态
     */
    @TableField(value = "STATUS")
    @ApiModelProperty(value = "状态")
    private String status;

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
    private Long updatedBy;

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

    public static final String COL_USER_ID = "USER_ID";

    public static final String COL_SYSTEM_ID = "SYSTEM_ID";

    public static final String COL_APP_ID = "APP_ID";

    public static final String COL_STATUS = "STATUS";

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