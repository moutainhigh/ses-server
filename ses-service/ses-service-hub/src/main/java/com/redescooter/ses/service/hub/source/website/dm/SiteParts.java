package com.redescooter.ses.service.hub.source.website.dm;

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

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author chris
 * @since 2021-03-16
 */
@ApiModel(value = "配件表")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Accessors(chain = true)
@JsonInclude(value = Include.NON_NULL)
public class SiteParts {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id")
    private Long id;

    @ApiModelProperty(value = "逻辑删除")
    @TableLogic
    private Integer dr;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "类型,全部类型AllType，零部件Parts，配件Accessory，电池Battery")
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
