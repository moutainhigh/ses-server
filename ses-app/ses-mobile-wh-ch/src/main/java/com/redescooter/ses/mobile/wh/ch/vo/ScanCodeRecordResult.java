package com.redescooter.ses.mobile.wh.ch.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.redescooter.ses.api.common.constant.DateConstant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @ClassName ScanCodeRecordResult
 * @Description
 * @Author Charles
 * @Date 2021/06/04 17:21
 */
@Data
@ApiModel(value = "部件列表")
@NoArgsConstructor
@AllArgsConstructor
public class ScanCodeRecordResult {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("车辆名称")
    private String scooterName;

    @ApiModelProperty("rsn 码")
    private String rsn;

    @JsonFormat(pattern = DateConstant.DEFAULT_DATETIME_FORMAT, timezone = DateConstant.UTC)
    @ApiModelProperty("创建时间")
    private Date createdTime;

    @ApiModelProperty("备注")
    private String remarks;
}
