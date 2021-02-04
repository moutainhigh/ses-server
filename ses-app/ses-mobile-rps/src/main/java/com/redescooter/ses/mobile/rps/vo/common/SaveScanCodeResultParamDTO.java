package com.redescooter.ses.mobile.rps.vo.common;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.mobile.rps.exception.ValidationExceptionCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 保存扫码结果入参对象 DTO
 * @author assert
 * @date 2021/1/19 17:23
 */
@Data
@ApiModel(value = "保存扫码结果入参对象")
public class SaveScanCodeResultParamDTO extends GeneralEnter {

    @NotNull(code = ValidationExceptionCode.BOM_ID_IS_EMPTY, message = "bomId不能为空")
    @ApiModelProperty(value = "bomId", dataType = "Long", required = true)
    private Long bomId;

    @NotNull(code = ValidationExceptionCode.PRODUCT_ID_IS_EMPTY, message = "产品id不能为空")
    @ApiModelProperty(value = "产品id", dataType = "Long", required = true)
    private Long productId;

    @NotNull(code = ValidationExceptionCode.PRODUCT_TYPE_IS_EMPTY, message = "产品类型不能为空")
    @ApiModelProperty(value = "产品类型 1车辆 2组装件 3部件", dataType = "Integer")
    private Integer productType;

    @ApiModelProperty(value = "产品序列号", dataType = "String")
    private String serialNum;

    @ApiModelProperty(value = "车载平板序列号(车辆入库扫码时传递)", dataType = "String")
    private String tabletSn;

    @ApiModelProperty(value = "批次号", dataType = "String")
    private String lot;

    @ApiModelProperty(value = "部件号", dataType = "String")
    private String partsNo;

    @ApiModelProperty(value = "入库数量(无码部件时传递)", dataType = "Integer")
    private Integer qty;

    @ApiModelProperty(value = "蓝牙mac地址(仪表部件、车辆时传递)", dataType = "String")
    private String bluetoothMacAddress;

    @NotNull(code = ValidationExceptionCode.ID_CLASS_IS_EMPTY, message = "有无码标识不能为空")
    @ApiModelProperty(value = "是否有序列号 true/false", dataType = "Boolean", required = true)
    private Boolean idClass;

}
