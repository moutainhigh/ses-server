package com.redescooter.ses.web.ros.dm;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

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

@ApiModel(value="com-redescooter-ses-web-ros-dm-OpePartsAssembly")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "ope_parts_assembly")
public class OpePartsAssembly implements Serializable {
    /**
     * 主键 主键
     */
    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value="主键 主键")
    private Long id;

    /**
     * 逻辑删除 逻辑删除
     */
    @TableField(value = "dr")
    @TableLogic
    @ApiModelProperty(value = "逻辑删除 逻辑删除")
    private Integer dr;

    /**
     * 租户ID 租户ID
     */
    @TableField(value = "tenant_id")
    @ApiModelProperty(value="租户ID 租户ID")
    private Long tenantId;

    /**
     * 用户ID 用户ID
     */
    @TableField(value = "user_id")
    @ApiModelProperty(value="用户ID 用户ID")
    private Long userId;

    /**
     * 状态 状态
     */
    @TableField(value = "status")
    @ApiModelProperty(value="状态 状态")
    private String status;

    /**
     * 唯一编号 部品号
     */
    @TableField(value = "ass_number")
    @ApiModelProperty(value="唯一编号 部品号")
    private String assNumber;

    /**
     * 中文名称 中文名称
     */
    @TableField(value = "cn_name")
    @ApiModelProperty(value="中文名称 中文名称")
    private String cnName;

    /**
     * 法文名称 法文名称
     */
    @TableField(value = "fr_name")
    @ApiModelProperty(value="法文名称 法文名称")
    private String frName;

    /**
     * 英文名称 英文名称
     */
    @TableField(value = "en_name")
    @ApiModelProperty(value="英文名称 英文名称")
    private String enName;

    /**
     * 已选部品数量 部品数量
     */
    @TableField(value = "in_qty")
    @ApiModelProperty(value="已选部品数量 部品数量")
    private Integer inQty;

    /**
     * 生产周期 生产周期
     */
    @TableField(value = "production_cycle")
    @ApiModelProperty(value="生产周期 生产周期")
    private String productionCycle;

    /**
     * 部品组装类型 部品组装类型,如1整车，2组合等
     */
    @TableField(value = "ass_type")
    @ApiModelProperty(value="部品组装类型 部品组装类型,如1整车，2组合等")
    private Integer assType;

    /**
     * 备注 备注
     */
    @TableField(value = "note")
    @ApiModelProperty(value="备注 备注")
    private String note;

    /**
     * 乐观锁 乐观锁
     */
    @TableField(value = "revision")
    @ApiModelProperty(value="乐观锁 乐观锁")
    private Integer revision;

    /**
     * 创建人 创建人
     */
    @TableField(value = "created_by")
    @ApiModelProperty(value="创建人 创建人")
    private Long createdBy;

    /**
     * 创建时间 创建时间
     */
    @TableField(value = "created_time")
    @ApiModelProperty(value="创建时间 创建时间")
    private Date createdTime;

    /**
     * 更新人 更新人
     */
    @TableField(value = "updated_by")
    @ApiModelProperty(value="更新人 更新人")
    private Long updatedBy;

    /**
     * 更新时间 更新时间
     */
    @TableField(value = "updated_time")
    @ApiModelProperty(value="更新时间 更新时间")
    private Date updatedTime;

    /**
     * 冗余字段 冗余字段
     */
    @TableField(value = "def1")
    @ApiModelProperty(value="冗余字段 冗余字段")
    private String def1;

    /**
     * 冗余字段 冗余字段
     */
    @TableField(value = "def2")
    @ApiModelProperty(value="冗余字段 冗余字段")
    private String def2;

    /**
     * 冗余字段 冗余字段
     */
    @TableField(value = "def3")
    @ApiModelProperty(value="冗余字段 冗余字段")
    private String def3;

    /**
     * 冗余字段 冗余字段
     */
    @TableField(value = "def5")
    @ApiModelProperty(value="冗余字段 冗余字段")
    private String def5;

    /**
     * 冗余字段 冗余字段
     */
    @TableField(value = "def6")
    @ApiModelProperty(value="冗余字段 冗余字段")
    private BigDecimal def6;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_DR = "dr";

    public static final String COL_TENANT_ID = "tenant_id";

    public static final String COL_USER_ID = "user_id";

    public static final String COL_STATUS = "status";

    public static final String COL_ASS_NUMBER = "ass_number";

    public static final String COL_CN_NAME = "cn_name";

    public static final String COL_FR_NAME = "fr_name";

    public static final String COL_EN_NAME = "en_name";

    public static final String COL_IN_QTY = "in_qty";

    public static final String COL_PRODUCTION_CYCLE = "production_cycle";

    public static final String COL_ASS_TYPE = "ass_type";

    public static final String COL_NOTE = "note";

    public static final String COL_REVISION = "revision";

    public static final String COL_CREATED_BY = "created_by";

    public static final String COL_CREATED_TIME = "created_time";

    public static final String COL_UPDATED_BY = "updated_by";

    public static final String COL_UPDATED_TIME = "updated_time";

    public static final String COL_DEF1 = "def1";

    public static final String COL_DEF2 = "def2";

    public static final String COL_DEF3 = "def3";

    public static final String COL_DEF5 = "def5";

    public static final String COL_DEF6 = "def6";
}