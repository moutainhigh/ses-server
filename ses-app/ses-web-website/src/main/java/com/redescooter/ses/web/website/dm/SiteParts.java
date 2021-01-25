package com.redescooter.ses.web.website.dm;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 配件表
 */
@ApiModel(value = "com-redescooter-ses-web-website-dm-SiteParts")
@Data
@TableName(value = "site_parts")
public class SiteParts implements Serializable {
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
     * 状态
     */
    @TableField(value = "`status`")
    @ApiModelProperty(value = "状态")
    private Integer status;

    /**
     * 类型,全部类型AllType，零部件Parts，配件Accessory，电池Battery
     */
    @TableField(value = "parts_type")
    @ApiModelProperty(value = "类型,全部类型AllType，零部件Parts，配件Accessory，电池Battery")
    private Integer partsType;

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
     * 部品数量
     */
    @TableField(value = "parts_qty")
    @ApiModelProperty(value = "部品数量")
    private Integer partsQty;

    /**
     * 销售价格 浮点型价格
     */
    @TableField(value = "price")
    @ApiModelProperty(value = "销售价格 浮点型价格")
    private BigDecimal price;

    /**
     * 图片
     */
    @TableField(value = "picture")
    @ApiModelProperty(value = "图片")
    private String picture;

    /**
     * 规格
     */
    @TableField(value = "specs")
    @ApiModelProperty(value = "规格")
    private String specs;

    /**
     * 采购来源
     */
    @TableField(value = "sources")
    @ApiModelProperty(value = "采购来源")
    private String sources;

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
     * 备注
     */
    @TableField(value = "remark")
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 是否有唯一编码
     */
    @TableField(value = "id_class")
    @ApiModelProperty(value = "是否有唯一编码")
    private Boolean idClass;

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

    public static final String COL_PARTS_TYPE = "parts_type";

    public static final String COL_PARTS_NUMBER = "parts_number";

    public static final String COL_CN_NAME = "cn_name";

    public static final String COL_FR_NAME = "fr_name";

    public static final String COL_EN_NAME = "en_name";

    public static final String COL_PARTS_QTY = "parts_qty";

    public static final String COL_PRICE = "price";

    public static final String COL_PICTURE = "picture";

    public static final String COL_SPECS = "specs";

    public static final String COL_SOURCES = "sources";

    public static final String COL_EFFECTIVE_TIME = "effective_time";

    public static final String COL_CURRENCY_TYPE = "currency_type";

    public static final String COL_CURRENCY_UNIT = "currency_unit";

    public static final String COL_STANDARD_CURRENCY = "standard_currency";

    public static final String COL_AMOUNT_DISCOUNT = "amount_discount";

    public static final String COL_COUNTRY_CODE = "country_code";

    public static final String COL_COUNTRY_CITY = "country_city";

    public static final String COL_COUNTRY_LANGUAGE = "country_language";

    public static final String COL_REMARK = "remark";

    public static final String COL_ID_CLASS = "id_class";

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
