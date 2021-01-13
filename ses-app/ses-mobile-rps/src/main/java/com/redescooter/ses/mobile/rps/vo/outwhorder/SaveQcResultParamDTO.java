package com.redescooter.ses.mobile.rps.vo.outwhorder;

import com.redescooter.ses.api.common.annotation.NotEmpty;
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

    @ApiModelProperty(value = "订单id(入库单、出库单id)")
    private Long orderId;

    @ApiModelProperty(value = "部件号", dataType = "String")
    private String partsNo;

    @ApiModelProperty(value = "产品序列号", dataType = "String")
    private String serialNum;

    @NotEmpty(code = ValidationExceptionCode.BATCH_NO_IS_EMPTY, message = "批次号不能为空")
    @ApiModelProperty(value = "批次号", dataType = "String")
    private String lot;

    @NotNull(code = ValidationExceptionCode.PRODUCT_TYPE_IS_EMPTY, message = "产品类型不能为空")
    @ApiModelProperty(value = "产品类型 1车辆 2组装件 3部件", dataType = "Integer")
    private Integer productType;

    /**
     * 单据类型字段说明：
     * 我需要通过这个字段来知道此时是在入库质检还是出库质检,通过这个字段配合productType来确定后面对库存的操作
     * 比如单据类型为：入库单,产品类型是部件,这个时候就能确定是对原料库进行操作
     */
    @ApiModelProperty(value = "单据类型(这个参数很重要,需要这个参数来知道后面流程怎么走) 1入库单 2出库单", dataType = "Integer")
    private Integer type;

    @ApiModelProperty(value = "产品质检结果json数据, 格式：[{\"templateId\":\"质检模板id\",\"templateResultId\":\"质检结果id\"," +
            "\"imageUrls\":\"质检图片,多个使用逗号隔开\",\"remark\":\"备注说明\"}]")
    private String st;

}
