package com.redescooter.ses.web.ros.vo.restproductionorder.allocateorder;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassNameAllocateNoDataResult
 * @Description
 * @Author Aleks
 * @Date2020/10/23 20:49
 * @Version V1.0
 **/
@Data
public class AllocateNoDataResult extends GeneralResult {

    @ApiModelProperty(value = "调拨单id")
    private Long allocateId;

    @ApiModelProperty(value = "调拨单号")
    private String allocateNo;

}
