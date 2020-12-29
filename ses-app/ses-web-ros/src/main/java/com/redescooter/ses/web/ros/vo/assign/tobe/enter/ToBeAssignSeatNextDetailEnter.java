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
 * @Description 填写完座位数点击下一步详情入参
 * @Author Chris
 * @Date 2020/12/28 11:47
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "填写完座位数点击下一步详情入参", description = "填写完座位数点击下一步详情入参")
public class ToBeAssignSeatNextDetailEnter extends GeneralEnter implements Serializable {

    private static final long serialVersionUID = -6549302334363065918L;

    @ApiModelProperty(value = "型号id", required = true)
    private Long specificatId;

    @ApiModelProperty(value = "型号名称", required = true)
    private String specificatName;

    @ApiModelProperty(value = "座位数", required = true)
    private Integer seatNumber;

}