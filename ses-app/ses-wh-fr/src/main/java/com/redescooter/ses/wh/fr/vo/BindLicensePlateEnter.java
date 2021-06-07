package com.redescooter.ses.wh.fr.vo;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @Description
 * @Author Chris
 * @Date 2021/5/18 9:39
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class BindLicensePlateEnter extends GeneralEnter {

    @ApiModelProperty(value = "车牌")
    private String licensePlate;

    @ApiModelProperty(value = "客户id")
    private Long customerId;

}
