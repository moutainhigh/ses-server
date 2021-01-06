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
 * @Date 2021/1/5 6:00 下午
 * @Description 产品型号结果出参
 **/
@ApiModel(value = "产品型号结果出参", description = "产品型号结果出参")
@Data
@EqualsAndHashCode(callSuper = true)
public class ProductModelDetailsResult extends GeneralResult {

    /**
     * 主键 主键
     */
    @ApiModelProperty(value = "主键")
    private Long id;

    /**
     * 产品种类主建
     */
    @ApiModelProperty(value = "产品种类主建")
    private Long productClassId;

    /**
     * 产品类型单项编码
     */
    @ApiModelProperty(value = "产品类型单项编码")
    private String productModelCode;

    /**
     * 产品类型单项名称
     */
    @ApiModelProperty(value = "产品类型单项名称")
    private String productModelName;

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
