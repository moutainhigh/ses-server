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

    @ApiModelProperty(value = "型号")



    @ApiModelProperty(value = "颜色")



    @ApiModelProperty(value = "总数")



    @ApiModelProperty(value = "待完成分配数")











}
