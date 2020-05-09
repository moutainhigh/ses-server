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

@ApiModel(value = "com-redescooter-ses-web-ros-dm-OpeProductionProduct")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "ope_production_product")
public class OpeProductionProduct implements Serializable {
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
     * 产品主键
     */
    @TableField(value = "product_id")
    @ApiModelProperty(value = "产品主键")
    private Long productId;

    /**
     * 导入ID
     */
    @TableField(value = "import_id")
    @ApiModelProperty(value = "导入ID")
    private Long importId;

    /**
     * 订单编号
     */
    @TableField(value = "production_delivery_id")
    @ApiModelProperty(value = "订单编号")
    private Long productionDeliveryId;

    /**
     * 产品定义类型code
     */
    @TableField(value = "product_type_code")
    @ApiModelProperty(value = "产品定义类型code")
    private String productTypeCode;

    /**
     * 产品序列号
     */
    @TableField(value = "serial_number")
    @ApiModelProperty(value = "产品序列号")
    private String serialNumber;

    /**
     * 状态
     */
    @TableField(value = "status")
    @ApiModelProperty(value = "状态")
    private String status;

    /**
     * 型号
     */
    @TableField(value = "model")
    @ApiModelProperty(value = "型号")
    private String model;

    /**
     * 生产批次号
     */
    @TableField(value = "batch_no")
    @ApiModelProperty(value = "生产批次号")
    private String batchNo;

    /**
     * 车架号
     */
    @TableField(value = "vin")
    @ApiModelProperty(value = "车架号")
    private String vin;

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
     * 生产时间
     */
    @TableField(value = "production_time")
    @ApiModelProperty(value = "生产时间")
    private Date productionTime;

    /**
     * 工厂
     */
    @TableField(value = "factory")
    @ApiModelProperty(value = "工厂")
    private String factory;

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

    public static final String COL_PRODUCT_ID = "product_id";

    public static final String COL_IMPORT_ID = "import_id";

    public static final String COL_PRODUCTION_DELIVERY_ID = "production_delivery_id";

    public static final String COL_PRODUCT_TYPE_CODE = "product_type_code";

    public static final String COL_SERIAL_NUMBER = "serial_number";

    public static final String COL_STATUS = "status";

    public static final String COL_MODEL = "model";

    public static final String COL_BATCH_NO = "batch_no";

    public static final String COL_VIN = "vin";

    public static final String COL_MOTOR_NO = "motor_no";

    public static final String COL_ECU_NO = "ecu_no";

    public static final String COL_PRODUCTION_TIME = "production_time";

    public static final String COL_FACTORY = "factory";

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