package com.redescooter.ses.mobile.rps.vo.restproductionorder;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName:saveQcResultEnter
 * @description: saveQcResultEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/11/02 15:25 
 */
@Data
@ApiModel(value = "保存质检结果", description = "保存质检结果")
public class SaveQcResultEnter extends GeneralEnter {

    @ApiModelProperty(value = "子单据Id", dataType = "Long")
    private Long id;

    @ApiModelProperty(value = "产品Id(不同产品类型的出库单id,比如部件产品id)", dataType = "Long")
    private Long productId;

    @ApiModelProperty(value = "产品类型 1车辆 2组装件 3部件", dataType = "Integer")
    private Integer productType;

    @ApiModelProperty(value = "序列号", dataType = "String")
    private String serialNum;

    @ApiModelProperty(value = "是否存在序列号", dataType = "Boolean")
    private Boolean idClass;

    @ApiModelProperty(value = "批次号", dataType = "String")
    private String lot;

    @ApiModelProperty(value = "质检数量", dataType = "Integer")
    private Integer qty;

    @ApiModelProperty(value = "图片URL", dataType = "String")
    private String imageUrl;

    @ApiModelProperty(value = "质检结果 json 格式：[{\"id\":123,\"imageUrls\":\"\",\"qcResultId\":321313,\"remark\":\"\"}]")
    private String st;

}
