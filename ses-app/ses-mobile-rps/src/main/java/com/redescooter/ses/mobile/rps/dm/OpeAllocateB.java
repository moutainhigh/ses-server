package com.redescooter.ses.mobile.rps.dm;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 调拨单子表
 */
@ApiModel(value = "com-redescooter-ses-mobile-rps-dm-OpeAllocateB")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OpeAllocateB implements Serializable {
    public static final String COL_ID = "id";
    public static final String COL_DR = "dr";
    public static final String COL_USER_ID = "user_id";
    public static final String COL_TENANT_ID = "tenant_id";
    public static final String COL_ALLOCATE_ID = "allocate_id";
    public static final String COL_PART_ID = "part_id";
    public static final String COL_MATERIEL_PRODUCT_ID = "materiel_product_id";
    public static final String COL_MATERIEL_PRODUCT_TYPE = "materiel_product_type";
    public static final String COL_TOTAL = "total";
    public static final String COL_PREPARATION_WAIT_QTY = "preparation_wait_qty";
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
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private Long id;

    /**
     * 删除标识
     */
    @ApiModelProperty(value = "删除标识")
    private Integer dr;

    /**
     * userId
     */
    @ApiModelProperty(value = "userId")
    private Long userId;

    /**
     * 租户Id
     */
    @ApiModelProperty(value = "租户Id")
    private Long tenantId;

    /**
     * 调拨单Id
     */
    @ApiModelProperty(value = "调拨单Id")
    private Long allocateId;

    /**
     * 部件Id
     */
    @ApiModelProperty(value = "部件Id")
    private Long partId;

    /**
     * 产品物料id
     */
    @ApiModelProperty(value = "产品物料id")
    private Long materielProductId;

    /**
     * 产品物料类型
     */
    @ApiModelProperty(value = "产品物料类型")
    private String materielProductType;

    /**
     * 数量
     */
    @ApiModelProperty(value = "数量")
    private Integer total;

    /**
     * 待备料总数
     */
    @ApiModelProperty(value = "待备料总数")
    private Integer preparationWaitQty;

    /**
     * 乐观锁
     */
    @ApiModelProperty(value = "乐观锁")
    private Integer revision;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private Long createdBy;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createdTime;

    /**
     * 更新人
     */
    @ApiModelProperty(value = "更新人")
    private Long updatedBy;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private Date updatedTime;

    /**
     * 冗余字段
     */
    @ApiModelProperty(value = "冗余字段")
    private String def1;

    /**
     * 冗余字段
     */
    @ApiModelProperty(value = "冗余字段")
    private String def2;

    /**
     * 冗余字段
     */
    @ApiModelProperty(value = "冗余字段")
    private String def3;

    /**
     * 冗余字段
     */
    @ApiModelProperty(value = "冗余字段")
    private String def5;

    /**
     * 冗余字段
     */
    @ApiModelProperty(value = "冗余字段")
    private Double def6;

    private static final long serialVersionUID = 1L;

    public static OpeAllocateBBuilder builder() {
        return new OpeAllocateBBuilder();
    }
}