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
 * @date 2021/2/2 11:26
 */
@ApiModel(value = "com-redescooter-ses-mobile-rps-dm-OpeWmsStockSerialNumber")
@Data
@TableName(value = "ope_wms_stock_serial_number")
public class OpeWmsStockSerialNumber implements Serializable {
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
     * 产品类型 1车辆 2组装件 3部件
     */
    @TableField(value = "relation_type")
    @ApiModelProperty(value = "产品类型 1车辆 2组装件 3部件")
    private Integer relationType;

    /**
     * 关联id
     */
    @TableField(value = "relation_id")
    @ApiModelProperty(value = "关联id")
    private Long relationId;

    /**
     * 仓库类型，1:中国仓库，2:法国仓库
     */
    @TableField(value = "stock_type")
    @ApiModelProperty(value = "仓库类型，1:中国仓库，2:法国仓库")
    private Integer stockType;

    /**
     * 序列号
     */
    @TableField(value = "rsn")
    @ApiModelProperty(value = "序列号")
    private String rsn;

    /**
     * 库存状态 0草稿(待入库) 1可继续使用 2已被别人使用
     */
    @TableField(value = "stock_status")
    @ApiModelProperty(value = "库存状态 0草稿(待入库) 1可继续使用 2已被别人使用")
    private Integer stockStatus;

    /**
     * 批次号
     */
    @TableField(value = "lot_num")
    @ApiModelProperty(value = "批次号")
    private String lotNum;

    /**
     * 供应商序列号
     */
    @TableField(value = "sn")
    @ApiModelProperty(value = "供应商序列号")
    private String sn;

    /**
     * 蓝牙mac地址
     */
    @TableField(value = "bluetooth_mac_address")
    @ApiModelProperty(value = "蓝牙mac地址")
    private String bluetoothMacAddress;

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

    public static final String COL_RELATION_TYPE = "relation_type";

    public static final String COL_RELATION_ID = "relation_id";

    public static final String COL_STOCK_TYPE = "stock_type";

    public static final String COL_RSN = "rsn";

    public static final String COL_STOCK_STATUS = "stock_status";

    public static final String COL_LOT_NUM = "lot_num";

    public static final String COL_SN = "sn";

    public static final String COL_BLUETOOTH_MAC_ADDRESS = "bluetooth_mac_address";

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
