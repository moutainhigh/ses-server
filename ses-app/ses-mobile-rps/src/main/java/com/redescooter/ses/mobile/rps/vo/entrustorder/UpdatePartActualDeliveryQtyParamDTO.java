package com.redescooter.ses.mobile.rps.vo.entrustorder;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 修改部件实际发货数量入参对象 DTO
 * @author assert
 * @date 2021/1/4 11:30
 */
@Data
@ApiModel(value = "修改部件实际发货数量入参对象")
public class UpdatePartActualDeliveryQtyParamDTO extends GeneralEnter {

    @ApiModelProperty(value = "部件id", dataType = "Long")
    private Long id;

    @ApiModelProperty(value = "实际发货数量", dataType = "Integer")
    private Integer qty;

}
