package com.redescooter.ses.mobile.rps.vo.qc;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 保存质检结果返回对象 DTO
 * @author assert
 * @date 2021/1/14 9:52
 */
@Data
@ApiModel(value = "保存质检结果返回对象")
public class SaveQcResultDTO extends GeneralResult {

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
