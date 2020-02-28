package com.redescooter.ses.web.ros.dm;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value = "com-redescooter-ses-web-ros-dm-OpePriceSheet")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "ope_price_sheet")
public class OpePriceSheet implements Serializable {
    /**
     * 主键 主键
     */
    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value = "主键 主键")
    private Long id;

    /**
     * 逻辑删除 逻辑删除
     */
    @TableField(value = "dr")
    @TableLogic
    @ApiModelProperty(value = "逻辑删除 逻辑删除")
    private Integer dr;

    /**
     * 租户ID 租户ID
     */
    @TableField(value = "tenant_id")
    @ApiModelProperty(value = "租户ID 租户ID")
    private Long tenantId;

    /**
     * 用户ID 用户ID
     */
    @TableField(value = "user_id")
    @ApiModelProperty(value = "用户ID 用户ID")
    private Long userId;

    /**
     * 状态 状态
     */
    @TableField(value = "status")
    @ApiModelProperty(value = "状态 状态")
    private String status;

    /**
     * 价格 浮点型价格
     */
    @TableField(value = "price")
    @ApiModelProperty(value = "价格 浮点型价格")
    private BigDecimal price;

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
     * 部品主键 用于关联部品
     */
    @TableField(value = "parts_id")
    @ApiModelProperty(value = "部品主键 用于关联部品")
    private Long partsId;

    /**
     * 乐观锁 乐观锁
     */
    @TableField(value = "revision")
    @ApiModelProperty(value = "乐观锁 乐观锁")
    private Integer revision;

    /**
     * 创建人 创建人
     */
    @TableField(value = "created_by")
    @ApiModelProperty(value = "创建人 创建人")
    private Long createdBy;

    /**
     * 创建时间 创建时间
     */
    @TableField(value = "created_time")
    @ApiModelProperty(value = "创建时间 创建时间")
    private Date createdTime;

    /**
     * 更新人 更新人
     */
    @TableField(value = "updated_by")
    @ApiModelProperty(value = "更新人 更新人")
    private Long updatedBy;

    /**
     * 更新时间 更新时间
     */
    @TableField(value = "updated_time")
    @ApiModelProperty(value = "更新时间 更新时间")
    private Date updatedTime;

    /**
     * 冗余字段 冗余字段
     */
    @TableField(value = "def1")
    @ApiModelProperty(value = "冗余字段 冗余字段")
    private String def1;

    /**
     * 冗余字段 冗余字段
     */
    @TableField(value = "def2")
    @ApiModelProperty(value = "冗余字段 冗余字段")
    private String def2;

    /**
     * 冗余字段 冗余字段
     */
    @TableField(value = "def3")
    @ApiModelProperty(value = "冗余字段 冗余字段")
    private String def3;

    /**
     * 冗余字段 冗余字段
     */
    @TableField(value = "def5")
    @ApiModelProperty(value = "冗余字段 冗余字段")
    private String def5;

    /**
     * 冗余字段 冗余字段
     */
    @TableField(value = "def6")
    @ApiModelProperty(value = "冗余字段 冗余字段")
    private BigDecimal def6;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_DR = "dr";

    public static final String COL_TENANT_ID = "tenant_id";

    public static final String COL_USER_ID = "user_id";

    public static final String COL_STATUS = "status";

    public static final String COL_PRICE = "price";

    public static final String COL_CURRENCY_TYPE = "currency_type";

    public static final String COL_CURRENCY_UNIT = "currency_unit";

    public static final String COL_STANDARD_CURRENCY = "standard_currency";

    public static final String COL_EXCHANGE_RATE = "exchange_rate";

    public static final String COL_PARTS_ID = "parts_id";

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

    public static OpePriceSheetBuilder builder() {
        return new OpePriceSheetBuilder();
    }
}