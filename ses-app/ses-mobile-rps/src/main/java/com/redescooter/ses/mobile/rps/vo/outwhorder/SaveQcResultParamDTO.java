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

    @NotNull(code = ValidationExceptionCode.BOM_ID_IS_EMPTY, message = "bomId不能为空")
    @ApiModelProperty(value = "bomId", dataType = "Long", required = true)
    private Long bomId;

    @NotNull(code = ValidationExceptionCode.PRODUCT_ID_IS_EMPTY, message = "产品id不能为空")
    @ApiModelProperty(value = "产品id", dataType = "Long", required = true)
    private Long productId;

    @NotNull(code = ValidationExceptionCode.PRODUCT_TYPE_IS_EMPTY, message = "产品类型不能为空")
    @ApiModelProperty(value = "产品类型 1车辆 2组装件 3部件", dataType = "Integer", required = true)
    private Integer productType;

    @ApiModelProperty(value = "单据类型(需要这个参数来知道后面逻辑怎么走) 1入库单 2出库单", dataType = "Integer", required = true)
    private Integer type;

    @ApiModelProperty(value = "部件号", dataType = "String")
    private String partsNo;

    @ApiModelProperty(value = "产品序列号", dataType = "String")
    private String serialNum;

    @ApiModelProperty(value = "批次号", dataType = "String")
    private String lot;

    @ApiModelProperty(value = "质检数量(无码产品传递)", dataType = "Integer")
    private Integer qcQty;

    @ApiModelProperty(value = "蓝牙mac地址(仪表质检时传递)", dataType = "String")
    private String bluetoothMacAddress;

    @ApiModelProperty(value = "产品质检结果json数据, 格式：[{\"templateId\":\"质检模板id\",\"templateResultId\":\"质检结果id\"," +
            "\"imageUrls\":\"质检图片,多个使用逗号隔开\",\"remark\":\"备注说明\"}]", required = true)
    private String st;

}
