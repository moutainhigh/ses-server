package com.redescooter.ses.web.website.vo.product;

import com.baomidou.mybatisplus.annotation.TableField;
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
    @ApiModelProperty(value = "类型,全部类型AllType，零部件Parts，配件Accessory，电池Battery")
    private String partsType;

    /**
     * 部品号
     */
    @ApiModelProperty(value = "部品号")
    private String partsNumber;

    /**
     * 中文名称
     */
    @ApiModelProperty(value = "中文名称")
    private String cnName;

    /**
     * 法文名称
     */
    @ApiModelProperty(value = "法文名称")
    private String frName;

    /**
     * 英文名称
     */
    @ApiModelProperty(value = "英文名称")
    private String enName;

    /**
     * 图片
     */
    @ApiModelProperty(value = "图片")
    private String picture;

    /**
     * 销售价格 浮点型价格
     */
    @TableField(value = "price")
    @ApiModelProperty(value = "销售价格 浮点型价格")
    private BigDecimal price;

    /**
     * 采购来源
     */
    @ApiModelProperty(value = "采购来源")
    private String sources;

    /**
     * 货币单位 如¥，$，€，￡
     */
    @ApiModelProperty(value = "货币单位 如¥，$，€，￡")
    private String currencyUnit;

    /**
     * 优惠抵扣金额
     */
    @ApiModelProperty(value = "优惠抵扣金额")
    private BigDecimal amountDiscount;

    /**
     * 备注
     */
    @TableField(value = "remark")
    @ApiModelProperty(value = "备注")
    private String remark;


}
