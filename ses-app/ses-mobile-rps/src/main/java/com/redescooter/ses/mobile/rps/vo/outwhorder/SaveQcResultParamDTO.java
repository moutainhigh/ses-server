package com.redescooter.ses.mobile.rps.vo.outwhorder;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.mobile.rps.exception.ValidationExceptionCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 保存质检结果入参对象 DTO
 * @author assert
 * @date 2021/1/4 17:01
 */
@Data
@ApiModel(value = "保存质检结果入参对象")
public class SaveQcResultParamDTO extends GeneralEnter {

    @NotNull(code = ValidationExceptionCode.PRODUCT_ID_IS_EMPTY, message = "产品id不能为空")
    @ApiModelProperty(value = "产品id", dataType = "Long")
    private Long productId;

    @NotNull(code = ValidationExceptionCode.PRODUCT_TYPE_IS_EMPTY, message = "产品类型不能为空")
    @ApiModelProperty(value = "产品类型 1车辆 2组装件 3部件", dataType = "Integer")
    private Integer productType;

    @ApiModelProperty(value = "质检数量 -- 无码产品质检时传递", dataType = "Integer")
    private Integer qty;

    @ApiModelProperty(value = "产品质检结果json数据, 格式：[{\"templateId\":\"111\",\"templateResultId\":\"111\"," +
            "\"imageUrls\":\"http://www.baidu.com,http://www.baidu.com\",\"remark\":\"备注说明\"}]")
    private String st;

}
