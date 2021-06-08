package com.redescooter.ses.web.ros.vo.restproductionorder.consignorder;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Description
 * @Author Chris
 * @Date 2021/6/8 16:54
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RSNResult extends GeneralResult {

    @ApiModelProperty(value = "整车序列号")
    private String rsn;

    @ApiModelProperty(value = "平板序列号")
    private String tabletSn;

    @ApiModelProperty(value = "蓝牙地址")
    private String bluetooth;

    @ApiModelProperty(value = "批次号")
    private String lot;

}
