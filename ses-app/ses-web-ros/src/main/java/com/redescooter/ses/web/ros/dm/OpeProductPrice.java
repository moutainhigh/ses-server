package com.redescooter.ses.web.ros.dm;

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
 * 产品价格表
 */
@ApiModel(value = "com-redescooter-ses-web-ros-dm-OpeProductPrice")
@Data
@TableName(value = "ope_product_price")
public class OpeProductPrice implements Serializable {
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
     * 状态，0： Invalid失效:1：Effective有效:2：Cancel取消
     */
    @TableField(value = "`status`")
    @ApiModelProperty(value = "状态，0： Invalid失效:1：Effective有效:2：Cancel取消")
    private Integer status;

    /**
     * 价格 浮点型价格
     */
    @TableField(value = "price")
    @ApiModelProperty(value = "价格 浮点型价格")
    private BigDecimal price;

    /**
     * 价格类型，0：采购价，1：销售价
     */
    @TableField(value = "price_type")
    @ApiModelProperty(value = "价格类型，0：采购价，1：销售价")
    private Integer priceType;

    /**
     * 币种，0：人民币（¥），1：美元（$），2：欧元（€），3：英镑（￡）
     */
    @TableField(value = "currency_type")
    @ApiModelProperty(value = "币种，0：人民币（¥），1：美元（$），2：欧元（€），3：英镑（￡）")
    private Integer currencyType;

    /**
     * 标准货币 用户货币转换，1：人民币（¥），2：美元（$），3：欧元（€），4：英镑（￡）
     */
    @TableField(value = "standard_currency")
    @ApiModelProperty(value = "标准货币 用户货币转换，1：人民币（¥），2：美元（$），3：欧元（€），4：英镑（￡）")
    private Integer standardCurrency;

    /**
     * 汇率 用于汇率转换
     */
    @TableField(value = "exchange_rate")
    @ApiModelProperty(value = "汇率 用于汇率转换")
    private String exchangeRate;

    /**
     * 部品主键(采购价时关联的是部件id，销售价时关联的是销售产品的id)
     */
    @TableField(value = "product_id")
    @ApiModelProperty(value = "部品主键(采购价时关联的是部件id，销售价时关联的是销售产品的id)")
    private Long productId;

    /**
     * 类型，1：采购部件，2：整车，3：组装，4：销售部件
     */
    @TableField(value = "product_price_type")
    @ApiModelProperty(value = "类型，1：采购部件，2：整车，3：组装，4：销售部件")
    private Integer productPriceType;

    /**
     * 开始日期
     */
    @TableField(value = "begin_date")
    @ApiModelProperty(value = "开始日期")
    private Date beginDate;

    /**
     * 结束日期
     */
    @TableField(value = "end_date")
    @ApiModelProperty(value = "结束日期")
    private Date endDate;

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
    @TableField(value = "def4")
    @ApiModelProperty(value = "冗余字段")
    private String def4;

    /**
     * 冗余字段
     */
    @TableField(value = "def5")
    @ApiModelProperty(value = "冗余字段")
    private BigDecimal def5;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_DR = "dr";

    public static final String COL_TENANT_ID = "tenant_id";

    public static final String COL_USER_ID = "user_id";

    public static final String COL_STATUS = "status";

    public static final String COL_PRICE = "price";

    public static final String COL_PRICE_TYPE = "price_type";

    public static final String COL_CURRENCY_TYPE = "currency_type";

    public static final String COL_STANDARD_CURRENCY = "standard_currency";

    public static final String COL_EXCHANGE_RATE = "exchange_rate";

    public static final String COL_PRODUCT_ID = "product_id";

    public static final String COL_PRODUCT_PRICE_TYPE = "product_price_type";

    public static final String COL_BEGIN_DATE = "begin_date";

    public static final String COL_END_DATE = "end_date";

    public static final String COL_REVISION = "revision";

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
