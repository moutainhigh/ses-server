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
@ApiModel(value = "绑定VIN入参")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class BindVinEnter extends GeneralEnter {

    @ApiModelProperty(value = "VIN", required = true)
    private String vinCode;

    @ApiModelProperty(value = "RSN", required = true)
    private String rsn;

}
