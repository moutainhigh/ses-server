package com.redescooter.ses.mobile.rps.vo.outwhorder;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.mobile.rps.exception.ValidationExceptionCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 修改部件质检数量入参对象 DTO
 * @author assert
 * @date 2021-01-10
 */
@Data
@ApiModel(value = "修改部件质检数量入参对象")
public class UpdatePartsQcQtyParamDTO extends GeneralEnter {

	@NotNull(code = ValidationExceptionCode.PRODUCT_ID_IS_EMPTY, message = "产品id不能为空")
	@ApiModelProperty(value = "产品id", dataType = "Long")
	private Long productId;

	@NotNull(code = ValidationExceptionCode.QTY_IS_EMPTY, message = "质检数量不能为空")
	@ApiModelProperty(value = "质检数量", dataType = "Integer")
	private Integer qcQty;

	@ApiModelProperty(value = "图片附件", dataType = "String")
	private String imageUrls;

}
