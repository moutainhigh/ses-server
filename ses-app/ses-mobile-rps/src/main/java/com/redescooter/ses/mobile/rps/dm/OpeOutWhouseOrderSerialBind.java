package com.redescooter.ses.mobile.rps.dm;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author assert
 * @date 2021/1/25 15:28
 */
@ApiModel(value = "com-redescooter-ses-mobile-rps-dm-OpeOutWhouseOrderSerialBind")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OpeOutWhouseOrderSerialBind {
    public static final String COL_ID = "id";
    public static final String COL_DR = "dr";
    public static final String COL_ORDER_B_ID = "order_b_id";
    public static final String COL_ORDER_TYPE = "order_type";
    public static final String COL_SERIAL_NUM = "serial_num";
    public static final String COL_DEFAULT_SERIAL_NUM = "default_serial_num";
    public static final String COL_TABLET_SN = "tablet_sn";
    public static final String COL_BLUETOOTH_MAC_ADDRESS = "bluetooth_mac_address";
    public static final String COL_LOT = "lot";
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
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private Long id;

    /**
     * 逻辑删除
     */
    @ApiModelProperty(value = "逻辑删除")
    private Integer dr;

    /**
     * 出库单产品表id
     */
    @ApiModelProperty(value = "出库单产品表id")
    private Long orderBId;

    /**
     * 出库单产品类型 1车辆 2组装件 3部件
     */
    @ApiModelProperty(value = "出库单产品类型 1车辆 2组装件 3部件")
    private Integer orderType;

    /**
     * 序列号
     */
    @ApiModelProperty(value = "序列号")
    private String serialNum;

    /**
     * 车辆平板序列号(整车时才会有值)
     */
    @ApiModelProperty(value = "车辆平板序列号(整车时才会有值)")
    private String tabletSn;

    /**
     * 部件本身序列号(厂商序列号)
     */
    @ApiModelProperty(value = "部件本身序列号(厂商序列号)")
    private String defaultSerialNum;

    /**
     * 蓝牙mac地址
     */
    @ApiModelProperty(value = "蓝牙mac地址")
    private String bluetoothMacAddress;

    /**
     * 批次号
     */
    @ApiModelProperty(value = "批次号")
    private String lot;

    /**
     * 产品Id
     */
    @ApiModelProperty(value = "产品Id")
    private Long productId;

    /**
     * 产品类型：1、整车 2、组合 3、零部件
     */
    @ApiModelProperty(value = "产品类型：1、整车 2、组合 3、零部件")
    private Integer productType;

    /**
     * 数量
     */
    @ApiModelProperty(value = "数量")
    private Integer qty;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private Long createdBy;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createdTime;

    /**
     * 更新人
     */
    @ApiModelProperty(value = "更新人")
    private Long updatedBy;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private Date updatedTime;

    /**
     * 冗余字段
     */
    @ApiModelProperty(value = "冗余字段")
    private String def1;

    /**
     * 冗余字段
     */
    @ApiModelProperty(value = "冗余字段")
    private String def2;

    /**
     * 冗余字段
     */
    @ApiModelProperty(value = "冗余字段")
    private String def3;

    /**
     * 冗余字段
     */
    @ApiModelProperty(value = "冗余字段")
    private String def4;

    /**
     * 冗余字段
     */
    @ApiModelProperty(value = "冗余字段")
    private BigDecimal def5;

    public static OpeOutWhouseOrderSerialBindBuilder builder() {
        return new OpeOutWhouseOrderSerialBindBuilder();
    }
}