package com.redescooter.ses.service.hub.source.operation.dm;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 组装bom表
 */
@ApiModel(value = "com-redescooter-ses-service-hub-source-operation-dm-OpeProductionCombinBom")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "ope_production_combin_bom")
public class OpeProductionCombinBom implements Serializable {
    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value = "主键id")
    private Long id;

    /**
     * 逻辑删除
     */
    @TableField(value = "dr")
    @ApiModelProperty(value = "逻辑删除")
    @TableLogic
    private Integer dr;

    /**
     * 租户ID
     */
    @TableField(value = "tenant_id")
    @ApiModelProperty(value = "租户ID")
    private Long tenantId;

    /**
     * 部门id（做数据权限用）
     */
    @TableField(value = "dept_id")
    @ApiModelProperty(value = "部门id（做数据权限用）")
    private Long deptId;

    /**
     * 版本标识
     */
    @TableField(value = "version_identificat")
    @ApiModelProperty(value = "版本标识")
    private String versionIdentificat;

    /**
     * bom编号
     */
    @TableField(value = "bom_no")
    @ApiModelProperty(value = "bom编号")
    private String bomNo;

    /**
     * 采购周期
     */
    @TableField(value = "procurement_cycle")
    @ApiModelProperty(value = "采购周期")
    private Integer procurementCycle;

    /**
     * 激活时间
     */
    @TableField(value = "effective_date")
    @ApiModelProperty(value = "激活时间")
    private Date effectiveDate;

    /**
     * 分组的id
     */
    @TableField(value = "group_id")
    @ApiModelProperty(value = "分组的id")
    private Long groupId;

    /**
     * 颜色的id
     */
    @TableField(value = "color_id")
    @ApiModelProperty(value = "颜色的id")
    private Long colorId;

    /**
     * 是否禁用，0：否，1：是
     */
    @TableField(value = "`disable`")
    @ApiModelProperty(value = "是否禁用，0：否，1：是")
    private Integer disable;

    /**
     * 状态，1：未激活，2：已激活，2：已失效
     */
    @TableField(value = "bom_status")
    @ApiModelProperty(value = "状态，1：未激活，2：已激活，2：已失效")
    private Integer bomStatus;

    /**
     * 发布人id
     */
    @TableField(value = "announ_user_id")
    @ApiModelProperty(value = "发布人id")
    private Long announUserId;

    /**
     * 操作发布人id
     */
    @TableField(value = "op_announ_user_id")
    @ApiModelProperty(value = "操作发布人id")
    private Long opAnnounUserId;

    /**
     * 版本
     */
    @TableField(value = "versoin")
    @ApiModelProperty(value = "版本")
    private String versoin;

    /**
     * 部件数量
     */
    @TableField(value = "parts_qty")
    @ApiModelProperty(value = "部件数量")
    private Integer partsQty;

    /**
     * 中文名称
     */
    @TableField(value = "cn_name")
    @ApiModelProperty(value = "中文名称")
    private String cnName;

    /**
     * 名称
     */
    @TableField(value = "en_name")
    @ApiModelProperty(value = "名称")
    private String enName;

    /**
     * 法文名称
     */
    @TableField(value = "fr_name")
    @ApiModelProperty(value = "法文名称")
    private String frName;

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
     * 更新时间
     */
    @TableField(value = "updated_time")
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
    @TableField(value = "def4")
    @ApiModelProperty(value = "冗余字段")
    private String def4;

    /**
     * 冗余字段
     */
    @TableField(value = "def5")
    @ApiModelProperty(value = "冗余字段")
    private BigDecimal def5;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_DR = "dr";

    public static final String COL_TENANT_ID = "tenant_id";

    public static final String COL_DEPT_ID = "dept_id";

    public static final String COL_VERSION_IDENTIFICAT = "version_identificat";

    public static final String COL_BOM_NO = "bom_no";

    public static final String COL_PROCUREMENT_CYCLE = "procurement_cycle";

    public static final String COL_EFFECTIVE_DATE = "effective_date";

    public static final String COL_GROUP_ID = "group_id";

    public static final String COL_COLOR_ID = "color_id";

    public static final String COL_DISABLE = "disable";

    public static final String COL_BOM_STATUS = "bom_status";

    public static final String COL_ANNOUN_USER_ID = "announ_user_id";

    public static final String COL_OP_ANNOUN_USER_ID = "op_announ_user_id";

    public static final String COL_VERSOIN = "versoin";

    public static final String COL_PARTS_QTY = "parts_qty";

    public static final String COL_CN_NAME = "cn_name";

    public static final String COL_EN_NAME = "en_name";

    public static final String COL_FR_NAME = "fr_name";

    public static final String COL_CREATED_BY = "created_by";

    public static final String COL_CREATED_TIME = "created_time";

    public static final String COL_UPDATED_BY = "updated_by";

    public static final String COL_UPDATED_TIME = "updated_time";

    public static final String COL_DEF1 = "def1";

    public static final String COL_DEF2 = "def2";

    public static final String COL_DEF3 = "def3";

    public static final String COL_DEF4 = "def4";

    public static final String COL_DEF5 = "def5";
}