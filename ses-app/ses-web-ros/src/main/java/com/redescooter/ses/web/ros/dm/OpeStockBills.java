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

@ApiModel(value = "com-redescooter-ses-web-ros-dm-OpeStockBills")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "ope_stock_bills")
public class OpeStockBills implements Serializable {
    /**
     * ID
     */
    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value = "ID")
    private Long id;

    /**
     * 逻辑删除标识 0正常 1删除
     */
    @TableField(value = "dr")
    @TableLogic
    @ApiModelProperty(value = "逻辑删除标识 0正常 1删除")
    private Integer dr;

    /**
     * 订单号
     */
    @TableField(value = "order_no")
    @ApiModelProperty(value = "订单号")
    private String orderNo;

    /**
     * 库存表主键
     */
    @TableField(value = "stock_id")
    @ApiModelProperty(value = "库存表主键")
    private Long stockId;

    /**
     * 仓库ID
     */
    @TableField(value = "warehouse_id")
    @ApiModelProperty(value = "仓库ID")
    private Integer warehouseId;

    /**
     * 仓库管理员Id 等价于StaffId
     */
    @TableField(value = "warehouse_manager_id")
    @ApiModelProperty(value = "仓库管理员Id 等价于StaffId")
    private Long warehouseManagerId;

    /**
     * 产品ID
     */
    @TableField(value = "product_id")
    @ApiModelProperty(value = "产品ID")
    private Long productId;

    /**
     * 数量
     */
    @TableField(value = "quantity")
    @ApiModelProperty(value = "数量")
    private Integer quantity;

    /**
     * 出入库预约时间
     */
    @TableField(value = "bills_time")
    @ApiModelProperty(value = "出入库预约时间 ")
    private Date billsTime;

    /**
     * 状态 RESERVED;SUCCESS;CANCEL
     */
    @TableField(value = "status")
    @ApiModelProperty(value = "状态 RESERVED;SUCCESS;CANCEL")
    private String status;

    /**
     * 单据来源，1Sales, 2after-sales, 3production, 4procurement
     */
    @TableField(value = "source")
    @ApiModelProperty(value = "单据来源，1Sales, 2after-sales, 3production, 4procurement")
    private Integer source;

    /**
     * 单据来源主键，即销售单主键，生产单主键，等
     */
    @TableField(value = "source_id")
    @ApiModelProperty(value = "单据来源主键，即销售单主键，生产单主键，等")
    private Long sourceId;

    /**
     * 出入库方向 IN;OUT
     */
    @TableField(value = "direction")
    @ApiModelProperty(value = "出入库方向 IN;OUT")
    private String direction;

    /**
     * 出入库人员
     */
    @TableField(value = "staff_id")
    @ApiModelProperty(value = "出入库人员")
    private Long staffId;

    /**
     * 出入库完成时间
     */
    @TableField(value = "complete_time")
    @ApiModelProperty(value = "出入库完成时间")
    private Date completeTime;

    /**
     * 创建人
     */
    @TableField(value = "created_by")
    @ApiModelProperty(value = "创建人")
    private Integer createdBy;

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

    public static final String COL_ORDER_NO = "order_no";

    public static final String COL_STOCK_ID = "stock_id";

    public static final String COL_WAREHOUSE_ID = "warehouse_id";

    public static final String COL_WAREHOUSE_MANAGER_ID = "warehouse_manager_id";

    public static final String COL_PRODUCT_ID = "product_id";

    public static final String COL_QUANTITY = "quantity";

    public static final String COL_BILLS_TIME = "bills_time";

    public static final String COL_STATUS = "status";

    public static final String COL_SOURCE = "source";

    public static final String COL_SOURCE_ID = "source_id";

    public static final String COL_DIRECTION = "direction";

    public static final String COL_STAFF_ID = "staff_id";

    public static final String COL_COMPLETE_TIME = "complete_time";

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