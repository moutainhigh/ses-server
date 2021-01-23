package com.redescooter.ses.web.website.vo.parts;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * @Author jerry
 * @Date 2021/1/6 3:37 上午
 * @Description 配件详情结果集出参
 **/
@ApiModel(value = "配件详情结果集出参", description = "配件详情结果集出参")
@Data
@EqualsAndHashCode(callSuper = true)
public class PartsDetailsResult extends GeneralResult {
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private Long id;

    /**
     * 类型,全部类型AllType，零部件Parts，配件Accessory，电池Battery
     */
    @ApiModelProperty(value = "Type, alltype, parts, accessory, battery")
    private String partsType;

    /**
     * 部品遍号
     */
    @ApiModelProperty(value = "Part number")
    private String partsNumber;

    /**
     * 中文名称
     */
    @ApiModelProperty(value = "Chinese name")
    private String cnName;

    /**
     * 法文名称
     */
    @ApiModelProperty(value = "French name")
    private String frName;

    /**
     * 英文名称
     */
    @ApiModelProperty(value = "English name")
    private String enName;

    /**
     * 图片
     */
    @ApiModelProperty(value = "picture")
    private String picture;

    /**
     * 销售价格
     */
    @ApiModelProperty(value = "price")
    private BigDecimal price;

    /**
     * 采购来源
     */
    @ApiModelProperty(value = "Source of procurement")
    private String sources;

    /**
     * 货币单位 如¥，$，€，￡
     */
    @ApiModelProperty(value = "Monetary units such as ￥, $, $, ￡")
    private String currencyUnit;

    /**
     * 优惠抵扣金额
     */
    @ApiModelProperty(value = "The discount deduction amount is 0 by default")
    private BigDecimal amountDiscount;

    /**
     * 备注
     */
    @ApiModelProperty(value = "remark")
    private String remark;


}
