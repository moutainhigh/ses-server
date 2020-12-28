package com.redescooter.ses.web.ros.dm;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
    * 成品库组装件库存表
    */
@Data
@TableName(value = "ope_wms_combin_stock")
public class OpeWmsCombinStock implements Serializable {
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
     * 组装件编号
     */
    @TableField(value = "combin_no")
    private String combinNo;

    /**
     * 中文名称
     */
    @TableField(value = "cn_name")
    private String cnName;

    /**
     * 英文名称
     */
    @TableField(value = "en_name")
    private String enName;

    /**
     * 法文名称
     */
    @TableField(value = "fr_name")
    private String frName;

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

    public static final String COL_COMBIN_NO = "combin_no";

    public static final String COL_CN_NAME = "cn_name";

    public static final String COL_EN_NAME = "en_name";

    public static final String COL_FR_NAME = "fr_name";

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
