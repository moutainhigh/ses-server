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
 * @Description 填写完车牌点击下一步详情入参
 * @Author Chris
 * @Date 2020/12/28 13:17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "填写完车牌点击下一步详情入参", description = "填写完车牌点击下一步详情入参")
public class ToBeAssignLicensePlateNextDetailEnter extends GeneralEnter implements Serializable {

    private static final long serialVersionUID = -9140542539812452340L;

    @ApiModelProperty(value = "主键", required = true)
    private Long id;

    @ApiModelProperty(value = "车牌", required = true)
    private String licensePlate;

}
