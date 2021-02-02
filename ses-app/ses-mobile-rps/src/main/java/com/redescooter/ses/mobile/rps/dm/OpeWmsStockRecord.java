package com.redescooter.ses.mobile.rps.dm;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @author assert
 * @date 2021/2/2 16:16
 */
@ApiModel(value = "com-redescooter-ses-mobile-rps-dm-OpeWmsStockRecord")
@Data
@TableName(value = "operation.ope_wms_stock_record")
public class OpeWmsStockRecord implements Serializable {
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
     * 关联的库存表ID
     */
    @TableField(value = "relation_id")
    @ApiModelProperty(value = "关联的库存表ID")
    private Long relationId;

    /**
     * 关联的单据类型，1:成品库车辆库存单，2:成品库组装件库存单，3:原料库部件库存单，4:不合格品库车辆库存单，5:不合格品库组装件库存单，6:不合格品库部件库存单，7:法国车辆库存，8:法国组装件库存，9:法国部件库存
     */
    @TableField(value = "relation_type")
    @ApiModelProperty(value = "关联的单据类型，1:成品库车辆库存单，2:成品库组装件库存单，3:原料库部件库存单，4:不合格品库车辆库存单，5:不合格品库组装件库存单，6:不合格品库部件库存单，7:法国车辆库存，8:法国组装件库存，9:法国部件库存")
    private Integer relationType;

    /**
     * 入库类型，1：生产入库，2：返修入库，3：采购入库，4：退料入库，5：其他，6：报废入库，7：调拨入库
     */
    @TableField(value = "in_wh_type")
    @ApiModelProperty(value = "入库类型，1：生产入库，2：返修入库，3：采购入库，4：退料入库，5：其他，6：报废入库，7：调拨入库")
    private Integer inWhType;

    /**
     * 入库数量
     */
    @TableField(value = "in_wh_qty")
    @ApiModelProperty(value = "入库数量")
    private Integer inWhQty;

    /**
     * 记录类型，1:入库，2:出库
     */
    @TableField(value = "record_type")
    @ApiModelProperty(value = "记录类型，1:入库，2:出库")
    private Integer recordType;

    /**
     * 仓库类型，1:中国仓库，2:法国仓库
     */
    @TableField(value = "stock_type")
    @ApiModelProperty(value = "仓库类型，1:中国仓库，2:法国仓库")
    private Integer stockType;

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
    private Double def5;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_DR = "dr";

    public static final String COL_RELATION_ID = "relation_id";

    public static final String COL_RELATION_TYPE = "relation_type";

    public static final String COL_IN_WH_TYPE = "in_wh_type";

    public static final String COL_IN_WH_QTY = "in_wh_qty";

    public static final String COL_RECORD_TYPE = "record_type";

    public static final String COL_STOCK_TYPE = "stock_type";

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