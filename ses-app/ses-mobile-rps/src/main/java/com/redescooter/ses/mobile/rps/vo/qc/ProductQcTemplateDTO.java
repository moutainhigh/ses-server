package com.redescooter.ses.mobile.rps.vo.qc;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 产品质检模板对象 DTO
 * @author assert
 * @date 2021/1/6 14:42
 */
@Data
@ApiModel(value = "产品质检模板对象")
public class ProductQcTemplateDTO extends GeneralResult {

    @ApiModelProperty(value = "主键id", dataType = "Long")
    private Long id;

    @ApiModelProperty(value = "bomId", dataType = "Long")
    private Long bomId;

    @ApiModelProperty(value = "bom类型 0：部件草稿，1：部件，2：整车草稿，3：整车，4：组装草稿，5：组装件", dataType = "Integer")
    private Integer bomType;

    @ApiModelProperty(value = "导入批次号", dataType = "String")
    private String importExcelBatchNo;

    @ApiModelProperty(value = "来源方式 0：手动新增 1：导入", dataType = "Integer")
    private Integer sourceType;

    @ApiModelProperty(value = "质检项名称", dataType = "String")
    private String qcItemName;

    @ApiModelProperty(value = "产品质检项结果")
    private List<ProductQcTemplateResultDTO> qcTemplateResultList;

}
