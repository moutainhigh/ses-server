package com.redescooter.ses.mobile.rps.vo.outwhorder;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.redescooter.ses.api.common.constant.DateConstant;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 出库单产品序列号信息 DTO
 * @author assert
 * @date 2021/1/5 13:19
 */
@Data
@ApiModel(value = "出库单产品序列号信息")
public class ProductSerialNumberDTO extends GeneralResult {

    @ApiModelProperty(value = "主键id", dataType = "Long")
    private Long id;

    @ApiModelProperty(value = "产品名称", dataType = "String")
    private String name;

    @ApiModelProperty(value = "序列号", dataType = "Long")
    private String serialNum;

    @ApiModelProperty(value = "批次号", dataType = "String")
    private String lot;

    @ApiModelProperty(value = "部件号", dataType = "String")
    private String partsNo;

    @ApiModelProperty(value = "蓝牙mac地址", dataType = "String")
    private String bluetoothMacAddress;

    @ApiModelProperty(value = "供应商名称", dataType = "String")
    private String supplierName;

    @ApiModelProperty(value = "工厂名称", dataType = "String")
    private String factoryName;

    @DateTimeFormat(pattern = DateConstant.DEFAULT_DATETIME_FORMAT)
    @JsonFormat(pattern = DateConstant.DEFAULT_DATETIME_FORMAT, timezone = DateConstant.UTC)
    @ApiModelProperty(value = "入库时间")
    private Date arrivalTime;

    @DateTimeFormat(pattern = DateConstant.DEFAULT_DATETIME_FORMAT_TWO)
    @JsonFormat(pattern = DateConstant.DEFAULT_DATETIME_FORMAT_TWO, timezone = DateConstant.UTC)
    @ApiModelProperty(value = "质检时间")
    private Date qualityInspectionTime;

}
