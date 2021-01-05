package com.redescooter.ses.mobile.rps.vo.entrustorder;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 委托单发货入参对象 DTO
 * @author assert
 * @date 2021/1/4 11:34
 */
@Data
@ApiModel(value = "委托单发货入参对象")
public class EntrustOrderDeliverParamDTO extends GeneralEnter {

    @ApiModelProperty(value = "委托单id", dataType = "Long")
    private Long id;

    @ApiModelProperty(value = "物流公司", dataType = "String")
    private String logisticsCompany;

    @ApiModelProperty(value = "物流单号", dataType = "String")
    private String logisticsNo;

    @ApiModelProperty(value = "产品信息 json 格式：[{\"id\":123,\"serialNum\":\"123\",\"qty\":123,\"idClass\":true}]", dataType = "String")
    private String st;

}
