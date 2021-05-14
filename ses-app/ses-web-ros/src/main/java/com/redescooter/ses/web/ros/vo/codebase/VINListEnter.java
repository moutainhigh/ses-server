package com.redescooter.ses.web.ros.vo.codebase;

import com.redescooter.ses.api.common.vo.base.PageEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Description
 * @Author Chris
 * @Date 2021/5/14 11:34
 */
@Data
@ApiModel(value = "VIN列表入参")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class VINListEnter extends PageEnter {

    @ApiModelProperty(value = "车型id")
    private Long specificatTypeId;

    @ApiModelProperty(value = "座位数")
    private Integer seatNumber;

    @ApiModelProperty(value = "VIN")
    private String vin;

    @ApiModelProperty(value = "状态 0全部 1待分配 2已分配")
    private Integer status;

    @ApiModelProperty(value = "生成时间开始")
    private Date generateDateStartTime;

    @ApiModelProperty(value = "生成时间结束")
    private Date generateDateEndTime;

    @ApiModelProperty(value = "更新时间开始")
    private Date finishDateStartTime;

    @ApiModelProperty(value = "更新时间结束")
    private Date finishDateEndTime;

}
