package com.redescooter.ses.mobile.rps.vo.inwhorder;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 确认入库返回结果对象 DTO
 * @author assert
 * @date 2021/1/19 17:29
 */
@Data
@ApiModel(value = "确认入库返回结果对象")
public class ConfirmStorageResultDTO extends GeneralResult {

    @ApiModelProperty(value = "剩余数量", dataType = "String")
    private Integer qty;

    @ApiModelProperty(value = "名称", dataType = "String")
    private String name;

    @ApiModelProperty(value = "部件号", dataType = "String")
    private String partsNo;

    @ApiModelProperty(value = "批次号", dataType = "String")
    private String lot;

    @ApiModelProperty(value = "序列号", dataType = "String")
    private String serialNum;

}
