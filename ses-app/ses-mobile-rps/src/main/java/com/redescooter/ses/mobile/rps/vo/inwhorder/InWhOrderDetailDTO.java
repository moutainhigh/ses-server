package com.redescooter.ses.mobile.rps.vo.inwhorder;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 入库单详情返回结果对象 DTO
 * @author assert
 * @date 2021/1/15 18:18
 */
@Data
@ApiModel(value = "入库单详情返回结果对象")
public class InWhOrderDetailDTO extends GeneralResult {

    @ApiModelProperty(value = "主键id", dataType = "Long")
    private Long id;

}
