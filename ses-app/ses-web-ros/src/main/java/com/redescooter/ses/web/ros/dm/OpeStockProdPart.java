package com.redescooter.ses.web.ros.dm;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value="com-redescooter-ses-web-ros-dm-OpeStockProdPart")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "ope_stock_prod_part")
public class OpeStockProdPart {
    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value="")
    private Long id;

    @TableField(value = "dr")
    @ApiModelProperty(value="删除标识")
    @TableLogic
    private Integer dr;

    /**
     * 用户id
     */
    @TableField(value = "user_id")
    @ApiModelProperty(value="用户id")
    private Long userId;

    /**
     * 可用、破损
     */
    @TableField(value = "status")
    @ApiModelProperty(value="可用、破损")
    private String status;

    /**
     * 租户id
     */
    @TableField(value = "tenant_id")
    @ApiModelProperty(value="租户id")
    private Long tenantId;

    /**
     * 入库单Id
     */
    @TableField(value = "stock_bill_id")
    @ApiModelProperty(value="入库单Id")
    private Long stockBillId;

    /**
     * 库存id
     */
    @TableField(value = "stock_id")
    @ApiModelProperty(value="库存id")
    private Long stockId;

    /**
     * 物料ID
     */
    @TableField(value = "part_id")
    @ApiModelProperty(value="物料ID")
    private Long partId;

    /**
     * 批次号
     */
    @TableField(value = "lot")
    @ApiModelProperty(value="批次号")
    private String lot;

    /**
     * 序列号
     */
    @TableField(value = "serial_number")
    @ApiModelProperty(value="序列号")
    private String serialNumber;

    /**
     * 部品号
     */
    @TableField(value = "parts_number")
    @ApiModelProperty(value="部品号")
    private String partsNumber;

    /**
     * 入库负责人Id
     */
    @TableField(value = "principal_id")
    @ApiModelProperty(value="入库负责人Id")
    private Long principalId;

    /**
     * 入库时间
     */
    @TableField(value = "in_stock_time")
    @ApiModelProperty(value="入库时间")
    private Date inStockTime;

    /**
     * 乐观锁
     */
    @TableField(value = "revision")
    @ApiModelProperty(value="乐观锁")
    private Integer revision;

    /**
     * 创建人
     */
    @TableField(value = "created_by")
    @ApiModelProperty(value="创建人")
    private Long createdBy;

    /**
     * 创建时间
     */
    @TableField(value = "created_time")
    @ApiModelProperty(value="创建时间")
    private Date createdTime;

    /**
     * 更新人
     */
    @TableField(value = "updated_by")
    @ApiModelProperty(value="更新人")
    private Long updatedBy;

    /**
     * 更新时间
     */
    @TableField(value = "updated_time")
    @ApiModelProperty(value="更新时间")
    private Date updatedTime;

    /**
     * 冗余字段
     */
    @TableField(value = "def1")
    @ApiModelProperty(value="冗余字段")
    private String def1;

    /**
     * 冗余字段
     */
    @TableField(value = "def2")
    @ApiModelProperty(value="冗余字段")
    private String def2;

    /**
     * 冗余字段
     */
    @TableField(value = "def3")
    @ApiModelProperty(value="冗余字段")
    private String def3;

    /**
     * 冗余字段
     */
    @TableField(value = "def5")
    @ApiModelProperty(value="冗余字段")
    private String def5;

    /**
     * 冗余字段
     */
    @TableField(value = "def6")
    @ApiModelProperty(value="冗余字段")
    private Double def6;

    public static final String COL_ID = "id";

    public static final String COL_DR = "dr";

    public static final String COL_USER_ID = "user_id";

    public static final String COL_STATUS = "status";

    public static final String COL_TENANT_ID = "tenant_id";

    public static final String COL_STOCK_BILL_ID = "stock_bill_id";

    public static final String COL_STOCK_ID = "stock_id";

    public static final String COL_PART_ID = "part_id";

    public static final String COL_LOT = "lot";

    public static final String COL_SERIAL_NUMBER = "serial_number";

    public static final String COL_PARTS_NUMBER = "parts_number";

    public static final String COL_PRINCIPAL_ID = "principal_id";

    public static final String COL_IN_STOCK_TIME = "in_stock_time";

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