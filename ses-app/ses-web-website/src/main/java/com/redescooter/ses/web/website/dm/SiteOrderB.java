package com.redescooter.ses.web.website.dm;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
    * 销售订单子表
    */
@ApiModel(value="com-redescooter-ses-web-website-dm-SiteOrderB")
@Data
@TableName(value = "site_order_b")
public class SiteOrderB implements Serializable {
    /**
     * 主建
     */
    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value="主建")
    private Long id;

    /**
     * 逻辑删除标识 0正常 1删除
     */
    @TableField(value = "dr")
    @ApiModelProperty(value="逻辑删除标识 0正常 1删除")
    private Integer dr;

    /**
     * 销售订单Id
     */
    @TableField(value = "order_id")
    @ApiModelProperty(value="销售订单Id")
    private Long orderId;

    /**
     * 产品Id
     */
    @TableField(value = "product_id")
    @ApiModelProperty(value="产品Id")
    private Long productId;

    /**
     * 配件ID
     */
    @TableField(value = "parts_id")
    @ApiModelProperty(value="配件ID")
    private BigDecimal partsId;

    /**
     * 配件数量
     */
    @TableField(value = "parts_qty")
    @ApiModelProperty(value="配件数量")
    private Integer partsQty;

    /**
     * 配件单价
     */
    @TableField(value = "parts_price")
    @ApiModelProperty(value="配件单价")
    private BigDecimal partsPrice;

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

    /**
     * 冗余字段
     */
    @TableField(value = "def1")
    @ApiModelProperty(value="冗余字段")
    private String def1;

    /**
     * 冗余字段
     */
    @TableField(value = "def2")
    @ApiModelProperty(value="冗余字段")
    private String def2;

    /**
     * 冗余字段
     */
    @TableField(value = "def3")
    @ApiModelProperty(value="冗余字段")
    private String def3;

    /**
     * 冗余字段
     */
    @TableField(value = "def5")
    @ApiModelProperty(value="冗余字段")
    private String def5;

    /**
     * 冗余字段
     */
    @TableField(value = "def6")
    @ApiModelProperty(value="冗余字段")
    private Double def6;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_DR = "dr";

    public static final String COL_ORDER_ID = "order_id";

    public static final String COL_PRODUCT_ID = "product_id";

    public static final String COL_PARTS_ID = "parts_id";

    public static final String COL_PARTS_QTY = "parts_qty";

    public static final String COL_PARTS_PRICE = "parts_price";

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