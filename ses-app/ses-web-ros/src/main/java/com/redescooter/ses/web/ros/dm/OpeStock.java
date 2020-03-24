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

@ApiModel(value = "com-redescooter-ses-web-ros-dm-OpeStock")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "ope_stock")
public class OpeStock implements Serializable {
    /**
     * ID
     */
    @TableId(value = "id", type = IdType.INPUT)
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
     * 仓库ID
     */
    @TableField(value = "warehouse_id")
    @ApiModelProperty(value = "仓库ID")
    private Long warehouseId;

    /**
     * 产品ID
     */
    @TableField(value = "product_id")
    @ApiModelProperty(value = "产品ID")
    private Long productId;

    /**
     * 产品编号
     */
    @TableField(value = "product_code")
    @ApiModelProperty(value = "产品编号")
    private String productCode;

    /**
     * 总数量
     */
    @TableField(value = "total_quantity")
    @ApiModelProperty(value = "总数量")
    private Integer totalQuantity;

    /**
     * 可用数量
     */
    @TableField(value = "available_quantity")
    @ApiModelProperty(value = "可用数量")
    private Integer availableQuantity;

    /**
     * 已预约出库数量
     */
    @TableField(value = "reserved_out_stock_quantity")
    @ApiModelProperty(value = "已预约出库数量")
    private Integer reservedOutStockQuantity;

    /**
     * 已出库数量
     */
    @TableField(value = "out_stock_quantity")
    @ApiModelProperty(value = "已出库数量")
    private Integer outStockQuantity;

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

    public static final String COL_WAREHOUSE_ID = "warehouse_id";

    public static final String COL_PRODUCT_ID = "product_id";

    public static final String COL_PRODUCT_CODE = "product_code";

    public static final String COL_TOTAL_QUANTITY = "total_quantity";

    public static final String COL_AVAILABLE_QUANTITY = "available_quantity";

    public static final String COL_RESERVED_OUT_STOCK_QUANTITY = "reserved_out_stock_quantity";

    public static final String COL_OUT_STOCK_QUANTITY = "out_stock_quantity";

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