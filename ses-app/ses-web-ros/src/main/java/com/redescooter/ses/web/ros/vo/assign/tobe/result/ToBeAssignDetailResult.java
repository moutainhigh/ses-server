package com.redescooter.ses.web.ros.vo.assign.tobe.result;

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

/**
 * @Description 待分配列表点击分配带出数据出参
 * @Author Chris
 * @Date 2020/12/28 9:57
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "待分配列表点击分配带出数据出参", description = "待分配列表点击分配带出数据出参")
public class ToBeAssignDetailResult extends GeneralResult implements Serializable {

    private static final long serialVersionUID = -8420834912374296492L;

    @ApiModelProperty(value = "客户信息")
    private ToBeAssignDetailCustomerInfoResult customerInfo;

    @ApiModelProperty(value = "车辆信息")
    private List<ToBeAssignDetailScooterInfoResult> scooterInfo;

}
