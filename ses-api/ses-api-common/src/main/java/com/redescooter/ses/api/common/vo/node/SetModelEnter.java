package com.redescooter.ses.api.common.vo.node;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @Description
 * @Author Chris
 * @Date 2021/6/21 21:05
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class SetModelEnter extends GeneralEnter {

    @ApiModelProperty(value = "车型 E50传1 E100传2", required = true)
    private Integer model;

    @ApiModelProperty(value = "RSN", required = true)
    private String rsn;

}
