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
 * 产品报价表
 */
@ApiModel(value = "com-redescooter-ses-web-website-dm-SiteProductPrice")
@Data
@TableName(value = "site_product_price")
public class SiteProductPrice implements Serializable {
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
     * 状态,1正常，-1失效
     */
    @TableField(value = "`status`")
    @ApiModelProperty(value = "状态,1正常，-1失效")
    private String status;

    /**
     * 产品型号Id
     */
    @TableField(value = "product_model_id")
    @ApiModelProperty(value = "产品型号Id")
    private Long productModelId;

    /**
     * 状态,0全额付款，1分期付款
     */
    @TableField(value = "price_type")
    @ApiModelProperty(value = "状态,0全额付款，1分期付款")
    private String priceType;

    /**
     * 分期付款时间数，单位month
     */
    @TableField(value = "installment_time")
    @ApiModelProperty(value = "分期付款时间数，单位month")
    private String installmentTime;

    /**
     * 销售价格 浮点型价格
     */
    @TableField(value = "price")
    @ApiModelProperty(value = "销售价格 浮点型价格")
    private BigDecimal price;

    /**
     * 起步价
     */
    @TableField(value = "start_price")
    @ApiModelProperty(value = "起步价")
    private BigDecimal startPrice;

    /**
     * 生效时间 默认当前生效
     */
    @TableField(value = "effective_time")
    @ApiModelProperty(value = "生效时间 默认当前生效")
    private Date effectiveTime;

    /**
     * 货币类型 如英镑，美元，人民币
     */
    @TableField(value = "currency_type")
    @ApiModelProperty(value = "货币类型 如英镑，美元，人民币")
    private String currencyType;

    /**
     * 货币单位 如¥，$，€，	￡
     */
    @TableField(value = "currency_unit")
    @ApiModelProperty(value = "货币单位 如¥，$，€，	￡")
    private String currencyUnit;

    /**
     * 标准货币 用户货币转换
     */
    @TableField(value = "standard_currency")
    @ApiModelProperty(value = "标准货币 用户货币转换")
    private String standardCurrency;

    /**
     * 预付定金
     */
    @TableField(value = "prepaid_deposit")
    @ApiModelProperty(value = "预付定金")
    private BigDecimal prepaidDeposit;

    /**
     * 优惠抵扣金额
     */
    @TableField(value = "amount_discount")
    @ApiModelProperty(value = "优惠抵扣金额")
    private BigDecimal amountDiscount;

    /**
     * 国家编码 当前销售国家
     */
    @TableField(value = "country_code")
    @ApiModelProperty(value = "国家编码 当前销售国家")
    private String countryCode;

    /**
     * 国家城市 当然销售国家的城市
     */
    @TableField(value = "country_city")
    @ApiModelProperty(value = "国家城市 当然销售国家的城市")
    private String countryCity;

    /**
     * 国家语言 当前销售国家语言
     */
    @TableField(value = "country_language")
    @ApiModelProperty(value = "国家语言 当前销售国家语言")
    private String countryLanguage;

    /**
     * 是否同步
     */
    @TableField(value = "synchronize_flag")
    @ApiModelProperty(value = "是否同步")
    private Boolean synchronizeFlag;

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
    private BigDecimal def6;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_DR = "dr";

    public static final String COL_STATUS = "status";

    public static final String COL_PRODUCT_MODEL_ID = "product_model_id";

    public static final String COL_PRICE_TYPE = "price_type";

    public static final String COL_INSTALLMENT_TIME = "installment_time";

    public static final String COL_PRICE = "price";

    public static final String COL_START_PRICE = "start_price";

    public static final String COL_EFFECTIVE_TIME = "effective_time";

    public static final String COL_CURRENCY_TYPE = "currency_type";

    public static final String COL_CURRENCY_UNIT = "currency_unit";

    public static final String COL_STANDARD_CURRENCY = "standard_currency";

    public static final String COL_PREPAID_DEPOSIT = "prepaid_deposit";

    public static final String COL_AMOUNT_DISCOUNT = "amount_discount";

    public static final String COL_COUNTRY_CODE = "country_code";

    public static final String COL_COUNTRY_CITY = "country_city";

    public static final String COL_COUNTRY_LANGUAGE = "country_language";

    public static final String COL_SYNCHRONIZE_FLAG = "synchronize_flag";

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