package com.redescooter.ses.web.ros.vo.sim;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName SimBaseCodeResult
 * @Description sim and baseCode
 * @Author Charles
 * @Date 2021/05/27 17:49
 */
@Data
@ApiModel("sim and baseCode")
@NoArgsConstructor
@AllArgsConstructor
public class SimBaseCodeResult extends SimCardListResult {

    @ApiModelProperty("仪表SN")
    private String tabledSn;

    @ApiModelProperty("mac地址")
    private String macAddress;

    @ApiModelProperty("RSN")
    private String rsn;

    @ApiModelProperty("VIN")
    private String vin;
}
