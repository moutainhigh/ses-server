package com.redescooter.ses.web.ros.dm;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
    * 入库记录表
    */
@Data
@TableName(value = "ope_wms_stock_record")
public class OpeWmsStockRecord implements Serializable {
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
     * 关联的库存表ID
     */
    @TableField(value = "relation_id")
    private Long relationId;

    /**
     * 入库类型，1：生产入库，2：返修入库，3：采购入库，5：退料入库，6：其他
     */
    @TableField(value = "in_wh_type")
    private Integer inWhType;

    /**
     * 入库数量
     */
    @TableField(value = "in_wh_qty")
    private Integer inWhQty;

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

    public static final String COL_RELATION_ID = "relation_id";

    public static final String COL_IN_WH_TYPE = "in_wh_type";

    public static final String COL_IN_WH_QTY = "in_wh_qty";

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
