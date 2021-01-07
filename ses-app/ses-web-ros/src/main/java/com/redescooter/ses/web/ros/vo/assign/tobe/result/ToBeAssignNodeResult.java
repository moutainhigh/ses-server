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
 * @Description 查询客户走到哪个节点并带出数据出参
 * @Author Chris
 * @Date 2020/12/29 16:25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "查询客户走到哪个节点并带出数据出参", description = "查询客户走到哪个节点并带出数据出参")
public class ToBeAssignNodeResult extends GeneralResult implements Serializable {

    private static final long serialVersionUID = -1831570953621686198L;

    @ApiModelProperty(value = "节点 1VIN Code 2Bind License Plate 3Bind R.SN")
    private Integer node;

    @ApiModelProperty(value = "客户信息")
    private ToBeAssignDetailCustomerInfoResult customerInfo;

    @ApiModelProperty(value = "车辆信息 已分配的数据")
    private List<ToBeAssignNodeScooterInfoResult> scooterInfo;

    @ApiModelProperty(value = "任务清单信息")
    private List<ToBeAssignDetailScooterInfoResult> taskInfo;

}
