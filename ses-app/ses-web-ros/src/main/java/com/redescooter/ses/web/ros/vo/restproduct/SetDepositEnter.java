package com.redescooter.ses.web.ros.vo.restproduct;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@ApiModel(value = "销售价格新增或者编辑入参")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class SetDepositEnter extends GeneralEnter {
    @ApiModelProperty(value = "定金(算作首期)")
    private BigDecimal deposit;
}
