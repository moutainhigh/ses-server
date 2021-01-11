package com.redescooter.ses.mobile.rps.vo.outwhorder;

import com.redescooter.ses.api.common.annotation.NotEmpty;
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

    @NotEmpty(code = ValidationExceptionCode.SERIAL_NUMBER_IS_EMPTY, message = "产品序列号不能为空")
    @ApiModelProperty(value = "产品序列号", dataType = "String")
    private String serialNum;

    @ApiModelProperty(value = "产品质检结果json数据, 格式：[{\"templateId\":\"111\",\"templateResultId\":\"111\"," +
            "\"imageUrls\":\"http://www.baidu.com,http://www.baidu.com\",\"remark\":\"备注说明\"}]")
    private String st;

}
