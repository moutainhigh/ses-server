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

@ApiModel(value = "com-redescooter-ses-web-ros-dm-OpeRegionalPriceSheet")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "ope_regional_price_sheet")
public class OpeRegionalPriceSheet {
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
     * 状态 状态
     */
    @TableField(value = "status")
    @ApiModelProperty(value = "状态 状态")
    private String status;

    /**
     * 产品Id
     */
    @TableField(value = "parts_product_id")
    @ApiModelProperty(value = "产品Id")
    private Long partsProductId;

    /**
     * 价格类型，1.销售价，2采购价，3其他价格
     */
    @TableField(value = "price_type")
    @ApiModelProperty(value = "价格类型，1.销售价，2采购价，3其他价格")
    private String priceType;

    /**
     * 销售价格 浮点型价格
     */
    @TableField(value = "sales_price")
    @ApiModelProperty(value = "销售价格 浮点型价格")
    private BigDecimal salesPrice;

    /**
     * 生效时间 默认当前生效
     */
    @TableField(value = "effective_time")
    @ApiModelProperty(value = "生效时间 默认当前生效")
    private Date effectiveTime;

    /**
     * 有效标识 标识当前区域报价是否生效
     */
    @TableField(value = "valid_flag")
    @ApiModelProperty(value = "有效标识 标识当前区域报价是否生效")
    private String validFlag;

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
     * 汇率 用于汇率转换
     */
    @TableField(value = "exchange_rate")
    @ApiModelProperty(value = "汇率 用于汇率转换")
    private String exchangeRate;

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

    public static final String COL_ID = "id";

    public static final String COL_DR = "dr";

    public static final String COL_TENANT_ID = "tenant_id";

    public static final String COL_USER_ID = "user_id";

    public static final String COL_STATUS = "status";

    public static final String COL_PARTS_PRODUCT_ID = "parts_product_id";

    public static final String COL_PRICE_TYPE = "price_type";

    public static final String COL_SALES_PRICE = "sales_price";

    public static final String COL_EFFECTIVE_TIME = "effective_time";

    public static final String COL_VALID_FLAG = "valid_flag";

    public static final String COL_CURRENCY_TYPE = "currency_type";

    public static final String COL_CURRENCY_UNIT = "currency_unit";

    public static final String COL_STANDARD_CURRENCY = "standard_currency";

    public static final String COL_EXCHANGE_RATE = "exchange_rate";

    public static final String COL_COUNTRY_CODE = "country_code";

    public static final String COL_COUNTRY_CITY = "country_city";

    public static final String COL_COUNTRY_LANGUAGE = "country_language";

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

    public static OpeRegionalPriceSheetBuilder builder() {
        return new OpeRegionalPriceSheetBuilder();
    }
}