package com.redescooter.ses.web.ros.vo.distributor.enter;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Description 经销商城市和邮政编码下拉框入参
 * @Author Chris
 * @Date 2020/12/17 17:28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "经销商城市和邮政编码下拉框入参", description = "经销商城市和邮政编码下拉框入参")
public class DistributorCityAndCPSelectorEnter extends GeneralEnter implements Serializable {

    private static final long serialVersionUID = -7279182907360498065L;

    @ApiModelProperty(value = "若想得到城市下拉框,入参传1;若想得到邮政编码下拉框,入参传2;若想得到联动后的邮政编码下拉框,入参传城市名称", required = true)
    private String key;

}
