package com.redescooter.ses.api.scooter.vo;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 25/10/2019 2:10 下午
 * @ClassName: ScooterIotResult
 * @Function: TODO
 */
@ApiModel(value = "Iot结果集", description = "Iot结果集")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class ScooterIotResult extends GeneralResult {

    @ApiModelProperty("iot请求编码:0->表示未调用IOT NONE 1->SUCCESS,2->OOFLINE,3->TIMEOUT")
    private int code;
    @ApiModelProperty("阐述说明")
    private String msg;

}
