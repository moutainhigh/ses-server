package com.redescooter.ses.web.website.vo.parts;

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

    @ApiModelProperty(value = "类型,全部类型AllType，1电池Battery，2配件Accessory")
    private String partsType;

    @ApiModelProperty(value = "部品号")
    private String partsNumber;

    @ApiModelProperty(value = "中文名称")
    private String cnName;

    @ApiModelProperty(value = "法文名称")
    private String frName;

    @ApiModelProperty(value = "英文名称")
    private String enName;

    @ApiModelProperty(value = "规格")
    private String specs;

    @ApiModelProperty(value = "销售价格 浮点型价格")
    private BigDecimal price;

}
