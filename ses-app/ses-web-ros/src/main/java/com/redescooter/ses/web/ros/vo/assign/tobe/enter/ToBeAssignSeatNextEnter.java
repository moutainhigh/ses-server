package com.redescooter.ses.web.ros.vo.assign.tobe.enter;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Description 填写完座位数点击下一步入参
 * @Author Chris
 * @Date 2020/12/28 11:45
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "填写完座位数点击下一步入参", description = "填写完座位数点击下一步入参")
public class ToBeAssignSeatNextEnter extends GeneralEnter implements Serializable {

    private static final long serialVersionUID = 6858844507056189548L;

    @ApiModelProperty(value = "specificatId:型号id,specificatName:型号名称,seatNumber:座位数,qty:待分配车辆数量,vin:VIN Code", required = true)
    private String list;

    @ApiModelProperty(value = "客户id", required = true)
    private Long customerId;

}
