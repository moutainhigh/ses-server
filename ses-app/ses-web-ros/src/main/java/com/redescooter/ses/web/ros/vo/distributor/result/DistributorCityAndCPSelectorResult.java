package com.redescooter.ses.web.ros.vo.distributor.result;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @Description 经销商城市和邮政编码下拉框出参
 * @Author Chris
 * @Date 2020/12/17 10:26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "经销商城市和邮政编码下拉框出参", description = "经销商城市和邮政编码下拉框出参")
public class DistributorCityAndCPSelectorResult extends GeneralResult implements Serializable {

    private static final long serialVersionUID = 7622578044355686003L;

    @ApiModelProperty(value = "下拉框的值")
    private List<Map<String, Object>> list;

}
