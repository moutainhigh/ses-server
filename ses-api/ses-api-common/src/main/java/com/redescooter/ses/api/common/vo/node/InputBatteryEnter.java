package com.redescooter.ses.api.common.vo.node;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @Description
 * @Author Chris
 * @Date 2021/5/11 16:21
 */
@Data
@ApiModel(value = "录入电池入参")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class InputBatteryEnter extends GeneralEnter {

    @ApiModelProperty(value = "电池码 多个时通过逗号分隔", required = true)
    private String battery;

    @ApiModelProperty(value = "RSN", required = true)
    private String rsn;

}
