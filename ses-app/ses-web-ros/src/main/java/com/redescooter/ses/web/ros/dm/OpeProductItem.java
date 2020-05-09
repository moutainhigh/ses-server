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

@ApiModel(value = "com-redescooter-ses-web-ros-dm-OpeProductItem")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "ope_product_item")
public class OpeProductItem implements Serializable {
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
    private Integer productId;

    /**
     * 产品序列号
     */
    @TableField(value = "serial_number")
    @ApiModelProperty(value = "产品序列号")
    private String serialNumber;

    /**
     * 状态 已消耗 consumed/可用 available
     */
    @TableField(value = "status")
    @ApiModelProperty(value = "状态 已消耗 consumed/可用 available")
    private String status;

    /**
     * 电机号
     */
    @TableField(value = "motor_no")
    @ApiModelProperty(value = "电机号")
    private String motorNo;

    /**
     * 仪表号
     */
    @TableField(value = "ecu_no")
    @ApiModelProperty(value = "仪表号")
    private String ecuNo;

    /**
     * 生产批次号
     */
    @TableField(value = "production_batch_no")
    @ApiModelProperty(value = "生产批次号")
    private String productionBatchNo;

    /**
     * 生产时间
     */
    @TableField(value = "production_time")
    @ApiModelProperty(value = "生产时间")
    private String productionTime;

    /**
     * 工厂
     */
    @TableField(value = "factory")
    @ApiModelProperty(value = "工厂")
    private String factory;

    /**
     * 关联信息
     */
    @TableField(value = "relation_info")
    @ApiModelProperty(value = "关联信息")
    private String relationInfo;

    /**
     * 父ID
     */
    @TableField(value = "p_id")
    @ApiModelProperty(value = "父ID")
    private Long pId;

    /**
     * 入库单ID
     */
    @TableField(value = "in_stock_bills_id")
    @ApiModelProperty(value = "入库单ID")
    private Long inStockBillsId;

    /**
     * 出库单ID
     */
    @TableField(value = "out_stock_bills_id")
    @ApiModelProperty(value = "出库单ID")
    private Long outStockBillsId;

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

    public static final String COL_SERIAL_NUMBER = "serial_number";

    public static final String COL_STATUS = "status";

    public static final String COL_MOTOR_NO = "motor_no";

    public static final String COL_ECU_NO = "ecu_no";

    public static final String COL_PRODUCTION_BATCH_NO = "production_batch_no";

    public static final String COL_PRODUCTION_TIME = "production_time";

    public static final String COL_FACTORY = "factory";

    public static final String COL_RELATION_INFO = "relation_info";

    public static final String COL_P_ID = "p_id";

    public static final String COL_IN_STOCK_BILLS_ID = "in_stock_bills_id";

    public static final String COL_OUT_STOCK_BILLS_ID = "out_stock_bills_id";

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