package com.redescooter.ses.mobile.rps.vo.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.redescooter.ses.api.common.constant.DateConstant;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 保存扫码结果返回对象 DTO
 * @author assert
 * @date 2021/1/21 15:35
 */
@Data
@ApiModel(value = "保存扫码结果返回对象")
public class SaveScanCodeResultDTO extends GeneralResult {

    @ApiModelProperty(value = "名称", dataType = "String")
    private String name;

    @ApiModelProperty(value = "部件号", dataType = "String")
    private String partsNo;

    @ApiModelProperty(value = "批次号", dataType = "String")
    private String lot;

    @ApiModelProperty(value = "序列号", dataType = "String")
    private String serialNum;

    @ApiModelProperty(value = "蓝牙mac地址(车辆完成组装返回)", dataType = "String")
    private String bluetoothMacAddress;

    @ApiModelProperty(value = "车载平板序列号(车辆完成组装返回)", dataType = "String")
    private String tabletSn;

    @ApiModelProperty(value = "剩余数量", dataType = "Integer")
    private Integer qty;

    @DateTimeFormat(pattern = DateConstant.DEFAULT_DATETIME_FORMAT_TWO)
    @JsonFormat(pattern = DateConstant.DEFAULT_DATETIME_FORMAT_TWO, timezone = DateConstant.UTC)
    @ApiModelProperty(value = "生产日期", dataType = "Date")
    private Date productionDate;

    @ApiModelProperty(value = "是否打印标识 true是/false否", dataType = "Boolean")
    private Boolean printFlag;

}
