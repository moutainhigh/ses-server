package com.redescooter.ses.mobile.rps.dm;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * @author assert
 * @date 2021/2/2 11:55
 */
@ApiModel(value = "com-redescooter-ses-mobile-rps-dm-OpeCombinOrder")
@Data
@TableName(value = "ope_combin_order")
public class OpeCombinOrder implements Serializable {
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
     * 组装单类型，1：车辆组装单，2组装件组装单
     */
    @TableField(value = "combin_type")
    @ApiModelProperty(value = "组装单类型，1：车辆组装单，2组装件组装单")
    private Integer combinType;

    /**
     * 组装单号
     */
    @TableField(value = "combin_no")
    @ApiModelProperty(value = "组装单号")
    private String combinNo;

    /**
     * 组装单状态 1草稿 10待备料 20备料完成 30组装中 40组装完成 45质检中 50质检完成
     */
    @TableField(value = "combin_status")
    @ApiModelProperty(value = "组装单状态 1草稿 10待备料 20备料完成 30组装中 40组装完成 45质检中 50质检完成")
    private Integer combinStatus;

    /**
     * 组装数量
     */
    @TableField(value = "combin_qty")
    @ApiModelProperty(value = "组装数量")
    private Integer combinQty;

    /**
     * 组装开始日期
     */
    @TableField(value = "combin_start_date")
    @ApiModelProperty(value = "组装开始日期")
    private Date combinStartDate;

    /**
     * 组装结束日期
     */
    @TableField(value = "combin_end_date")
    @ApiModelProperty(value = "组装结束日期")
    private Date combinEndDate;

    /**
     * 负责人id
     */
    @TableField(value = "principal_id")
    @ApiModelProperty(value = "负责人id")
    private Long principalId;

    /**
     * 负责人名称
     */
    @TableField(value = "principal_name")
    @ApiModelProperty(value = "负责人名称")
    private String principalName;

    /**
     * 国家编码，
     */
    @TableField(value = "country_code")
    @ApiModelProperty(value = "国家编码，")
    private String countryCode;

    /**
     * 联系电话
     */
    @TableField(value = "telephone")
    @ApiModelProperty(value = "联系电话")
    private String telephone;

    /**
     * 备注
     */
    @TableField(value = "remark")
    @ApiModelProperty(value = "备注")
    private String remark;

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

    public static final String COL_COMBIN_TYPE = "combin_type";

    public static final String COL_COMBIN_NO = "combin_no";

    public static final String COL_COMBIN_STATUS = "combin_status";

    public static final String COL_COMBIN_QTY = "combin_qty";

    public static final String COL_COMBIN_START_DATE = "combin_start_date";

    public static final String COL_COMBIN_END_DATE = "combin_end_date";

    public static final String COL_PRINCIPAL_ID = "principal_id";

    public static final String COL_PRINCIPAL_NAME = "principal_name";

    public static final String COL_COUNTRY_CODE = "country_code";

    public static final String COL_TELEPHONE = "telephone";

    public static final String COL_REMARK = "remark";

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
