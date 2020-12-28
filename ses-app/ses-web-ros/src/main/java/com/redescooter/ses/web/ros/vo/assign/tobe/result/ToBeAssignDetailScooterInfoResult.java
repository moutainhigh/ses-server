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

/**
 * @Description 待分配列表点击分配带出数据车辆信息出参
 * @Author Chris
 * @Date 2020/12/28 10:48
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "待分配列表点击分配带出数据车辆信息出参", description = "待分配列表点击分配带出数据车辆信息出参")
public class ToBeAssignDetailScooterInfoResult extends GeneralResult implements Serializable {

    private static final long serialVersionUID = 861225200525751044L;

    @ApiModelProperty(value = "型号id")
    private Long productId;

    @ApiModelProperty(value = "型号名称")
    private String productName;

    @ApiModelProperty(value = "颜色名称")
    private String colorName;

    @ApiModelProperty(value = "颜色色值")
    private String colorValue;

    @ApiModelProperty(value = "总数")
    private Integer totalCount;

    @ApiModelProperty(value = "待完成分配数")
    private Integer toBeAssignCount;

}
