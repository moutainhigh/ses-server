package com.redescooter.ses.web.ros.dm;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value = "com-redescooter-ses-web-ros-dm-OpePartsDraft")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "ope_parts_draft")
public class OpePartsDraft {
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
    @TableLogic
    private Integer dr;

    /**
     * 租户ID
     */
    @TableField(value = "tenant_id")
    @ApiModelProperty(value = "租户ID")
    private Long tenantId;

    /**
     * 用户ID
     */
    @TableField(value = "user_id")
    @ApiModelProperty(value = "用户ID")
    private Long userId;

    /**
     * 导入批次号
     */
    @TableField(value = "import_lot")
    @ApiModelProperty(value = "导入批次号")
    private String importLot;

    /**
     * 状态
     */
    @TableField(value = "status")
    @ApiModelProperty(value = "状态")
    private String status;

    /**
     * 类型,全部类型AllType，零部件Parts，配件Accessory，电池Battery
     */
    @TableField(value = "parts_type")
    @ApiModelProperty(value = "类型,全部类型AllType，零部件Parts，配件Accessory，电池Battery")
    private String partsType;

    /**
     * 项目区域，全部项目区域AllSEC，F04等该部件在车体什么位置，分类查询，数据来源为数据库设定。
     */
    @TableField(value = "sec")
    @ApiModelProperty(value = "项目区域，全部项目区域AllSEC，F04等该部件在车体什么位置，分类查询，数据来源为数据库设定。")
    private String sec;

    /**
     * 部品号
     */
    @TableField(value = "parts_number")
    @ApiModelProperty(value = "部品号")
    private String partsNumber;

    /**
     * 中文名称
     */
    @TableField(value = "cn_name")
    @ApiModelProperty(value = "中文名称")
    private String cnName;

    /**
     * 法文名称
     */
    @TableField(value = "fr_name")
    @ApiModelProperty(value = "法文名称")
    private String frName;

    /**
     * 英文名称
     */
    @TableField(value = "en_name")
    @ApiModelProperty(value = "英文名称")
    private String enName;

    /**
     * 是否可销售 是否可销售,0:SC仅可采购，1:SSC可销售可采购
     */
    @TableField(value = "sn_class")
    @ApiModelProperty(value = "是否可销售 是否可销售,0:SC仅可采购，1:SSC可销售可采购")
    private String snClass;

    /**
     * 部品数量
     */
    @TableField(value = "parts_qty")
    @ApiModelProperty(value = "部品数量")
    private Integer partsQty;

    /**
     * 生产周期
     */
    @TableField(value = "production_cycle")
    @ApiModelProperty(value = "生产周期")
    private String productionCycle;

    /**
     * 成本
     */
    @TableField(value = "cost")
    @ApiModelProperty(value = "成本")
    private String cost;

    /**
     * 供应商
     */
    @TableField(value = "supplier_id")
    @ApiModelProperty(value = "供应商")
    private Long supplierId;

    /**
     * 图纸
     */
    @TableField(value = "dwg")
    @ApiModelProperty(value = "图纸")
    private String dwg;

    /**
     * 备注
     */
    @TableField(value = "note")
    @ApiModelProperty(value = "备注")
    private String note;

    /**
     * 乐观锁
     */
    @TableField(value = "revision")
    @ApiModelProperty(value = "乐观锁")
    private Integer revision;

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
     * 是否有唯一编码
     */
    @TableField(value = "id_class")
    @ApiModelProperty(value = "是否有唯一编码")
    private Boolean idClass;

    /**
     * 是否信息完善
     */
    @TableField(value = "perfect_flag")
    @ApiModelProperty(value = "是否信息完善")
    private Boolean perfectFlag;

    /**
     * 是否同步，只有在信息完善的前提下可以进行同步操作
     */
    @TableField(value = "synchronize_flag")
    @ApiModelProperty(value = "是否同步，只有在信息完善的前提下可以进行同步操作")
    private Boolean synchronizeFlag;

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
    private BigDecimal def6;

    public static final String COL_ID = "id";

    public static final String COL_DR = "dr";

    public static final String COL_TENANT_ID = "tenant_id";

    public static final String COL_USER_ID = "user_id";

    public static final String COL_IMPORT_LOT = "import_lot";

    public static final String COL_STATUS = "status";

    public static final String COL_PARTS_TYPE = "parts_type";

    public static final String COL_SEC = "sec";

    public static final String COL_PARTS_NUMBER = "parts_number";

    public static final String COL_CN_NAME = "cn_name";

    public static final String COL_FR_NAME = "fr_name";

    public static final String COL_EN_NAME = "en_name";

    public static final String COL_SN_CLASS = "sn_class";

    public static final String COL_PARTS_QTY = "parts_qty";

    public static final String COL_PRODUCTION_CYCLE = "production_cycle";

    public static final String COL_COST = "cost";

    public static final String COL_SUPPLIER_ID = "supplier_id";

    public static final String COL_DWG = "dwg";

    public static final String COL_NOTE = "note";

    public static final String COL_REVISION = "revision";

    public static final String COL_CREATED_BY = "created_by";

    public static final String COL_CREATED_TIME = "created_time";

    public static final String COL_UPDATED_BY = "updated_by";

    public static final String COL_UPDATED_TIME = "updated_time";

    public static final String COL_ID_CLASS = "id_class";

    public static final String COL_PERFECT_FLAG = "perfect_flag";

    public static final String COL_SYNCHRONIZE_FLAG = "synchronize_flag";

    public static final String COL_DEF1 = "def1";

    public static final String COL_DEF2 = "def2";

    public static final String COL_DEF3 = "def3";

    public static final String COL_DEF5 = "def5";

    public static final String COL_DEF6 = "def6";

    public static OpePartsDraftBuilder builder() {
        return new OpePartsDraftBuilder();
    }
}