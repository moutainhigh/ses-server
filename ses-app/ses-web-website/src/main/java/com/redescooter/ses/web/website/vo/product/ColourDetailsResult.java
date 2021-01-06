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
@ApiModel(value = "产品颜色结果集出参", description = "产品颜色结果集出参")
@Data
@EqualsAndHashCode(callSuper = true)
public class ColourDetailsResult extends GeneralResult {

    /**
     * 主建
     */
    @ApiModelProperty(value = "主键")
    private Long id;

    /**
     * 颜色使用范围,1整车，-1配件
     */
    @ApiModelProperty(value = "颜色使用范围,1整车，-1配件")
    private String colourRange;

    /**
     * 颜色名称
     */
    @ApiModelProperty(value = "颜色名称")
    private String colourName;

    /**
     * 颜色编码
     */
    @ApiModelProperty(value = "颜色编码")
    private String colourCode;

    /**
     * 颜色RGB值
     */
    @ApiModelProperty(value = "颜色RGB值")
    private String colourRgb;

    /**
     * 颜色16进制颜色编码
     */
    @ApiModelProperty(value = "颜色16进制颜色编码")
    private String colour16;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = DateConstant.DEFAULT_DATETIME_FORMAT)
    @JsonFormat(pattern = DateConstant.DEFAULT_DATETIME_FORMAT, timezone = DateConstant.UTC)
    @ApiModelProperty(value = "创建时间")
    private Date createdTime;

    /**
     * 更新时间
     */
    @DateTimeFormat(pattern = DateConstant.DEFAULT_DATETIME_FORMAT)
    @JsonFormat(pattern = DateConstant.DEFAULT_DATETIME_FORMAT, timezone = DateConstant.UTC)
    @ApiModelProperty(value = "更新时间")
    private Date updatedTime;
}
