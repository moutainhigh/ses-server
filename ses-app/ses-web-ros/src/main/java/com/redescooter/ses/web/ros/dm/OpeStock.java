package com.redescooter.ses.web.ros.dm;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 库存总表
 */
@ApiModel(value = "com-redescooter-ses-web-ros-dm-OpeStock")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "ope_stock")
public class OpeStock implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value = "主键")
    private Long id;

    @TableField(value = "dr")
    @ApiModelProperty(value = "")
    private Integer dr;

    @TableField(value = "user_id")
    @ApiModelProperty(value = "")
    private Long userId;

    @TableField(value = "tenant_id")
    @ApiModelProperty(value = "")
    private Long tenantId;

    /**
     * 所属仓库Id
     */
    @TableField(value = "whse_id")
    @ApiModelProperty(value = "所属仓库Id")
    private Long whseId;

    /**
     * 入库总数
     */
    @TableField(value = "int_total")
    @ApiModelProperty(value = "入库总数")
    private Integer intTotal;

    /**
     * 出库总数
     */
    @TableField(value = "out_total")
    @ApiModelProperty(value = "出库总数")
    private Integer outTotal;

    /**
     * 剩余库存
     */
    @TableField(value = "available_total")
    @ApiModelProperty(value = "剩余库存")
    private Integer availableTotal;

    /**
     * 破损总数
     */
    @TableField(value = "worn_total")
    @ApiModelProperty(value = "破损总数")
    private Integer wornTotal;

    /**
     * 锁定库存
     */
    @TableField(value = "lock_total")
    @ApiModelProperty(value = "锁定库存")
    private Integer lockTotal;

    /**
     * 待生产
     */
    @TableField(value = "wait_product_total")
    @ApiModelProperty(value = "待生产")
    private Integer waitProductTotal;

    /**
     * 待入库
     */
    @TableField(value = "wait_stored_total")
    @ApiModelProperty(value = "待入库")
    private Integer waitStoredTotal;

    /**
     * 所属物料产品Id
     */
    @TableField(value = "materiel_product_id")
    @ApiModelProperty(value = "所属物料产品Id")
    private Long materielProductId;

    /**
     * 所属物料产品类型
     */
    @TableField(value = "materiel_product_type")
    @ApiModelProperty(value = "所属物料产品类型")
    private String materielProductType;

    /**
     * 所属物料名称
     */
    @TableField(value = "materiel_product_name")
    @ApiModelProperty(value = "所属物料名称")
    private String materielProductName;

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

    public static final String COL_USER_ID = "user_id";

    public static final String COL_TENANT_ID = "tenant_id";

    public static final String COL_WHSE_ID = "whse_id";

    public static final String COL_INT_TOTAL = "int_total";

    public static final String COL_OUT_TOTAL = "out_total";

    public static final String COL_AVAILABLE_TOTAL = "available_total";

    public static final String COL_WORN_TOTAL = "worn_total";

    public static final String COL_LOCK_TOTAL = "lock_total";

    public static final String COL_WAIT_PRODUCT_TOTAL = "wait_product_total";

    public static final String COL_WAIT_STORED_TOTAL = "wait_stored_total";

    public static final String COL_MATERIEL_PRODUCT_ID = "materiel_product_id";

    public static final String COL_MATERIEL_PRODUCT_TYPE = "materiel_product_type";

    public static final String COL_MATERIEL_PRODUCT_NAME = "materiel_product_name";

    public static final String COL_REVISION = "revision";

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