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
 * @date 2021/2/2 17:13
 */
@ApiModel(value = "com-redescooter-ses-mobile-rps-dm-OpeWmsCombinStock")
@Data
@TableName(value = "operation.ope_wms_combin_stock")
public class OpeWmsCombinStock implements Serializable {
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
     * 仓库类型，1:中国仓库，2:法国仓库
     */
    @TableField(value = "stock_type")
    @ApiModelProperty(value = "仓库类型，1:中国仓库，2:法国仓库")
    private Integer stockType;

    /**
     * 组装件id
     */
    @TableField(value = "production_combin_bom_id")
    @ApiModelProperty(value = "组装件id")
    private Long productionCombinBomId;

    /**
     * 组装件编号
     */
    @TableField(value = "combin_no")
    @ApiModelProperty(value = "组装件编号")
    private String combinNo;

    /**
     * 中文名称
     */
    @TableField(value = "cn_name")
    @ApiModelProperty(value = "中文名称")
    private String cnName;

    /**
     * 英文名称
     */
    @TableField(value = "en_name")
    @ApiModelProperty(value = "英文名称")
    private String enName;

    /**
     * 法文名称
     */
    @TableField(value = "fr_name")
    @ApiModelProperty(value = "法文名称")
    private String frName;

    /**
     * 可用库存数量
     */
    @TableField(value = "able_stock_qty")
    @ApiModelProperty(value = "可用库存数量")
    private Integer ableStockQty;

    /**
     * 已用库存数量
     */
    @TableField(value = "used_stock_qty")
    @ApiModelProperty(value = "已用库存数量")
    private Integer usedStockQty;

    /**
     * 待入库数量
     */
    @TableField(value = "wait_in_stock_qty")
    @ApiModelProperty(value = "待入库数量")
    private Integer waitInStockQty;

    /**
     * 待出库数量
     */
    @TableField(value = "wait_out_stock_qty")
    @ApiModelProperty(value = "待出库数量")
    private Integer waitOutStockQty;

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

    public static final String COL_STOCK_TYPE = "stock_type";

    public static final String COL_PRODUCTION_COMBIN_BOM_ID = "production_combin_bom_id";

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