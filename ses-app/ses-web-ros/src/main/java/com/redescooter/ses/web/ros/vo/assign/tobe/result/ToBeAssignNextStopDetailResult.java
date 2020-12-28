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
 * @Description 待分配点击下一步车辆信息详情出参
 * @Author Chris
 * @Date 2020/12/28 11:30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "待分配点击下一步车辆信息详情出参", description = "待分配点击下一步车辆信息详情出参")
public class ToBeAssignNextStopDetailResult extends GeneralResult implements Serializable {

    private static final long serialVersionUID = -3335091688984467098L;

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "型号")
    private String specificatName;

    @ApiModelProperty(value = "座位数")
    private Integer seatNumber;

    @ApiModelProperty(value = "Vin Code")
    private String vinCode;

    @ApiModelProperty(value = "车牌")
    private String licensePlate;

    @ApiModelProperty(value = "R.SN")
    private String rsn;

    @ApiModelProperty(value = "颜色")
    private String colorName;

}
