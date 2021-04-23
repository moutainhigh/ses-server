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
 * @date 2021/2/2 13:49
 */
@ApiModel(value = "com-redescooter-ses-mobile-rps-dm-OpeCombinListPartsSerialBind")
@Data
@TableName(value = "ope_combin_list_parts_serial_bind")
public class OpeCombinListPartsSerialBind implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value = "主键")
    private Long id;

    /**
     * 逻辑删除
     */
    @TableField(value = "dr")
    @ApiModelProperty(value = "逻辑删除")
    private Integer dr;

    /**
     * 组装清单表id
     */
    @TableField(value = "order_b_id")
    @ApiModelProperty(value = "组装清单表id")
    private Long orderBId;

    /**
     * 组装产品类型 1车辆 2组装件
     */
    @TableField(value = "order_type")
    @ApiModelProperty(value = "组装产品类型 1车辆 2组装件")
    private Integer orderType;

    /**
     * 序列号
     */
    @TableField(value = "serial_num")
    @ApiModelProperty(value = "序列号")
    private String serialNum;

    /**
     * 部件本身序列号(厂商序列号)
     */
    @TableField(value = "default_serial_num")
    @ApiModelProperty(value = "部件本身序列号(厂商序列号)")
    private String defaultSerialNum;

    /**
     * 批次号
     */
    @TableField(value = "lot")
    @ApiModelProperty(value = "批次号")
    private String lot;

    /**
     * 蓝牙mac地址
     */
    @TableField(value = "bluetooth_mac_address")
    @ApiModelProperty(value = "蓝牙mac地址")
    private String bluetoothMacAddress;

    /**
     * 产品Id
     */
    @TableField(value = "product_id")
    @ApiModelProperty(value = "产品Id")
    private Long productId;

    /**
     * 产品类型：3零部件
     */
    @TableField(value = "product_type")
    @ApiModelProperty(value = "产品类型：3零部件")
    private Integer productType;

    /**
     * 数量
     */
    @TableField(value = "qty")
    @ApiModelProperty(value = "数量")
    private Integer qty;

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

    public static final String COL_ORDER_B_ID = "order_b_id";

    public static final String COL_ORDER_TYPE = "order_type";

    public static final String COL_SERIAL_NUM = "serial_num";

    public static final String COL_DEFAULT_SERIAL_NUM = "default_serial_num";

    public static final String COL_LOT = "lot";

    public static final String COL_BLUETOOTH_MAC_ADDRESS = "bluetooth_mac_address";

    public static final String COL_PRODUCT_ID = "product_id";

    public static final String COL_PRODUCT_TYPE = "product_type";

    public static final String COL_QTY = "qty";

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
