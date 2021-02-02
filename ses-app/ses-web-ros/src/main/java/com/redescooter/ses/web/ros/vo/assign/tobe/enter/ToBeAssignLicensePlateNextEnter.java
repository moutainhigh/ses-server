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
 * @Description 填写完车牌点击下一步入参
 * @Author Chris
 * @Date 2020/12/28 13:14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "填写完车牌点击下一步入参", description = "填写完车牌点击下一步入参")
public class ToBeAssignLicensePlateNextEnter extends GeneralEnter implements Serializable {

    private static final long serialVersionUID = -114831388249018355L;

    @ApiModelProperty(value = "车辆信息 给json字符串 格式:id:主键,licensePlate:车牌", required = true)
    private String list;

    @ApiModelProperty(value = "客户id", required = true)
    private Long customerId;

}
