package com.redescooter.ses.web.ros.dm;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
    * 成品库车辆库存表
    */
@Data
@TableName(value = "ope_wms_scooter_stock")
public class OpeWmsScooterStock implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 逻辑删除
     */
    @TableField(value = "dr")
    @TableLogic
    private Integer dr;

    /**
     * 仓库类型，1:中国仓库，2:法国仓库
     */
    @TableField(value = "stock_type")
    private Integer stockType;

    /**
     * 车型ID
     */
    @TableField(value = "group_id")
    private Long groupId;

    /**
     * 颜色ID
     */
    @TableField(value = "color_id")
    private Long colorId;

    /**
     * 可用库存数量
     */
    @TableField(value = "able_stock_qty")
    private Integer ableStockQty;

    /**
     * 已用库存数量
     */
    @TableField(value = "used_stock_qty")
    private Integer usedStockQty;

    /**
     * 待入库数量
     */
    @TableField(value = "wait_in_stock_qty")
    private Integer waitInStockQty;

    /**
     * 待出库数量
     */
    @TableField(value = "wait_out_stock_qty")
    private Integer waitOutStockQty;

    /**
     * 备注
     */
    @TableField(value = "remark")
    private String remark;

    /**
     * 创建人
     */
    @TableField(value = "created_by")
    private Long createdBy;

    /**
     * 创建时间
     */
    @TableField(value = "created_time")
    private Date createdTime;

    /**
     * 更新人
     */
    @TableField(value = "updated_by")
    private Long updatedBy;

    /**
     * 更新时间
     */
    @TableField(value = "updated_time")
    private Date updatedTime;

    /**
     * 冗余字段
     */
    @TableField(value = "def1")
    private String def1;

    /**
     * 冗余字段
     */
    @TableField(value = "def2")
    private String def2;

    /**
     * 冗余字段
     */
    @TableField(value = "def3")
    private String def3;

    /**
     * 冗余字段
     */
    @TableField(value = "def4")
    private String def4;

    /**
     * 冗余字段
     */
    @TableField(value = "def5")
    private Double def5;

    public static final String COL_ID = "id";

    public static final String COL_DR = "dr";

    public static final String COL_STOCK_TYPE = "stock_type";

    public static final String COL_GROUP_ID = "group_id";

    public static final String COL_COLOR_ID = "color_id";

    public static final String COL_ABLE_STOCK_QTY = "able_stock_qty";

    public static final String COL_USED_STOCK_QTY = "used_stock_qty";

    public static final String COL_WAIT_IN_STOCK_QTY = "wait_in_stock_qty";

    public static final String COL_WAIT_OUT_STOCK_QTY = "wait_out_stock_qty";

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
