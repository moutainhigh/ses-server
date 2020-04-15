package com.redescooter.ses.web.ros.dm;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value="com-redescooter-ses-web-ros-dm-OpePurchasProduct")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "ope_purchas_product")
public class OpePurchasProduct {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value="主键")
    private Long id;

    /**
     * 删除表示
     */
    @TableField(value = "dr")
    @ApiModelProperty(value="删除表示")
    private Integer dr;

    /**
     * 租户Id
     */
    @TableField(value = "tenant_id")
    @ApiModelProperty(value="租户Id")
    private Long tenantId;

    /**
     * 租户Id
     */
    @TableField(value = "user_id")
    @ApiModelProperty(value="租户Id")
    private Long userId;

    /**
     * 采购订单id
     */
    @TableField(value = "purchas_id")
    @ApiModelProperty(value="采购订单id")
    private Long purchasId;

    /**
     * 产品Id
     */
    @TableField(value = "product_id")
    @ApiModelProperty(value="产品Id")
    private Long productId;

    /**
     * qc质检类型（整车质检、部品质检）
     */
    @TableField(value = "id_class")
    @ApiModelProperty(value="qc质检类型（整车质检、部品质检）")
    private String idClass;

    /**
     * 产品类型
     */
    @TableField(value = "product_type")
    @ApiModelProperty(value="产品类型")
    private String productType;

    /**
     * 产品编号
     */
    @TableField(value = "product_num")
    @ApiModelProperty(value="产品编号")
    private String productNum;

    /**
     * 乐观锁
     */
    @TableField(value = "revision")
    @ApiModelProperty(value="乐观锁")
    private Integer revision;

    /**
     * 创建人
     */
    @TableField(value = "created_by")
    @ApiModelProperty(value="创建人")
    private Long createdBy;

    /**
     * 创建时间
     */
    @TableField(value = "created_time")
    @ApiModelProperty(value="创建时间")
    private Date createdTime;

    /**
     * 更新人
     */
    @TableField(value = "updated_by")
    @ApiModelProperty(value="更新人")
    private Long updatedBy;

    /**
     * 更新时间
     */
    @TableField(value = "updated_time")
    @ApiModelProperty(value="更新时间")
    private Date updatedTime;

    public static final String COL_ID = "id";

    public static final String COL_DR = "dr";

    public static final String COL_TENANT_ID = "tenant_id";

    public static final String COL_USER_ID = "user_id";

    public static final String COL_PURCHAS_ID = "purchas_id";

    public static final String COL_PRODUCT_ID = "product_id";

    public static final String COL_ID_CLASS = "id_class";

    public static final String COL_PRODUCT_TYPE = "product_type";

    public static final String COL_PRODUCT_NUM = "product_num";

    public static final String COL_REVISION = "revision";

    public static final String COL_CREATED_BY = "created_by";

    public static final String COL_CREATED_TIME = "created_time";

    public static final String COL_UPDATED_BY = "updated_by";

    public static final String COL_UPDATED_TIME = "updated_time";
}