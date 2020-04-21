package com.redescooter.ses.mobile.rps.dm;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value = "com-redescooter-ses-mobile-rps-dm-OpeAssemblyBOrder")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "ope_assembly_b_order")
public class OpeAssemblyBOrder implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value = "主键")
    private Long id;

    /**
     * 删除标识
     */
    @TableField(value = "dr")
    @ApiModelProperty(value = "删除标识")
    @TableLogic
    private Integer dr;

    /**
     * 状态
     */
    @TableField(value = "status")
    @ApiModelProperty(value = "状态")
    private String status;

    /**
     * 用户id
     */
    @TableField(value = "user_id")
    @ApiModelProperty(value = "用户id")
    private Integer userId;

    /**
     * 租户id
     */
    @TableField(value = "tenant_id")
    @ApiModelProperty(value = "租户id")
    private Integer tenantId;

    /**
     * 组装单id
     */
    @TableField(value = "assembly_id")
    @ApiModelProperty(value = "组装单id")
    private Long assemblyId;

    /**
     * 产品id
     */
    @TableField(value = "product_id")
    @ApiModelProperty(value = "产品id")
    private Long productId;

    /**
     * 子表编号
     */
    @TableField(value = "assemblyb_number")
    @ApiModelProperty(value = "子表编号")
    private String assemblybNumber;

    /**
     * 产品编码
     */
    @TableField(value = "product_number")
    @ApiModelProperty(value = "产品编码")
    private String productNumber;

    /**
     * 产品英文名
     */
    @TableField(value = "en_name")
    @ApiModelProperty(value = "产品英文名")
    private String enName;

    /**
     * 产品单价
     */
    @TableField(value = "price")
    @ApiModelProperty(value = "产品单价")
    private BigDecimal price;

    /**
     * 待组装数量
     */
    @TableField(value = "wait_assembly_qty")
    @ApiModelProperty(value = "待组装数量")
    private Integer waitAssemblyQty;

    /**
     * 待入库数
     */
    @TableField(value = "in_wait_wh_qty")
    @ApiModelProperty(value = "待入库数")
    private Integer inWaitWhQty;

    /**
     * 待质检数
     */
    @TableField(value = "lave_wait_qc_qty")
    @ApiModelProperty(value = "待质检数")
    private Integer laveWaitQcQty;

    /**
     * 组装总数量
     */
    @TableField(value = "assembly_qty")
    @ApiModelProperty(value = "组装总数量")
    private Integer assemblyQty;

    /**
     * 完成组装数
     */
    @TableField(value = "complete_qty")
    @ApiModelProperty(value = "完成组装数")
    private Integer completeQty;

    /**
     * 乐观锁
     */
    @TableField(value = "revision")
    @ApiModelProperty(value = "乐观锁")
    private Integer revision;

    /**
     * 创建表
     */
    @TableField(value = "created_by")
    @ApiModelProperty(value = "创建表")
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

    public static final String COL_STATUS = "status";

    public static final String COL_USER_ID = "user_id";

    public static final String COL_TENANT_ID = "tenant_id";

    public static final String COL_ASSEMBLY_ID = "assembly_id";

    public static final String COL_PRODUCT_ID = "product_id";

    public static final String COL_ASSEMBLYB_NUMBER = "assemblyb_number";

    public static final String COL_PRODUCT_NUMBER = "product_number";

    public static final String COL_EN_NAME = "en_name";

    public static final String COL_PRICE = "price";

    public static final String COL_WAIT_ASSEMBLY_QTY = "wait_assembly_qty";

    public static final String COL_IN_WAIT_WH_QTY = "in_wait_wh_qty";

    public static final String COL_LAVE_WAIT_QC_QTY = "lave_wait_qc_qty";

    public static final String COL_ASSEMBLY_QTY = "assembly_qty";

    public static final String COL_COMPLETE_QTY = "complete_qty";

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

    public static OpeAssemblyBOrderBuilder builder() {
        return new OpeAssemblyBOrderBuilder();
    }
}