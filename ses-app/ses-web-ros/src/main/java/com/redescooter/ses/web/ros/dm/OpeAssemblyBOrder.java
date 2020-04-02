package com.redescooter.ses.web.ros.dm;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@ApiModel(value = "com-redescooter-ses-web-ros-dm-OpeAssemblyBOrder")
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
    private Long userId;

    /**
     * 租户id
     */
    @TableField(value = "tenant_id")
    @ApiModelProperty(value = "租户id")
    private Long tenantId;

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
     * 组装单子单号
     */
    @TableField(value = "assembly_b_number")
    @ApiModelProperty(value = "组装单子单号")
    private String assemblyBNumber;

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
     * 已完成数
     */
    @TableField(value = "complete_qty")
    @ApiModelProperty(value = "已完成数")
    private Integer completeQty;

    /**
     * 组装总数量
     */
    @TableField(value = "assembly_qty")
    @ApiModelProperty(value = "组装总数量")
    private Integer assemblyQty;

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

    public static final String COL_ASSEMBLY_B_NUMBER = "assembly_b_number";

    public static final String COL_PRODUCT_NUMBER = "product_number";

    public static final String COL_EN_NAME = "en_name";

    public static final String COL_PRICE = "price";

    public static final String COL_COMPLETE_QTY = "complete_qty";

    public static final String COL_ASSEMBLY_QTY = "assembly_qty";

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