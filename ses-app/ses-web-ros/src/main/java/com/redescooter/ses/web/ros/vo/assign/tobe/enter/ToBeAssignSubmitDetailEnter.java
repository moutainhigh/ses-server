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
 * @Description 填写完R.SN并点击提交详情入参
 * @Author Chris
 * @Date 2020/12/28 13:46
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "填写完R.SN并点击提交详情入参", description = "填写完R.SN并点击提交详情入参")
public class ToBeAssignSubmitDetailEnter extends GeneralEnter implements Serializable {

    private static final long serialVersionUID = -5727715491909213573L;

    @ApiModelProperty(value = "型号id")
    private Long specificatId;

    @ApiModelProperty(value = "型号名称")
    private String specificatName;

    @ApiModelProperty(value = "座位数")
    private Integer seatNumber;

    @ApiModelProperty(value = "车牌")
    private String licensePlate;

    @ApiModelProperty(value = "R.SN")
    private String rsn;

    @ApiModelProperty(value = "颜色id")
    private Long colorId;

}
