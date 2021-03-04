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
 * @date 2021/2/2 10:04
 */
@ApiModel(value = "com-redescooter-ses-mobile-rps-dm-OpeInWhouseOrder")
@Data
@TableName(value = "ope_in_whouse_order")
public class OpeInWhouseOrder implements Serializable {
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
     * 国家类型，1:中国，2:法国
     */
    @TableField(value = "country_type")
    @ApiModelProperty(value = "国家类型，1:中国，2:法国")
    private Integer countryType;

    /**
     * 入库单号
     */
    @TableField(value = "in_wh_no")
    @ApiModelProperty(value = "入库单号")
    private String inWhNo;

    /**
     * 入库单状态 1草稿 10已入库
     */
    @TableField(value = "in_wh_status")
    @ApiModelProperty(value = "入库单状态 1草稿 10已入库")
    private Integer inWhStatus;

    /**
     * 入库仓库。1:成品库，2:原料库，3:不合格品库
     */
    @TableField(value = "wh_type")
    @ApiModelProperty(value = "入库仓库。1:成品库，2:原料库，3:不合格品库")
    private Integer whType;

    /**
     * 入库单据类型,1:车辆，2:组装件，3:部件
     */
    @TableField(value = "order_type")
    @ApiModelProperty(value = "入库单据类型,1:车辆，2:组装件，3:部件")
    private Integer orderType;

    /**
     * 关联的单据id
     */
    @TableField(value = "relation_order_id")
    @ApiModelProperty(value = "关联的单据id")
    private Long relationOrderId;

    /**
     * 关联的单据号
     */
    @TableField(value = "relation_order_no")
    @ApiModelProperty(value = "关联的单据号")
    private String relationOrderNo;

    /**
     * 关联的单据类型，7：生产采购单，9：组装单
     */
    @TableField(value = "relation_order_type")
    @ApiModelProperty(value = "关联的单据类型，7：生产采购单，9：组装单")
    private Integer relationOrderType;

    /**
     * 入库类型，1：生产入库，2：返修入库，3：采购入库，4：退料入库，5：其他，6:报废入库，7:调拨入库
     */
    @TableField(value = "in_wh_type")
    @ApiModelProperty(value = "入库类型，1：生产入库，2：返修入库，3：采购入库，4：退料入库，5：其他，6:报废入库，7:调拨入库")
    private Integer inWhType;

    /**
     * 入库数量
     */
    @TableField(value = "in_wh_qty")
    @ApiModelProperty(value = "入库数量")
    private Integer inWhQty;

    /**
     * 是否是不合格品库产生，0:否，1:是
     */
    @TableField(value = "source")
    @ApiModelProperty(value = "是否是不合格品库产生，0:否，1:是")
    private Integer source;

    /**
     * 是否是仓库新增 0否 1是
     */
    @TableField(value = "if_wh")
    @ApiModelProperty(value = "是否是仓库新增 0否 1是")
    private Integer ifWh;

    /**
     * 备注
     */
    @TableField(value = "remark")
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 质检完成时间
     */
    @TableField(value = "qc_completion_time")
    @ApiModelProperty(value = "质检完成时间")
    private Date qcCompletionTime;

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

    public static final String COL_COUNTRY_TYPE = "country_type";

    public static final String COL_IN_WH_NO = "in_wh_no";

    public static final String COL_IN_WH_STATUS = "in_wh_status";

    public static final String COL_WH_TYPE = "wh_type";

    public static final String COL_ORDER_TYPE = "order_type";

    public static final String COL_RELATION_ORDER_ID = "relation_order_id";

    public static final String COL_RELATION_ORDER_NO = "relation_order_no";

    public static final String COL_RELATION_ORDER_TYPE = "relation_order_type";

    public static final String COL_IN_WH_TYPE = "in_wh_type";

    public static final String COL_IN_WH_QTY = "in_wh_qty";

    public static final String COL_SOURCE = "source";

    public static final String COL_IF_WH = "if_wh";

    public static final String COL_REMARK = "remark";

    public static final String COL_QC_COMPLETION_TIME = "qc_completion_time";

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
