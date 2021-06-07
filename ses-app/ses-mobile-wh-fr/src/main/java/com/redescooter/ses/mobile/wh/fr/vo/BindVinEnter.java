package com.redescooter.ses.mobile.wh.fr.vo;

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

    @ApiModelProperty(value = "VIN")
    private String vinCode;

    @ApiModelProperty(value = "客户id")
    private Long customerId;

    @ApiModelProperty(value = "车型名称")
    private String scooterName;

    @ApiModelProperty(value = "座位数量")
    private Integer seatNumber;

}
