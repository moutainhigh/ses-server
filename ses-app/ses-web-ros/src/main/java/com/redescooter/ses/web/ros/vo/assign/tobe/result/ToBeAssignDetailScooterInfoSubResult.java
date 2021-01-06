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
 * @Description 待分配列表点击分配带出数据车辆信息子出参
 * @Author Chris
 * @Date 2021/1/5 13:42
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "待分配列表点击分配带出数据车辆信息子出参", description = "待分配列表点击分配带出数据车辆信息子出参")
public class ToBeAssignDetailScooterInfoSubResult extends GeneralResult implements Serializable {

    private static final long serialVersionUID = 1863840952402694539L;

    @ApiModelProperty(value = "颜色名称")
    private String colorName;

    @ApiModelProperty(value = "颜色色值")
    private String colorValue;

    @ApiModelProperty(value = "颜色id")
    private Long colorId;

    @ApiModelProperty(value = "待完成分配数")
    private Integer toBeAssignCount;

}
