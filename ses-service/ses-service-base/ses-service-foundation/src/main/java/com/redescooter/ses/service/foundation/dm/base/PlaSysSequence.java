package com.redescooter.ses.service.foundation.dm.base;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

@ApiModel(value = "com-redescooter-ses-service-foundation-dm-base-PlaSysSequence")
@Data
@TableName(value = "pla_sys_sequence")
public class PlaSysSequence implements Serializable {
    /**
     * sequence名称
     */
    @TableId(value = "NAME", type = IdType.INPUT)
    @ApiModelProperty(value = "sequence名称")
    private String name;

    /**
     * 逻辑删除标识 0正常 1删除
     */
    @TableField(value = "dr")
    @TableLogic
    @ApiModelProperty(value = "逻辑删除标识 0正常 1删除")
    private Integer dr;

    /**
     * 当前值
     */
    @TableField(value = "CURRENT_VALUE")
    @ApiModelProperty(value = "当前值")
    private Long currentValue;

    /**
     * 增量
     */
    @TableField(value = "INCREMENT")
    @ApiModelProperty(value = "增量")
    private Integer increment;

    /**
     * 缓存大小
     */
    @TableField(value = "CACHE")
    @ApiModelProperty(value = "缓存大小")
    private Integer cache;

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

    private static final long serialVersionUID = 1L;

    public static final String COL_NAME = "NAME";

    public static final String COL_DR = "dr";

    public static final String COL_CURRENT_VALUE = "CURRENT_VALUE";

    public static final String COL_INCREMENT = "INCREMENT";

    public static final String COL_CACHE = "CACHE";

    public static final String COL_CREATED_BY = "CREATED_BY";

    public static final String COL_CREATED_TIME = "CREATED_TIME";

    public static final String COL_UPDATED_BY = "UPDATED_BY";

    public static final String COL_UPDATED_TIME = "UPDATED_TIME";
}