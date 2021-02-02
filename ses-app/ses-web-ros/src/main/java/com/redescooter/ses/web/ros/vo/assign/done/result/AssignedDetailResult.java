package com.redescooter.ses.web.ros.vo.assign.done.result;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.web.ros.vo.assign.tobe.result.ToBeAssignDetailCustomerInfoResult;
import com.redescooter.ses.web.ros.vo.assign.tobe.result.ToBeAssignNextStopDetailResult;
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
 * @Description 已分配列表查看详情出参
 * @Author Chris
 * @Date 2020/12/28 14:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "已分配列表查看详情出参", description = "已分配列表查看详情出参")
public class AssignedDetailResult extends GeneralResult implements Serializable {

    private static final long serialVersionUID = -116861658362080216L;

    @ApiModelProperty(value = "客户信息")
    private ToBeAssignDetailCustomerInfoResult customerInfo;

    @ApiModelProperty(value = "车辆信息")
    private List<ToBeAssignNextStopDetailResult> scooterInfo;

}
