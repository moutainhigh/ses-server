package com.redescooter.ses.service.hub.source.website.dm;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author chris
 * @since 2021-04-26
 */
@ApiModel(value = "产品报价表")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Accessors(chain = true)
@JsonInclude(value = Include.NON_NULL)
public class SiteProductPrice implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.UUID)
    private Long id;

    @ApiModelProperty(value = "逻辑删除")
    @TableLogic
    private Integer dr;

    @ApiModelProperty(value = "状态,1正常，-1失效")
    private Integer status;

    @ApiModelProperty(value = "产品型号Id")
    private Long productModelId;

    @ApiModelProperty(value = "状态,0全额付款，1分期付款")
    private Integer priceType;

    @ApiModelProperty(value = "分期付款时间数，单位month")
    private String installmentTime;

    @ApiModelProperty(value = "销售价格 浮点型价格")
    private BigDecimal price;

    @ApiModelProperty(value = "起步价")
    private BigDecimal startPrice;

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

    @ApiModelProperty(value = "预付定金")
    private BigDecimal prepaidDeposit;

    @ApiModelProperty(value = "优惠抵扣金额")
    private BigDecimal amountDiscount;

    @ApiModelProperty(value = "国家编码 当前销售国家")
    private String countryCode;

    @ApiModelProperty(value = "国家城市 当然销售国家的城市")
    private String countryCity;

    @ApiModelProperty(value = "国家语言 当前销售国家语言")
    private String countryLanguage;

    @ApiModelProperty(value = "是否同步")
    private Boolean synchronizeFlag;

    @ApiModelProperty(value = "乐观锁")
    private Integer revision;

    @ApiModelProperty(value = "创建人")
    private Long createdBy;

    @ApiModelProperty(value = "创建时间", hidden = true)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdTime;

    @ApiModelProperty(value = "更新人")
    private Long updatedBy;

    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatedTime;

    @ApiModelProperty(value = "冗余字段")
    private String def1;

    @ApiModelProperty(value = "冗余字段")
    private String def2;

    @ApiModelProperty(value = "冗余字段")
    private String def3;

    @ApiModelProperty(value = "冗余字段")
    private String def5;

    @ApiModelProperty(value = "冗余字段")
    private BigDecimal def6;

}