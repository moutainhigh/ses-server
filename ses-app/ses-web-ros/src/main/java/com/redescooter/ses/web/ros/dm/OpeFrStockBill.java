package com.redescooter.ses.web.ros.dm;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value = "com-redescooter-ses-web-ros-dm-OpeFrStockBill")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "ope_fr_stock_bill")
public class OpeFrStockBill implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value = "主键")
    private Long id;

    /**
     * 删除标识
     */
    @TableField(value = "dr")
    @ApiModelProperty(value = "删除标识")
    private Integer dr;

    /**
     * 用户id
     */
    @TableField(value = "user_id")
    @ApiModelProperty(value = "用户id")
    private Long userId;

    /**
     * 租户id
     */
    @TableField(value = "tenant_id")
    @ApiModelProperty(value = "租户id")
    private Long tenantId;

    /**
     * 库存id
     */
    @TableField(value = "stock_id")
    @ApiModelProperty(value = "库存id")
    private Long stockId;

    /**
     * 入库 0:In 出库 1:Out
     */
    @TableField(value = "direction")
    @ApiModelProperty(value = "入库 0:In 出库 1:Out")
    private String direction;

    /**
     * 单据来源ID
     */
    @TableField(value = "source_id")
    @ApiModelProperty(value = "单据来源ID")
    private Long sourceId;

    /**
     * 状态:0正常，1异常
     */
    @TableField(value = "status")
    @ApiModelProperty(value = "状态:0正常，1异常")
    private String status;

    /**
     * 入库单数量总计
     */
    @TableField(value = "total")
    @ApiModelProperty(value = "入库单数量总计")
    private Integer total;

    /**
     * 单据来源:1采购入库单，2采购入库单，3调拨入库单，4调拨出库单
     */
    @TableField(value = "source_type")
    @ApiModelProperty(value = "单据来源:1采购入库单，2采购入库单，3调拨入库单，4调拨出库单")
    private String sourceType;

    /**
     * 负责人
     */
    @TableField(value = "principal_id")
    @ApiModelProperty(value = "负责人")
    private Long principalId;

    /**
     * 操作时间
     */
    @TableField(value = "operatine_time")
    @ApiModelProperty(value = "操作时间")
    private Date operatineTime;

    /**
     * 乐观锁
     */
    @TableField(value = "revision")
    @ApiModelProperty(value = "乐观锁")
    private Integer revision;

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

    public static final String COL_ID = "id";

    public static final String COL_DR = "dr";

    public static final String COL_USER_ID = "user_id";

    public static final String COL_TENANT_ID = "tenant_id";

    public static final String COL_STOCK_ID = "stock_id";

    public static final String COL_DIRECTION = "direction";

    public static final String COL_SOURCE_ID = "source_id";

    public static final String COL_STATUS = "status";

    public static final String COL_TOTAL = "total";

    public static final String COL_SOURCE_TYPE = "source_type";

    public static final String COL_PRINCIPAL_ID = "principal_id";

    public static final String COL_OPERATINE_TIME = "operatine_time";

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