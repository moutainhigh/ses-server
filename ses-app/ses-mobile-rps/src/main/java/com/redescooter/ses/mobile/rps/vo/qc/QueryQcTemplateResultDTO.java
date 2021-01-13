package com.redescooter.ses.mobile.rps.vo.qc;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 查询质检模板返回结果对象 DTO
 * @author assert
 * @date 2021-01-10
 */
@Data
@ApiModel(value = "查询质检模板返回结果对象")
public class QueryQcTemplateResultDTO extends GeneralResult {

	@ApiModelProperty(value = "产品名称", dataType = "String")
	private String name;

	@ApiModelProperty(value = "产品质检模板集合")
	private List<ProductQcTemplateDTO> productQcTemplateList;

}
