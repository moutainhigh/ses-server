package com.redescooter.ses.web.website.vo.product;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.redescooter.ses.api.common.constant.DateConstant;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Author jerry
 * @Date 2021/1/5 10:16 下午
 * @Description 产品颜色结果集出参
 **/
@ApiModel(value = "Product color result set parameters", description = "产品颜色结果集出参")
@Data
@EqualsAndHashCode(callSuper = true)
public class ColourDetailsResult extends GeneralResult {

    /**
     * 主建
     */
    @ApiModelProperty(value = "ID")
    private Long id;

    /**
     * 颜色使用范围,1整车，-1配件
     */
    @ApiModelProperty(value = "Color range, 1 vehicle, - 1 accessories")
    private String colourRange;

    /**
     * 颜色名称
     */
    @ApiModelProperty(value = "Color name")
    private String colourName;

    /**
     * 颜色编码
     */
    @ApiModelProperty(value = "Color coding")
    private String colourCode;

    /**
     * 颜色RGB值
     */
    @ApiModelProperty(value = "Color RGB value")
    private String colourRgb;

    /**
     * 颜色16进制颜色编码
     */
    @ApiModelProperty(value = "Color hexadecimal color coding")
    private String colour16;

    /**
     * 备注
     */
    @ApiModelProperty(value = "remark")
    private String remark;

    /**
     * 更新时间
     */
    @DateTimeFormat(pattern = DateConstant.DEFAULT_DATETIME_FORMAT)
    @JsonFormat(pattern = DateConstant.DEFAULT_DATETIME_FORMAT, timezone = DateConstant.UTC)
    @ApiModelProperty(value = "Update time")
    private Date updatedTime;
}
