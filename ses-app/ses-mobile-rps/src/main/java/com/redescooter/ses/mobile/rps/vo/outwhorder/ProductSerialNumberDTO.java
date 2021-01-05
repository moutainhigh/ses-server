package com.redescooter.ses.mobile.rps.vo.outwhorder;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    @ApiModelProperty(value = "子单据id(出库单id)", dataType = "Long")
    private Long orderBId;

    @ApiModelProperty(value = "产品id", dataType = "Long")
    private Long productId;

    @ApiModelProperty(value = "序列号", dataType = "Long")
    private String serialNum;

    @ApiModelProperty(value = "批次号", dataType = "String")
    private String batchNo;

    @ApiModelProperty(value = "供应商Id", dataType = "Long")
    private Long supplierId;

    @ApiModelProperty(value = "供应商名称", dataType = "String")
    private String supplierName;

    @ApiModelProperty(value = "数量", dataType = "Integer")
    private Integer qty;

    @ApiModelProperty(value = "入库时间", dataType = "Date")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Date arrivalTime;

    @ApiModelProperty(value = "附件 多个附件逗号分割", dataType = "String")
    private String annex;

}
