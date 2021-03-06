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
 * @Description 查询客户走到哪个节点并带出车辆信息数据出参
 * @Author Chris
 * @Date 2021/1/6 13:28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "查询客户走到哪个节点并带出车辆信息数据出参", description = "查询客户走到哪个节点并带出车辆信息数据出参")
public class ToBeAssignNodeScooterInfoResult extends GeneralResult implements Serializable {

    private static final long serialVersionUID = 5926288128511373389L;

    @ApiModelProperty(value = "型号id")
    private Long specificatId;

    @ApiModelProperty(value = "型号名称")
    private String specificatName;

    @ApiModelProperty(value = "总数")
    private Integer totalCount;

    @ApiModelProperty(value = "车辆列表")
    private List<ToBeAssignNodeScooterInfoSubResult> scooterList;

}
