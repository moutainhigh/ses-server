package com.redescooter.ses.api.hub.vo.website;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description
 * @Author Chris
 * @Date 2021/3/16 17:19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Accessors(chain = true)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class SyncSalePartsDataEnter extends GeneralEnter {

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "类型 1：Parts，2：Accessory，3：Battery，4：Scooter，5：Combination 6：ECU")
    private Integer partsType;

    @ApiModelProperty(value = "部品号")
    private String partsNumber;

    @ApiModelProperty(value = "中文名称")
    private String cnName;

    @ApiModelProperty(value = "法文名称")
    private String frName;

    @ApiModelProperty(value = "英文名称")
    private String enName;

    @ApiModelProperty(value = "部品数量")
    private Integer partsQty;

    @ApiModelProperty(value = "销售价格 浮点型价格")
    private BigDecimal price;

    @ApiModelProperty(value = "图片")
    private String picture;

    @ApiModelProperty(value = "规格")
    private String specs;

    @ApiModelProperty(value = "采购来源")
    private String sources;

    @ApiModelProperty(value = "生效时间 默认当前生效")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date effectiveTime;

    @ApiModelProperty(value = "货币类型 如英镑，美元，人民币")
    private String currencyType;

    @ApiModelProperty(value = "货币单位 如¥，$，€，	￡")
    private String currencyUnit;

    @ApiModelProperty(value = "标准货币 用户货币转换")
    private String standardCurrency;

    @ApiModelProperty(value = "优惠抵扣金额")
    private BigDecimal amountDiscount;

    @ApiModelProperty(value = "国家编码 当前销售国家")
    private String countryCode;

    @ApiModelProperty(value = "国家城市 当然销售国家的城市")
    private String countryCity;

    @ApiModelProperty(value = "国家语言 当前销售国家语言")
    private String countryLanguage;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "是否有唯一编码")
    private Boolean idClass;

    @ApiModelProperty(value = "是否同步")
    private Boolean synchronizeFlag;

    @ApiModelProperty(value = "乐观锁")
    private Integer revision;

    @ApiModelProperty(value = "创建人")
    private Long createdBy;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdTime;

    @ApiModelProperty(value = "更新人")
    private Long updatedBy;

    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatedTime;

}
