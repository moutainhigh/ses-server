package com.redescooter.ses.web.website.vo.product;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * @Author jerry
 * @Date 2021/1/6 3:37 上午
 * @Description 新增配件入参
 **/

@ApiModel(value = "新增配件入参", description = "新增配件入参")
@Data
@EqualsAndHashCode(callSuper = true)
public class AddPartsEnter extends GeneralEnter {

    /**
     * 逻辑删除
     */
    @ApiModelProperty(value = "逻辑删除")
    private Integer dr;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    private String status;

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
     * 采购来源
     */
    @ApiModelProperty(value = "采购来源")
    private String sources;


    @ApiModelProperty(value="销售价格 浮点型价格")
    private BigDecimal price;

    @ApiModelProperty(value = "货币单位 如¥，$，€，￡")
    private String currencyUnit;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;


}
