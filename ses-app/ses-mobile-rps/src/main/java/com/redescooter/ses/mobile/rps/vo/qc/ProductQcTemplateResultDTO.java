package com.redescooter.ses.mobile.rps.vo.qc;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 产品质检模板结果对象 DTO
 * @author assert
 * @date 2021/1/6 14:43
 */
@Data
@ApiModel(value = "产品质检模板结果对象")
public class ProductQcTemplateResultDTO extends GeneralResult {

    @ApiModelProperty(value = "主键id", dataType = "Long")
    private Long id;

    @ApiModelProperty(value = "质检模板Id", dataType = "Long")
    private Long templateId;

    @ApiModelProperty(value = "质检结果", dataType = "String")
    private String qcResult;

    @ApiModelProperty(value = "通过标记 true/false", dataType = "Boolean")
    private Boolean passFlag;

    @ApiModelProperty(value = "上传图片标志", dataType = "Boolean")
    private Boolean uploadFlag;

    @ApiModelProperty(value = "结果排序级别", dataType = "Integer")
    private Integer resultsSequence;

}
