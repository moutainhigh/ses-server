package com.redescooter.ses.web.ros.vo.assign.doing.result;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.web.ros.vo.assign.tobe.result.ToBeAssignDetailCustomerInfoResult;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Description
 * @Author Chris
 * @Date 2021/5/11 12:56
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class AssigningDetailResult extends GeneralResult {

    @ApiModelProperty(value = "客户信息")
    private ToBeAssignDetailCustomerInfoResult customerInfo;

    @ApiModelProperty(value = "车辆信息")
    private List<AssigningDetailScooterResult> scooterInfo;

}
